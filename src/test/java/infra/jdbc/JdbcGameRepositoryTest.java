package infra.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.GameStatus;
import domain.pieces.PieceType;
import domain.pieces.Side;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.SavedGameDto;
import repository.SavedPieceDto;

class JdbcGameRepositoryTest {
    private JdbcConnectionManager connectionManager;
    private JdbcGameRepository repository;

    @BeforeEach
    void setUp() {
        connectionManager = JdbcTestSupport.connectionManager();
        new SchemaInitializer(connectionManager).initialize();
        repository = new JdbcGameRepository(connectionManager);
    }

    @AfterEach
    void clearAll() {
        JdbcTestSupport.clearAll(connectionManager);
    }

    @Test
    void 저장하면_janggi_game_테이블에_게임_메타정보를_저장한다() throws Exception {
        // given
        SavedGameDto savedGameDto = savedGameDto();

        // when
        long gameId = repository.save(savedGameDto);

        // then
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT current_turn, status, winner FROM janggi_game WHERE game_id = ?")) {
            statement.setLong(1, gameId);

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
        SavedGameDto savedGameDto = savedGameDto();

        // when
        long gameId = repository.save(savedGameDto);

        // then
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement countStatement = connection.prepareStatement(
                     "SELECT COUNT(*) FROM game_piece WHERE game_id = ?");
             PreparedStatement pieceStatement = connection.prepareStatement(
                     "SELECT side, piece_type FROM game_piece WHERE game_id = ? AND row_index = ? AND column_index = ?")) {
            countStatement.setLong(1, gameId);

            try (ResultSet resultSet = countStatement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getInt(1)).isEqualTo(2);
            }

            pieceStatement.setLong(1, gameId);
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
        repository.save(new SavedGameDto(
                null,
                Side.CHO,
                GameStatus.RUNNING,
                null,
                LocalDateTime.of(2026, 4, 7, 12, 0),
                LocalDateTime.of(2026, 4, 7, 12, 1),
                List.of(
                        new SavedPieceDto(0, 0, Side.CHO, PieceType.CHA)
                )
        ));

        repository.save(new SavedGameDto(
                null,
                Side.HAN,
                GameStatus.ENDED,
                Side.HAN,
                LocalDateTime.of(2026, 4, 7, 12, 0),
                LocalDateTime.of(2026, 4, 7, 12, 30),
                List.of(
                        new SavedPieceDto(8, 4, Side.HAN, PieceType.GUNG)
                )
        ));

        long expectedGameId = repository.save(new SavedGameDto(
                null,
                Side.HAN,
                GameStatus.RUNNING,
                null,
                LocalDateTime.of(2026, 4, 7, 12, 10),
                LocalDateTime.of(2026, 4, 7, 12, 40),
                List.of(
                        new SavedPieceDto(0, 0, Side.CHO, PieceType.CHA),
                        new SavedPieceDto(8, 4, Side.HAN, PieceType.GUNG)
                )
        ));

        // when
        var result = repository.findLatestRunningGame();

        // then
        assertThat(result).isPresent();

        SavedGameDto savedGameDto = result.orElseThrow();
        assertThat(savedGameDto.gameId()).isEqualTo(expectedGameId);
        assertThat(savedGameDto.currentTurn()).isEqualTo(Side.HAN);
        assertThat(savedGameDto.status()).isEqualTo(GameStatus.RUNNING);
        assertThat(savedGameDto.winner()).isNull();
        assertThat(savedGameDto.createdAt()).isEqualTo(LocalDateTime.of(2026, 4, 7, 12, 10));
        assertThat(savedGameDto.updatedAt()).isEqualTo(LocalDateTime.of(2026, 4, 7, 12, 40));
        assertThat(savedGameDto.pieces()).containsExactly(
                new SavedPieceDto(0, 0, Side.CHO, PieceType.CHA),
                new SavedPieceDto(8, 4, Side.HAN, PieceType.GUNG)
        );
    }

    @Test
    void 수정하면_janggi_game_메타정보를_갱신한다() throws Exception {
        // given
        long gameId = repository.save(savedGameDto());
        SavedGameDto updatedGameDto = new SavedGameDto(
                gameId,
                Side.HAN,
                GameStatus.ENDED,
                Side.HAN,
                LocalDateTime.of(2026, 4, 7, 12, 0),
                LocalDateTime.of(2026, 4, 7, 12, 30),
                List.of(
                        new SavedPieceDto(8, 4, Side.HAN, PieceType.GUNG)
                )
        );

        // when
        repository.update(updatedGameDto);

        // then
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT current_turn, status, winner, updated_at FROM janggi_game WHERE game_id = ?")) {
            statement.setLong(1, gameId);

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
        long gameId = repository.save(savedGameDto());
        SavedGameDto updatedGameDto = new SavedGameDto(
                gameId,
                Side.HAN,
                GameStatus.RUNNING,
                null,
                LocalDateTime.of(2026, 4, 7, 12, 0),
                LocalDateTime.of(2026, 4, 7, 12, 10),
                List.of(
                        new SavedPieceDto(0, 1, Side.CHO, PieceType.CHA)
                )
        );

        // when
        repository.update(updatedGameDto);

        // then
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement countStatement = connection.prepareStatement(
                     "SELECT COUNT(*) FROM game_piece WHERE game_id = ?");
             PreparedStatement oldPieceStatement = connection.prepareStatement(
                     "SELECT COUNT(*) FROM game_piece WHERE game_id = ? AND row_index = ? AND column_index = ?");
             PreparedStatement movedPieceStatement = connection.prepareStatement(
                     "SELECT side, piece_type FROM game_piece WHERE game_id = ? AND row_index = ? AND column_index = ?")) {
            countStatement.setLong(1, gameId);

            try (ResultSet resultSet = countStatement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getInt(1)).isEqualTo(1);
            }

            oldPieceStatement.setLong(1, gameId);
            oldPieceStatement.setInt(2, 0);
            oldPieceStatement.setInt(3, 0);

            try (ResultSet resultSet = oldPieceStatement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getInt(1)).isZero();
            }

            movedPieceStatement.setLong(1, gameId);
            movedPieceStatement.setInt(2, 0);
            movedPieceStatement.setInt(3, 1);

            try (ResultSet resultSet = movedPieceStatement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getString("side")).isEqualTo("CHO");
                assertThat(resultSet.getString("piece_type")).isEqualTo("CHA");
            }
        }
    }

    private SavedGameDto savedGameDto() {
        return new SavedGameDto(
                null,
                Side.CHO,
                GameStatus.RUNNING,
                null,
                LocalDateTime.of(2026, 4, 7, 12, 0),
                LocalDateTime.of(2026, 4, 7, 12, 1),
                List.of(
                        new SavedPieceDto(0, 0, Side.CHO, PieceType.CHA),
                        new SavedPieceDto(1, 4, Side.HAN, PieceType.GUNG)
                )
        );
    }
}
