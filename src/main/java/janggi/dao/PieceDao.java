package janggi.dao;

import janggi.dao.connection.DatabaseConnection;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.piece.pieces.Piece;
import janggi.position.Position;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PieceDao {
    private final DatabaseConnection dbConnection;

    public PieceDao(DatabaseConnection databaseConnection) {
        this.dbConnection = databaseConnection;
    }

    public String addPiece(final Piece piece, final int column, final int row, final String boardId) {
        final var query = "INSERT INTO piece (piece_id, x, y, team, piece_type, board_id) VALUES (?, ?, ?, ?, ?, ?)";
        String pieceId = UUID.randomUUID().toString();

        try (final var connection = dbConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, pieceId);
            preparedStatement.setString(2, String.valueOf(column));
            preparedStatement.setString(3, String.valueOf(row));
            preparedStatement.setString(4, piece.getTeam().toString());
            preparedStatement.setString(5, piece.getType().toString());
            preparedStatement.setString(6, boardId);
            preparedStatement.executeUpdate();

            return pieceId;
        } catch (SQLException e) {
            throw new RuntimeException("장기판 생성에 실패했습니다.");
        }
    }

    public void deleteAllPieces() {
        final var query = "DELETE from piece";
        try (final var connection = dbConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("기물 삭제에 실패했습니다.");
        }
    }

    public Map<Position, Piece> findAll() {
        final var query = "SELECT * FROM piece";
        Map<Position, Piece> pieces = new HashMap<>();

        try (final var connection = dbConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int x = resultSet.getInt("x");
                int y = resultSet.getInt("y");
                Team team = Team.fromString(resultSet.getString("team"));
                PieceType pieceType = PieceType.fromString(resultSet.getString("piece_type"));

                pieces.put(new Position(x, y), pieceType.createPiece(team));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException("기물 전체 조회에 실패했습니다.");
        }
        return pieces;
    }
}
