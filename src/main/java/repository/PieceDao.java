package repository;

import domain.board.BoardFactory;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class PieceDao {

    private final DBConnection dbConnection;

    public PieceDao(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void saveAll(long gameId, Map<Position, Piece> pieces) {

        deleteByGameId(gameId); // 기존 기물 데이터 싹 비우기

        String sql = "INSERT INTO piece (game_id, team, piece_type, x, y) VALUES (?, ?, ?, ?, ?)";  // 새로 전부 저장

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();

                preparedStatement.setLong(1, gameId);
                preparedStatement.setString(2, piece.getTeam().name());
                preparedStatement.setString(3, piece.getPieceType().name());
                preparedStatement.setInt(4, position.x());
                preparedStatement.setInt(5, position.y());
                preparedStatement.addBatch(); // 여러 건을 한 번에 보내기 위해 배치 사용
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Position, Piece> findByGameId(long gameId) {
        String sql = "SELECT team, piece_type, x, y FROM piece WHERE game_id = ?";
        Map<Position, Piece> pieces = new java.util.HashMap<>();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, gameId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Team team = Team.valueOf(resultSet.getString("team"));
                    PieceType type = PieceType.valueOf(resultSet.getString("piece_type"));
                    int x = resultSet.getInt("x");
                    int y = resultSet.getInt("y");

                    Position position = new Position(x, y);
                    Piece piece = BoardFactory.createPiece(type, team);
                    pieces.put(position, piece);
                }
            }
            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException("기물 불러오기 실패", e);
        }
    }

    private void deleteByGameId(long gameId) {
        String sql = "DELETE FROM piece WHERE game_id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
