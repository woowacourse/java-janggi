package data;

import domain.piece.Camp;
import domain.piece.PieceType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {

    public void insertPiece(Connection connection, Long boardId, String pieceType, String camp, int column, int row) {
        String sql = "INSERT INTO pieces (`board_id`,`type`,`camp`,`column`, `row`) VALUES (?,?,?,?,?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setLong(1, boardId);
            preparedStatement.setString(2, pieceType);
            preparedStatement.setString(3, camp);
            preparedStatement.setInt(4, column);
            preparedStatement.setInt(5, row);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 삽입에 실패했습니다.", e);
        }
    }

    public List<PieceDto> getAllPieceByBoard(Connection connection, Long boardId) {
        String sql = "SELECT `column`, `row`, `type`, `camp` FROM pieces WHERE `board_id` = (?)";
        List<PieceDto> pieces = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, boardId);

            try (ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                while (resultSet.next()) {
                    int column = resultSet.getInt("column");
                    int row = resultSet.getInt("row");
                    PieceType type = PieceType.valueOf(resultSet.getString("type"));
                    Camp camp = Camp.valueOf(resultSet.getString("camp"));

                    pieces.add(new PieceDto(column, row, type, camp));
                }
                return pieces;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 조회에 실패했습니다.", e);
        }
    }

    public void deleteAllByBoard(Connection connection, Long boardId) {
        String sql = "DELETE FROM pieces WHERE `board_id` = (?)";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setLong(1, boardId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 삭제에 실패했습니다.", e);
        }
    }
}
