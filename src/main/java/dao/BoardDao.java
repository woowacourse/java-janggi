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

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public void saveAll(List<PieceDto> pieces) {
        final var insertSQL = "INSERT INTO piece (column_name, row_name, country, piece_type) VALUES (?, ?, ?, ?)";

        try (var connection = getConnection()) {
            try (var statement = connection.prepareStatement("DELETE FROM piece")) {
                statement.executeUpdate();
            }

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
             var rs = statement.executeQuery()) {

            while (rs.next()) {
                pieces.add(new PieceDto(
                        rs.getString("column_name"),
                        rs.getString("row_name"),
                        rs.getString("country"),
                        rs.getString("piece_type")
                ));
            }

            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException("DB 불러오기 중 오류 발생", e);
        }
    }
}
