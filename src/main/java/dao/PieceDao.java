package dao;

import domain.board.Board;
import domain.board.BoardLocation;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class PieceDao {

    private final JdbcConnection jdbcConnection;

    public PieceDao(JdbcConnection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
    }

    public void initializePieceIfNotExists(Board board) {
        Map<BoardLocation, Piece> pieces = board.getPieces();
        String query = """
                INSERT INTO piece (piece_type, team, location_x, location_y) 
                VALUES (?, ?, ?, ?)
                """;
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            for (Entry<BoardLocation, Piece> entry : pieces.entrySet()) {
                stmt.setString(1, entry.getValue().getType().name());
                stmt.setString(2, entry.getValue().getTeam().name());
                stmt.setInt(3, entry.getKey().x());
                stmt.setInt(4, entry.getKey().y());
                stmt.executeUpdate();
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Board> findByAllAlivePieces() {
        createPieceTableIfNotExists();
        String query = "SELECT piece_type, team, location_x, location_y FROM piece";
        Map<BoardLocation, Piece> pieces = new HashMap<>();
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String pieceType = rs.getString("piece_type");
                    String team = rs.getString("team");
                    int locationX = rs.getInt("location_x");
                    int locationY = rs.getInt("location_y");
                    pieces.put(new BoardLocation(locationX, locationY), createPieceByType(pieceType, team));
                }
            }
            return Optional.of(new Board(pieces));
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBoard(BoardLocation current, BoardLocation destination) {
        String updatePieceQuery = """
                UPDATE piece SET location_x = ?, location_y = ?
                WHERE location_x = ? AND location_y = ?
                """;
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(updatePieceQuery)) {
            stmt.setInt(1, destination.x());
            stmt.setInt(2, destination.y());
            stmt.setInt(3, current.x());
            stmt.setInt(4, current.y());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBoard(BoardLocation destination) {
        String deletePieceQuery = "DELETE FROM piece WHERE location_x = ? AND location_y = ?";
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(deletePieceQuery)) {
            stmt.setInt(1, destination.x());
            stmt.setInt(2, destination.y());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createPieceTableIfNotExists() {
        String query = """
                CREATE TABLE IF NOT EXISTS piece (
                piece_id INT AUTO_INCREMENT PRIMARY KEY,"piece_type VARCHAR(50) NOT NULL,
                team VARCHAR(10) NOT NULL, location_x INT NOT NULL, location_y INT NOT NULL)
                """;
        try (Connection connection = jdbcConnection.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Piece createPieceByType(String pieceType, String teamName) throws SQLException {
        Team team = Team.getTeamByName(teamName);
        return PieceType.valueOf(pieceType).createPiece(team);
    }
}
