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

    public boolean hasGameId(int gameId) {
        String query = "SELECT * FROM games WHERE id = ?";
        Function<PreparedStatement, Boolean> execution = (PreparedStatement preparedStatement) -> {
            try {
                preparedStatement.setInt(1, gameId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return false;
        };

        return executeQuery(query, execution);
    }

    public List<PieceDto> getPieces(int gameId) {
        String query = "SELECT * FROM pieces WHERE game_id = ?";
        Function<PreparedStatement, List<PieceDto>> execution = (PreparedStatement preparedStatement) -> {
            try {
                preparedStatement.setInt(1, gameId);
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

    public void addGameId(int id) {
        String query = "INSERT INTO games(id, current_turn) VALUES(?, 'CHO')";
        Consumer<PreparedStatement> execution = (PreparedStatement preparedStatement) -> {
            try {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };

        executeQuery(query, execution);
    }

    public void addPieces(int gameId, List<PieceDto> piece) {
        String query = "INSERT INTO pieces(name, side, position_row, position_column, game_id) VALUES(?, ?, ?, ?, ?)";

        Connection connection = DatabaseConnector.getConnection(DATABASE_NAME);
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (PieceDto pieceDto : piece) {
                preparedStatement.setString(1, pieceDto.name());
                preparedStatement.setString(2, pieceDto.side());
                preparedStatement.setInt(3, pieceDto.row());
                preparedStatement.setInt(4, pieceDto.column());
                preparedStatement.setInt(5, gameId);
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

    public String getCurrentTurn(int gameId) {
        String query = "SELECT * FROM games WHERE id = ?";
        Function<PreparedStatement, String> execution = (PreparedStatement preparedStatement) -> {
            try {
                preparedStatement.setInt(1, gameId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("current_turn");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        };

        return executeQuery(query, execution);
    }

    public void updatePiece(int gameId, PositionDto oldPositionDto, PositionDto newPositionDto) {
        String deleteQuery = "DELETE FROM pieces WHERE position_row = ? AND position_column = ? AND game_id = ?";
        String updateQuery = "UPDATE pieces SET position_row = ?, position_column = ? WHERE position_row = ? AND position_column = ? AND game_id = ?";

        Connection connection = DatabaseConnector.getConnection(DATABASE_NAME);
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedDeleteStatement = connection.prepareStatement(deleteQuery);

            preparedDeleteStatement.setInt(1, newPositionDto.row());
            preparedDeleteStatement.setInt(2, newPositionDto.column());
            preparedDeleteStatement.setInt(3, gameId);

            preparedDeleteStatement.executeUpdate();

            PreparedStatement preparedUpdateStatement = connection.prepareStatement(updateQuery);

            preparedUpdateStatement.setInt(1, newPositionDto.row());
            preparedUpdateStatement.setInt(2, newPositionDto.column());
            preparedUpdateStatement.setInt(3, oldPositionDto.row());
            preparedUpdateStatement.setInt(4, oldPositionDto.column());
            preparedUpdateStatement.setInt(5, gameId);

            preparedUpdateStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateCurrentTurn(int gameId, String currentTurn) {
        String query = "UPDATE games SET current_turn = ? WHERE id = ?";
        Consumer<PreparedStatement> execution = (PreparedStatement preparedStatement) -> {
            try {
                preparedStatement.setString(1, currentTurn);
                preparedStatement.setInt(2, gameId);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };

        executeQuery(query, execution);
    }

    public void deleteGamePieces(int gameId) {
        String query = "DELETE FROM pieces WHERE game_id = ?";
        Consumer<PreparedStatement> execution = (PreparedStatement preparedStatement) -> {
            try {
                preparedStatement.setInt(1, gameId);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };

        executeQuery(query, execution);
    }

    public void deleteGameId(int gameId) {
        String query = "DELETE FROM games WHERE id = ?";
        Consumer<PreparedStatement> execution = (PreparedStatement preparedStatement) -> {
            try {
                preparedStatement.setInt(1, gameId);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };

        executeQuery(query, execution);
    }
}
