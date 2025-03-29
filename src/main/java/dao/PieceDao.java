package dao;

import domain.board.Board;
import domain.board.BoardLocation;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.Scholar;
import domain.piece.Score;
import domain.piece.Team;
import dto.BoardDto;
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
    private final Connection connection = JdbcConnection.getInstance();

    public void initializePieceIfNotExists(Board board) {
        Map<BoardLocation, Piece> pieces = board.getPieces();
        String query = "INSERT INTO piece ("
                + "piece_type,"
                + "team,"
                + "location_x,"
                + "location_y) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
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

    public Optional<BoardDto> findByAllAlivePieces() {
        createPieceTableIfNotExists();
        String query = "SELECT piece_type, team, location_x, location_y FROM piece";
        Map<BoardLocation, Piece> pieces = new HashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String pieceType = rs.getString("piece_type");
                    String team = rs.getString("team");
                    int locationX = rs.getInt("location_x");
                    int locationY = rs.getInt("location_y");
                    pieces.put(new BoardLocation(locationX, locationY), createPieceByType(pieceType, team));
                }
            }
            return Optional.of(new BoardDto(pieces));
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBoard(BoardLocation current, BoardLocation destination) {
        String updatePieceQuery = "UPDATE piece SET location_x = ?, location_y = ? " +
                "WHERE location_x = ? AND location_y = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updatePieceQuery)) {
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
        try (PreparedStatement stmt = connection.prepareStatement(deletePieceQuery)) {
            stmt.setInt(1, destination.x());
            stmt.setInt(2, destination.y());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createPieceTableIfNotExists() {
        String query = "CREATE TABLE IF NOT EXISTS piece (" +
                "piece_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "piece_type VARCHAR(50) NOT NULL, " +
                "team VARCHAR(10) NOT NULL, " +
                "location_x INT NOT NULL, " +
                "location_y INT NOT NULL" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Piece createPieceByType(String pieceType, String teamName) throws SQLException {
        Piece piece;
        Team team = Team.getTeamByName(teamName);
        if (team == Team.HAN) {
            switch (pieceType) {
                case "CANNON" -> piece = new Cannon(team);
                case "ELEPHANT" -> piece = new Elephant(team);
                case "SCHOLAR" -> piece = new Scholar(team);
                case "HORSE" -> piece = new Horse(team);
                case "KING" -> piece = new King(team, new Score(1.5));
                case "PAWN" -> piece = new Pawn(team);
                case "CHARIOT" -> piece = new Chariot(team);
                default -> throw new IllegalArgumentException("유효하지 않은 타입입니다.");
            }
        } else {
            switch (pieceType) {
                case "CANNON" -> piece = new Cannon(team);
                case "ELEPHANT" -> piece = new Elephant(team);
                case "SCHOLAR" -> piece = new Scholar(team);
                case "HORSE" -> piece = new Horse(team);
                case "KING" -> piece = new King(team, new Score(0));
                case "PAWN" -> piece = new Pawn(team);
                case "CHARIOT" -> piece = new Chariot(team);
                default -> throw new IllegalArgumentException("유효하지 않은 타입입니다.");
            }
        }
        return piece;
    }
}
