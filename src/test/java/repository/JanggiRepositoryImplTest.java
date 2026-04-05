package repository;

import config.db.DatabaseConfig;
import model.coordinate.Position;
import model.game.Team;
import model.piece.Piece;
import model.piece.Soldier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.command.MoveCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiRepositoryImplTest {

    private Connection connection;
    private JanggiRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        DatabaseConfig.initSchema();
        connection = DatabaseConfig.getConnection();
        repository = new JanggiRepositoryImpl(connection);
    }

    @Test
    void 게임을_저장하고_ID를_반환한다() {
        long gameId = repository.saveGame(Team.HAN, Map.of());

        assertThat(gameId).isNotNull();
    }

    @Test
    void 저장된_게임의_턴이_올바르다() throws SQLException {
        long gameId = repository.saveGame(Team.CHO, Map.of());

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
        long gameId = repository.saveGame(Team.HAN, Map.of(
                new Position(6, 0), new Soldier(Team.HAN),
                new Position(3, 0), new Soldier(Team.CHO)
        ));

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
        long gameId = repository.saveGame(Team.HAN, Map.of());
        insertPiece(gameId, "SOLDIER", "HAN", 6, 0);

        MoveCommand move = new MoveCommand(new Position(6, 0), new Position(5, 0), Team.CHO);
        repository.updateGame(gameId, move);

        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT row_idx, col_idx FROM piece WHERE game_id = ?")) {
            stmt.setLong(1, gameId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            assertThat(rs.getInt("row_idx")).isEqualTo(5);
            assertThat(rs.getInt("col_idx")).isEqualTo(0);
        }
    }

    @Test
    void 기물_이동시_대상_위치의_기물이_삭제된다() throws SQLException {
        long gameId = repository.saveGame(Team.HAN, Map.of());
        insertPiece(gameId, "SOLDIER", "HAN", 6, 0);
        insertPiece(gameId, "SOLDIER", "CHO", 5, 0);

        MoveCommand move = new MoveCommand(new Position(6, 0), new Position(5, 0), Team.CHO);
        repository.updateGame(gameId, move);

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
        long gameId = repository.saveGame(Team.HAN, Map.of());
        insertPiece(gameId, "SOLDIER", "HAN", 6, 0);

        MoveCommand move = new MoveCommand(new Position(6, 0), new Position(5, 0), Team.CHO);
        repository.updateGame(gameId, move);

        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT turn FROM game WHERE game_id = ?")) {
            stmt.setLong(1, gameId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            assertThat(rs.getString("turn")).isEqualTo("CHO");
        }
    }

    @Test
    void 저장된_기물을_게임ID로_조회한다() {
        Position hanPosition = new Position(6, 0);
        Position choPosition = new Position(3, 0);
        long gameId = repository.saveGame(Team.HAN, Map.of(
                hanPosition, new Soldier(Team.HAN),
                choPosition, new Soldier(Team.CHO)
        ));

        Map<Position, Piece> pieces = repository.findPiecesByGameId(gameId);

        assertThat(pieces).hasSize(2);
        assertThat(pieces.get(hanPosition)).isInstanceOf(Soldier.class);
        assertThat(pieces.get(choPosition)).isInstanceOf(Soldier.class);
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
}
