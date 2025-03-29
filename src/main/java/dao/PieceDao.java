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

    public void createPieceTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS piece (" +
                "piece_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "piece_type VARCHAR(50) NOT NULL, " +
                "team VARCHAR(10) NOT NULL, " +
                "location_x INT NOT NULL, " +
                "location_y INT NOT NULL, " +
                "is_alive BOOLEAN NOT NULL, " +
                "PRIMARY KEY (game_id, piece_type, team, location_x, location_y))";

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("테이블이 생성되었습니다.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializePieceIfNotExists(Board board) {
        Map<BoardLocation, Piece> pieces = board.getPieces();
        String query = "INSERT INTO piece ("
                + "piece_type,"
                + "team,"
                + "location_x,"
                + "location_y,"
                + "is_alive) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (Entry<BoardLocation, Piece> entry : pieces.entrySet()) {
                stmt.setString(1, entry.getValue().getType().name());
                stmt.setString(2, entry.getValue().getTeam().name());
                stmt.setInt(3, entry.getKey().x());
                stmt.setInt(4, entry.getKey().y());
                stmt.setBoolean(5, true);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<BoardDto> findByAllAlivePieces() {
        String query = "SELECT piece_type,team, location_x, location_y "
                + "FROM piece_status WHERE game_id = ? AND is_alive = true";
        Map<BoardLocation, Piece> pieces = new HashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.wasNull()) {
                    return Optional.empty();
                }
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

    private Piece createPieceByType(String pieceType, String teamName) throws SQLException {
        Piece piece;
        Team team = Team.getTeamByName(teamName);
        if (team == Team.HAN) {
            switch (pieceType) {
                case "Cannon" -> piece = new Cannon(team);
                case "Elephant" -> piece = new Elephant(team);
                case "Guard" -> piece = new Scholar(team);
                case "Horse" -> piece = new Horse(team);
                case "King" -> piece = new King(team, new Score(1.5));
                case "Pawn" -> piece = new Pawn(team);
                case "Chariot" -> piece = new Chariot(team);
                default -> throw new IllegalArgumentException("유효하지 않은 타입입니다.");
            }
        } else {
            switch (pieceType) {
                case "Cannon" -> piece = new Cannon(team);
                case "Elephant" -> piece = new Elephant(team);
                case "Guard" -> piece = new Scholar(team);
                case "Horse" -> piece = new Horse(team);
                case "King" -> piece = new King(team, new Score(0));
                case "Pawn" -> piece = new Pawn(team);
                case "Chariot" -> piece = new Chariot(team);
                default -> throw new IllegalArgumentException("유효하지 않은 타입입니다.");
            }
        }
        return piece;
    }

}
