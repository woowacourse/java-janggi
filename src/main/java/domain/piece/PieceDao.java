package domain.piece;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PieceDao {

    private final Connection connection;

    public PieceDao(Connection connection) {
        this.connection = connection;
    }

    public void insertPiece(int playerId, String pieceType, int column, int row) {
        // SQL 쿼리 작성
        final String insertPieceSql = "INSERT INTO piece (player_id, type, position_x, position_y) VALUES (?, ?, ?, ?)";

        // PreparedStatement 사용하여 DB에 삽입
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertPieceSql)) {
            // 파라미터 설정
            preparedStatement.setInt(1, playerId);
            preparedStatement.setString(2, pieceType);
            preparedStatement.setInt(3, column);
            preparedStatement.setInt(4, row);

            // 쿼리 실행
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("장기말 저장 실패");
        }
    }
}
