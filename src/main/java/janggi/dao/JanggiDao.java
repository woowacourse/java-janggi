package janggi.dao;

import janggi.dto.PieceDto;
import janggi.dto.PositionDto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JanggiDao {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<PieceDto> getPieces() {
        String query = "SELECT * FROM pieces";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PieceDto> pieceDtos = new ArrayList<>();

            while (resultSet.next()) {
                pieceDtos.add(new PieceDto(
                        resultSet.getString("name"),
                        resultSet.getString("side"),
                        resultSet.getInt("position_row"),
                        resultSet.getInt("position_column")
                ));
            }

            return pieceDtos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PieceDto getLastMovedPiece() {
        String query = "SELECT * FROM pieces ORDER BY last_moved DESC LIMIT 1";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new PieceDto(
                        resultSet.getString("name"),
                        resultSet.getString("side"),
                        resultSet.getInt("position_row"),
                        resultSet.getInt("position_column")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void addPieces(List<PieceDto> piece) {
        String query = "INSERT INTO pieces(name, side, position_row, position_column) VALUES(?, ?, ?, ?)";

        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (PieceDto pieceDto : piece) {
                preparedStatement.setString(1, pieceDto.name());
                preparedStatement.setString(2, pieceDto.side());
                preparedStatement.setInt(3, pieceDto.row());
                preparedStatement.setInt(4, pieceDto.column());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e2) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updatePiece(PositionDto oldPosition, PieceDto pieceDto) {
        String deleteQuery = "DELETE FROM pieces WHERE position_row = ? AND position_column = ?";
        String insertQuery = "INSERT INTO pieces(name, side, position_row, position_column) VALUES(?, ?, ?, ?)";

        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedDeleteStatement = connection.prepareStatement(deleteQuery);

            preparedDeleteStatement.setInt(1, oldPosition.row());
            preparedDeleteStatement.setInt(2, oldPosition.column());

            preparedDeleteStatement.executeUpdate();

            PreparedStatement preparedInsertStatement = connection.prepareStatement(insertQuery);

            preparedInsertStatement.setString(1, pieceDto.name());
            preparedInsertStatement.setString(2, pieceDto.side());
            preparedInsertStatement.setInt(3, pieceDto.row());
            preparedInsertStatement.setInt(4, pieceDto.column());

            preparedInsertStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e2) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteAllPieces() {
        String query = "DELETE FROM pieces";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
