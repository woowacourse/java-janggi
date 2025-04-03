package dao;

import config.JdbcUtils;
import dto.PieceDto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {

    public void saveAll(List<PieceDto> pieces) {
        final var insertSQL = "INSERT INTO piece (column_name, row_name, country, piece_type) VALUES (?, ?, ?, ?)";
        deleteAll();

        try (var connection = JdbcUtils.getConnection();
             var preparedStatement = connection.prepareStatement(insertSQL)) {

            for (PieceDto piece : pieces) {
                preparedStatement.setString(1, piece.column());
                preparedStatement.setString(2, piece.row());
                preparedStatement.setString(3, piece.country());
                preparedStatement.setString(4, piece.pieceType());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("DB 저장 중 오류 발생", e);
        }
    }

    public List<PieceDto> loadAll() {
        final var selectSQL = "SELECT column_name, row_name, country, piece_type FROM piece";
        List<PieceDto> pieces = new ArrayList<>();

        try (var connection = JdbcUtils.getConnection();
             var statement = connection.prepareStatement(selectSQL);
             var resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                pieces.add(new PieceDto(
                        resultSet.getString("column_name"),
                        resultSet.getString("row_name"),
                        resultSet.getString("country"),
                        resultSet.getString("piece_type")
                ));
            }

            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException("DB 불러오기 중 오류 발생", e);
        }
    }

    public void deleteAll() {
        final var deleteSQL = "DELETE FROM piece";
        try (var connection = JdbcUtils.getConnection();
             var preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB 삭제 중 오류 발생", e);
        }
    }
}
