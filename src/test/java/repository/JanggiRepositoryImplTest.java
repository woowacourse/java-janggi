package repository;

import config.db.DatabaseConfig;
import model.coordinate.Position;
import model.game.GameStatus;
import model.game.Team;
import model.game.dto.GameDto;
import model.piece.Piece;
import model.piece.Soldier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.command.MoveCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiRepositoryImplTest {

    private Connection connection;
    private JanggiRepositoryImpl repository;

    @BeforeEach
    void setUp() throws SQLException {
        DatabaseConfig.initSchema();
        connection = DatabaseConfig.getConnection();
        repository = new JanggiRepositoryImpl(connection);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM piece");
            stmt.execute("DELETE FROM game");
        }
    }

    @Test
    void 게임을_저장하고_ID를_반환한다() {
        // when
        long gameId = repository.saveGame(Team.HAN, Map.of());

        // then
        assertThat(gameId).isNotNull();
    }

    @Test
    void 저장된_게임의_턴이_올바르다() throws SQLException {
        // when
        long gameId = repository.saveGame(Team.CHO, Map.of());

        // then
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT turn FROM game WHERE game_id = ?")) {
            stmt.setLong(1, gameId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            assertThat(rs.getString("turn")).isEqualTo("CHO");
        }
    }

    @Test
    void 게임_저장시_기물이_함께_저장된다() throws SQLException {
        // when
        long gameId = repository.saveGame(Team.HAN, Map.of(
                new Position(6, 0), new Soldier(Team.HAN),
                new Position(3, 0), new Soldier(Team.CHO)
        ));

        // then
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT COUNT(*) FROM piece WHERE game_id = ?")) {
            stmt.setLong(1, gameId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            assertThat(rs.getInt(1)).isEqualTo(2);
        }
    }

    @Test
    void 기물_이동시_위치가_업데이트된다() throws SQLException {
        // given
        long gameId = repository.saveGame(Team.HAN, Map.of());
        insertPiece(gameId, "SOLDIER", "HAN", 6, 0);

        // when
        MoveCommand move = new MoveCommand(new Position(6, 0), new Position(5, 0), Team.CHO);
        repository.updateGame(gameId, move, GameStatus.PLAYING);

        // then
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT row_idx, col_idx FROM piece WHERE game_id = ?")) {
            stmt.setLong(1, gameId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            assertThat(rs.getInt("row_idx")).isEqualTo(5);
            assertThat(rs.getInt("col_idx")).isEqualTo(0);
        }
    }

    private void insertPiece(long gameId, String pieceType, String team, int row, int col) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO piece(game_id, piece_type, team, row_idx, col_idx) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setLong(1, gameId);
            stmt.setString(2, pieceType);
            stmt.setString(3, team);
            stmt.setInt(4, row);
            stmt.setInt(5, col);
            stmt.executeUpdate();
        }
    }

    @Test
    void 기물_이동시_대상_위치의_기물이_삭제된다() throws SQLException {
        // given
        long gameId = repository.saveGame(Team.HAN, Map.of());
        insertPiece(gameId, "SOLDIER", "HAN", 6, 0);
        insertPiece(gameId, "SOLDIER", "CHO", 5, 0);

        // when
        MoveCommand move = new MoveCommand(new Position(6, 0), new Position(5, 0), Team.CHO);
        repository.updateGame(gameId, move, GameStatus.PLAYING);

        // then
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT COUNT(*) FROM piece WHERE game_id = ?")) {
            stmt.setLong(1, gameId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            assertThat(rs.getInt(1)).isEqualTo(1);
        }
    }

    @Test
    void 기물_이동시_턴이_변경된다() throws SQLException {
        // given
        long gameId = repository.saveGame(Team.HAN, Map.of());
        insertPiece(gameId, "SOLDIER", "HAN", 6, 0);

        // when
        MoveCommand move = new MoveCommand(new Position(6, 0), new Position(5, 0), Team.CHO);
        repository.updateGame(gameId, move, GameStatus.PLAYING);

        // then
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT turn FROM game WHERE game_id = ?")) {
            stmt.setLong(1, gameId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            assertThat(rs.getString("turn")).isEqualTo("CHO");
        }
    }

    @Test
    void 진행중인_최근_게임을_조회한다() {
        // given
        repository.saveGame(Team.HAN, Map.of());

        // when
        Optional<GameDto> result = repository.findRecentGame();

        // then
        assertThat(result).isPresent();
        assertThat(result.get().turn()).isEqualTo("HAN");
    }

    @Test
    void 진행중인_게임이_없으면_빈값을_반환한다() {
        // when
        Optional<GameDto> result = repository.findRecentGame();

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void 게임_상태를_변경한다() throws SQLException {
        // given
        long gameId = repository.saveGame(Team.HAN, Map.of());

        // when
        repository.updateCurrentGameStatus(gameId, GameStatus.WIN_BY_SCORE);

        // then
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT status FROM game WHERE game_id = ?")) {
            stmt.setLong(1, gameId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            assertThat(rs.getString("status")).isEqualTo("WIN_BY_SCORE");
        }
    }

    @Test
    void 종료된_게임은_최근_게임_조회에서_제외된다() {
        // given
        long gameId = repository.saveGame(Team.HAN, Map.of());
        repository.updateCurrentGameStatus(gameId, GameStatus.WIN_BY_SCORE);

        // when
        Optional<GameDto> result = repository.findRecentGame();

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void 저장된_기물을_게임ID로_조회한다() {
        // given
        Position hanPosition = new Position(6, 0);
        Position choPosition = new Position(3, 0);
        long gameId = repository.saveGame(Team.HAN, Map.of(
                hanPosition, new Soldier(Team.HAN),
                choPosition, new Soldier(Team.CHO)
        ));

        // when
        Map<Position, Piece> pieces = repository.findPiecesByGameId(gameId);

        // then
        assertThat(pieces).hasSize(2);
        assertThat(pieces.get(hanPosition)).isInstanceOf(Soldier.class);
        assertThat(pieces.get(choPosition)).isInstanceOf(Soldier.class);
    }
}
