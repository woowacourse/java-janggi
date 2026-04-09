package repository;

import config.DatabaseConfig;

import java.sql.*;
import java.util.Optional;

public class JdbcGameRepository implements GameRepository {
    private static final String DELETE_PIECE = "DELETE FROM piece";
    private static final String DELETE_GAME = "DELETE FROM game";

    private static final String INSERT_GAME = """
            INSERT INTO game(turn,finished,winner)
            VALUES (?,?,?)
            """;

    private static final String INSERT_PIECE = """
            INSERT INTO piece(game_id, row_number, column_number,country,piece_type)
            VALUES(?,?,?,?,?)
            """;

    @Override
    public void save(SavedGame savedGame) {
        Connection connection = null;
        try {
            connection = DatabaseConfig.getConnection();
            connection.setAutoCommit(false);

            deleteAll(connection);
            long gameId = insertGame(connection, savedGame);
            insertPiece(connection, gameId, savedGame);
            connection.commit();
        } catch (SQLException exception) {
            rollback(connection);
            throw new IllegalStateException("[ERROR] 게임 저장에 실패했습니다.", exception);
        } finally {
            close(connection);
        }
    }

    @Override
    public Optional<SavedGame> find() {
        return Optional.empty();
    }

    @Override
    public void clear() {
        try (Connection connection = DatabaseConfig.getConnection()) {
            deleteAll(connection);
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] 저장 데이터 삭제에 실패했습니다.", exception);
        }
    }

    private void deleteAll(Connection connection) throws SQLException {
        try (PreparedStatement pieceStatement = connection.prepareStatement(DELETE_PIECE);
             PreparedStatement gameStatement = connection.prepareStatement(DELETE_GAME);
        ) {
            pieceStatement.executeUpdate();
            gameStatement.executeUpdate();
        }
    }

    private long insertGame(Connection connection, SavedGame savedGame) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_GAME, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, savedGame.turn().name());
            statement.setBoolean(2, savedGame.finished());
            statement.setString(3, winnerName(savedGame));
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
                throw new IllegalStateException("[ERROR] game id를 생성할 수 없습니다.");
            }
        }
    }

    private void insertPiece(Connection connection, long gameId, SavedGame savedGame) throws SQLException {
        for (SavedPiece savedPiece : savedGame.pieces()) {
            insertPiece(connection, gameId, savedPiece);
        }
    }

    private static void insertPiece(Connection connection, long gameId, SavedPiece savedPiece) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_PIECE);) {
            statement.setLong(1, gameId);
            statement.setInt(2, savedPiece.row());
            statement.setInt(3, savedPiece.col());
            statement.setString(4, savedPiece.country().name());
            statement.setString(5, savedPiece.pieceType().name());
            statement.executeUpdate();
        }
    }

    private String winnerName(SavedGame savedGame) {
        if (savedGame.winner() == null) {
            return null;
        }
        return savedGame.winner().name();
    }

    private void rollback(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.rollback();
        } catch (SQLException ignored) {}
    }

    private void close(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException ignored) {}
    }
}
