package infra.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.game.GameResult;
import domain.game.GameStatus;
import domain.game.JanggiGame;
import domain.pieces.Cha;
import domain.pieces.Gung;
import domain.pieces.Piece;
import domain.pieces.PieceType;
import domain.pieces.Side;
import domain.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JdbcGameRepositoryTest {
    private JdbcConnectionManager connectionManager;
    private JdbcGameRepository repository;

    @BeforeEach
    void setUp() {
        connectionManager = JdbcTestSupport.connectionManager();
        new SchemaInitializer(connectionManager).initialize();
        repository = repositoryAt(Instant.parse("2026-04-07T03:01:00Z"));
    }

    @AfterEach
    void clearAll() {
        JdbcTestSupport.clearAll(connectionManager);
    }

    @Test
    void 저장하면_janggi_game_테이블에_게임_메타정보를_저장한다() throws Exception {
        // given
        JanggiGame janggiGame = runningGame(Side.CHO, new Position(0, 0), new Position(1, 4));

        // when
        JanggiGame savedGame = repository.save(janggiGame);

        // then
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT current_turn, status, winner FROM janggi_game WHERE game_id = ?")) {
            statement.setLong(1, savedGame.gameId());

            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getString("current_turn")).isEqualTo("CHO");
                assertThat(resultSet.getString("status")).isEqualTo("RUNNING");
                assertThat(resultSet.getString("winner")).isNull();
            }
        }
    }

    @Test
    void 저장하면_game_piece_테이블에_기물_정보를_저장한다() throws Exception {
        // given
        JanggiGame janggiGame = runningGame(Side.CHO, new Position(0, 0), new Position(1, 4));

        // when
        JanggiGame savedGame = repository.save(janggiGame);

        // then
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement countStatement = connection.prepareStatement(
                     "SELECT COUNT(*) FROM game_piece WHERE game_id = ?");
             PreparedStatement pieceStatement = connection.prepareStatement(
                     "SELECT side, piece_type FROM game_piece WHERE game_id = ? AND row_index = ? AND column_index = ?")) {
            countStatement.setLong(1, savedGame.gameId());

            try (ResultSet resultSet = countStatement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getInt(1)).isEqualTo(2);
            }

            pieceStatement.setLong(1, savedGame.gameId());
            pieceStatement.setInt(2, 0);
            pieceStatement.setInt(3, 0);

            try (ResultSet resultSet = pieceStatement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getString("side")).isEqualTo("CHO");
                assertThat(resultSet.getString("piece_type")).isEqualTo("CHA");
            }
        }
    }

    @Test
    void 진행중인_게임이_없으면_Optional_empty를_반환한다() {
        // when
        var result = repository.findLatestRunningGame();

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void 가장_최근의_진행중_게임을_조회한다() {
        repositoryAt(Instant.parse("2026-04-07T03:01:00Z"))
                .save(runningGame(Side.CHO, new Position(0, 0), new Position(1, 4)));
        repositoryAt(Instant.parse("2026-04-07T03:30:00Z"))
                .save(endedGame(Side.HAN));
        JanggiGame expectedGame = repositoryAt(Instant.parse("2026-04-07T03:40:00Z"))
                .save(runningGame(Side.HAN, new Position(0, 0), new Position(8, 4)));

        // when
        var result = repository.findLatestRunningGame();

        // then
        assertThat(result).isPresent();

        JanggiGame janggiGame = result.orElseThrow();
        assertThat(janggiGame.gameId()).isEqualTo(expectedGame.gameId());
        assertThat(janggiGame.currentTurn()).isEqualTo(Side.HAN);
        assertThat(janggiGame.gameResult()).isEqualTo(GameResult.running());
        assertThat(janggiGame.board().pieces().get(new Position(0, 0)).getType()).isEqualTo(PieceType.CHA);
        assertThat(janggiGame.board().pieces().get(new Position(8, 4)).getType()).isEqualTo(PieceType.GUNG);
    }

    @Test
    void 수정하면_janggi_game_메타정보를_갱신한다() throws Exception {
        // given
        JanggiGame savedGame = repositoryAt(Instant.parse("2026-04-07T03:01:00Z"))
                .save(runningGame(Side.CHO, new Position(0, 0), new Position(1, 4)));
        JanggiGame updatedGame = endedGame(Side.HAN);
        updatedGame.assignGameId(savedGame.gameId());

        // when
        repositoryAt(Instant.parse("2026-04-07T03:30:00Z")).update(updatedGame);

        // then
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT current_turn, status, winner, updated_at FROM janggi_game WHERE game_id = ?")) {
            statement.setLong(1, savedGame.gameId());

            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getString("current_turn")).isEqualTo("HAN");
                assertThat(resultSet.getString("status")).isEqualTo("ENDED");
                assertThat(resultSet.getString("winner")).isEqualTo("HAN");
                assertThat(resultSet.getTimestamp("updated_at").toLocalDateTime())
                        .isEqualTo(LocalDateTime.of(2026, 4, 7, 12, 30));
            }
        }
    }

    @Test
    void 수정하면_game_piece_기물_정보를_현재_상태로_교체한다() throws Exception {
        // given
        JanggiGame savedGame = repositoryAt(Instant.parse("2026-04-07T03:01:00Z"))
                .save(runningGame(Side.CHO, new Position(0, 0), new Position(1, 4)));
        JanggiGame updatedGame = runningGame(Side.HAN, new Position(0, 1), null);
        updatedGame.assignGameId(savedGame.gameId());

        // when
        repositoryAt(Instant.parse("2026-04-07T03:10:00Z")).update(updatedGame);

        // then
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement countStatement = connection.prepareStatement(
                     "SELECT COUNT(*) FROM game_piece WHERE game_id = ?");
             PreparedStatement oldPieceStatement = connection.prepareStatement(
                     "SELECT COUNT(*) FROM game_piece WHERE game_id = ? AND row_index = ? AND column_index = ?");
             PreparedStatement movedPieceStatement = connection.prepareStatement(
                     "SELECT side, piece_type FROM game_piece WHERE game_id = ? AND row_index = ? AND column_index = ?")) {
            countStatement.setLong(1, savedGame.gameId());

            try (ResultSet resultSet = countStatement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getInt(1)).isEqualTo(1);
            }

            oldPieceStatement.setLong(1, savedGame.gameId());
            oldPieceStatement.setInt(2, 0);
            oldPieceStatement.setInt(3, 0);

            try (ResultSet resultSet = oldPieceStatement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getInt(1)).isZero();
            }

            movedPieceStatement.setLong(1, savedGame.gameId());
            movedPieceStatement.setInt(2, 0);
            movedPieceStatement.setInt(3, 1);

            try (ResultSet resultSet = movedPieceStatement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getString("side")).isEqualTo("CHO");
                assertThat(resultSet.getString("piece_type")).isEqualTo("CHA");
            }
        }
    }

    private JdbcGameRepository repositoryAt(Instant instant) {
        Clock fixedClock = Clock.fixed(instant, ZoneId.of("Asia/Seoul"));
        return new JdbcGameRepository(
                connectionManager,
                new SavedGameWriteMapper(fixedClock),
                new SavedGameReadMapper()
        );
    }

    private JanggiGame runningGame(Side currentTurn, Position chaPosition, Position gungPosition) {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(chaPosition, new Cha(Side.CHO));
        if (gungPosition != null) {
            pieces.put(gungPosition, new Gung(Side.HAN));
        }
        return JanggiGame.restore(null, new Board(pieces), currentTurn, GameResult.running());
    }

    private JanggiGame endedGame(Side winner) {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(8, 4), new Gung(winner));
        return JanggiGame.restore(null, new Board(pieces), Side.HAN, GameResult.ended(winner));
    }
}
