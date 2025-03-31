package domain.piece;

import domain.Position;
import domain.movestrategy.BasicFixedMoveStrategy;
import domain.movestrategy.BasicRangeMoveStrategy;
import domain.player.Player;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PieceDao {

    private final Connection connection;

    public PieceDao(Connection connection) {
        this.connection = connection;
    }

    public void insertPiece(int playerId, String pieceType, int column, int row, int point, int gameId) {
        final String insertPieceSql = "INSERT INTO piece (player_id, type, position_x, position_y,point,game_id) VALUES (?, ?, ?, ?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertPieceSql)) {
            preparedStatement.setInt(1, playerId);
            preparedStatement.setString(2, pieceType);
            preparedStatement.setInt(3, column);
            preparedStatement.setInt(4, row);
            preparedStatement.setInt(5, point);
            preparedStatement.setInt(6, gameId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("장기말 저장 실패");
        }
    }

    public void updatePiecePosition(Piece startPiece, Position startPosition, Position targetPosition, int gameId) {
        final var updatePieceSql = "UPDATE piece " +
                "SET position_x = ?, position_y = ? " +
                "WHERE player_id = ? AND position_x = ? AND position_y = ? AND game_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updatePieceSql)) {
            // SQL 쿼리에 파라미터 세팅
            preparedStatement.setInt(1, targetPosition.getColumn());
            preparedStatement.setInt(2, targetPosition.getRow());
            preparedStatement.setInt(3, startPiece.getPlayer().getId());
            preparedStatement.setInt(4, startPosition.getColumn());
            preparedStatement.setInt(5, startPosition.getRow());
            preparedStatement.setInt(6, gameId);

            preparedStatement.executeUpdate();  // SQL 실행
        } catch (SQLException se) {
            throw new IllegalArgumentException("장기말 이동 업데이트 실패");
        }
    }

    public void deleteTargetPiece(Piece targetPiece, Position targetPosition, int gameId) {
        final var deletePieceSql = "DELETE FROM piece WHERE player_id = ? AND position_x = ? AND position_y = ? AND game_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deletePieceSql)) {
            preparedStatement.setInt(1, targetPiece.getPlayer().getId());
            preparedStatement.setInt(2, targetPosition.getColumn());
            preparedStatement.setInt(3, targetPosition.getRow());
            preparedStatement.setInt(4, gameId);

            preparedStatement.executeUpdate();
        } catch (SQLException se) {
            throw new IllegalArgumentException("타겟 장기말 삭제 실패");
        }

    }

    public Map<Position, Piece> selectAllPieceById(int gameId, Player player) {
        final String query = "SELECT * FROM piece WHERE game_id = ? AND player_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.setInt(2, player.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            Map<Position, Piece> piecesMap = new HashMap<>();
            while (resultSet.next()) {
                int x = resultSet.getInt("position_x");
                int y = resultSet.getInt("position_y");
                Position position = new Position(y, x);
                int point = resultSet.getInt("point");
                String type = resultSet.getString("type");  // Piece 타입을 조회
                Piece piece = createPieceByType(type, player, point);
                piecesMap.put(position, piece);
            }

            return piecesMap;
        } catch (SQLException e) {
            throw new IllegalArgumentException("장기 말 정보 조회 오류", e);
        }
    }

    private Piece createPieceByType(String type, Player player, int point) {
        switch (type) {
            case "Chariot":
                return new Chariot(player, new BasicRangeMoveStrategy(), point);
            case "Cannon":
                return new Cannon(player, new BasicRangeMoveStrategy(), point);
            case "Elephant":
                return new Elephant(player, point);
            case "Horse":
                return new Horse(player, point);
            case "King":
                return new King(player, point);
            case "Guard":
                return new Guard(player, point);
            case "Pawn":
                return new Pawn(player, new BasicFixedMoveStrategy(), point);
            default:
                throw new IllegalArgumentException("알 수 없는 말 타입: " + type);
        }
    }

}
