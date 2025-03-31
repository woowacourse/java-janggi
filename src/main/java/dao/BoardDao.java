package dao;

import dto.PieceDto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {

    private static final String URL = "jdbc:mysql://localhost:13306/janggi?useSSL=false&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public void saveAll(List<PieceDto> pieces) {
        final var insertSQL = "INSERT INTO piece (column_name, row_name, country, piece_type) VALUES (?, ?, ?, ?)";
        deleteAll();
        try (var connection = getConnection()) {

            try (var preparedStatement = connection.prepareStatement(insertSQL)) {
                for (PieceDto piece : pieces) {
                    preparedStatement.setString(1, piece.column());
                    preparedStatement.setString(2, piece.row());
                    preparedStatement.setString(3, piece.country());
                    preparedStatement.setString(4, piece.pieceType());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 저장 중 오류 발생", e);
        }
    }

    public List<PieceDto> loadAll() {
        final var selectSQL = "SELECT column_name, row_name, country, piece_type FROM piece";
        List<PieceDto> pieces = new ArrayList<>();

        try (var connection = getConnection();
             var statement = connection.prepareStatement(selectSQL);
             var record = statement.executeQuery()) {

            while (record.next()) {
                pieces.add(new PieceDto(
                        record.getString("column_name"),
                        record.getString("row_name"),
                        record.getString("country"),
                        record.getString("piece_type")
                ));
            }

            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException("DB 불러오기 중 오류 발생", e);
        }
    }

    public void deleteAll() {
        final var deleteSQL = "DELETE FROM piece";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("DB 삭제 중 오류 발생", e);
        }

    }

}
