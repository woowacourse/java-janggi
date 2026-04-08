package repository;

import config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcGameRepository implements GameRepository {
    private static final String DELETE_PIECE = "DELETE FROM piece";
    private static final String DELETE_GAME = "DELETE FROM game";
    private static final String INSERT_GAME = """
            INSERT INTO game(turn,finished,winner)
            VALUES (?,?,?)
            """;

    @Override
    public void save(SavedGame savedGame) {
        try (
                Connection connection = DatabaseConfig.getConnection()
        ) {
            connection.setAutoCommit(false);
            deleteAll(connection);
            insertGame(connection, savedGame);

            connection.commit();
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] 게임 저장에 실패했습니다.", exception);
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

    private void insertGame(Connection connection, SavedGame savedGame) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_GAME)) {
            statement.setString(1, savedGame.turn().name());
            statement.setBoolean(2, savedGame.finished());
            statement.setString(3, winnerName(savedGame));

            statement.executeUpdate();
        }
    }

    private String winnerName(SavedGame savedGame) {
        if (savedGame.winner() == null) {
            return null;
        }
        return savedGame.winner().name();
    }
}
