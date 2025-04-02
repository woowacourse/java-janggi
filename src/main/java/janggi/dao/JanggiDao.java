package janggi.dao;

import janggi.dto.PieceDto;
import janggi.dto.PositionDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class JanggiDao {

    private static final String DATABASE_NAME = "janggi";

    private void executeQuery(String query, Consumer<PreparedStatement> queryHandler) {
        try (Connection connection = DatabaseConnector.getConnection(DATABASE_NAME);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            queryHandler.accept(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T executeQuery(String query, Function<PreparedStatement, T> queryHandler) {
        try (Connection connection = DatabaseConnector.getConnection(DATABASE_NAME);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return queryHandler.apply(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PieceDto> getPieces() {
        String query = "SELECT * FROM pieces";
        Function<PreparedStatement, List<PieceDto>> execution = (PreparedStatement preparedStatement) -> {
            try {
                ResultSet resultSet = preparedStatement.executeQuery();
                List<PieceDto> pieceDtos = new ArrayList<>();

                while (resultSet.next()) {
                    pieceDtos.add(getPieceDto(resultSet));
                }

                return pieceDtos;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };

        return executeQuery(query, execution);
    }

    private PieceDto getPieceDto(ResultSet resultSet) throws SQLException {
        return new PieceDto(
                resultSet.getString("name"),
                resultSet.getString("side"),
                resultSet.getInt("position_row"),
                resultSet.getInt("position_column"));
    }

    public PieceDto getLastMovedPiece() {
        String query = "SELECT * FROM pieces ORDER BY last_moved DESC LIMIT 1";
        Function<PreparedStatement, PieceDto> execution = (PreparedStatement preparedStatement) -> {
            try {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return getPieceDto(resultSet);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        };

        return executeQuery(query, execution);
    }

    public void addPieces(List<PieceDto> piece) {
        String query = "INSERT INTO pieces(name, side, position_row, position_column) VALUES(?, ?, ?, ?)";

        Connection connection = DatabaseConnector.getConnection(DATABASE_NAME);
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
                connection.rollback();
            } catch (SQLException e2) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updatePiece(PositionDto oldPosition, PieceDto pieceDto) {
        String deleteQuery = "DELETE FROM pieces WHERE position_row = ? AND position_column = ?";
        String insertQuery = "INSERT INTO pieces(name, side, position_row, position_column) VALUES(?, ?, ?, ?)";

        Connection connection = DatabaseConnector.getConnection(DATABASE_NAME);
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
                connection.rollback();
            } catch (SQLException e2) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteAllPieces() {
        String query = "DELETE FROM pieces";
        Consumer<PreparedStatement> execution = (PreparedStatement preparedStatement) -> {
            try {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };

        executeQuery(query, execution);
    }
}
