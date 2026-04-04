package db.jdbc;

import core.GameStatus;
import db.dao.GameDao;
import db.model.Game;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import pieces.Side;

public class JdbcGameDao implements GameDao {

    private final ConnectionManager connectionManager;

    public JdbcGameDao(final ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Long save(final Game game) {
        final String sql = """
            INSERT INTO game (turn_side, status)
            VALUES (?, ?)
            """;

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, game.turnSide().name());
            statement.setString(2, game.status().name());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
            throw new IllegalStateException("게임 저장 후 생성된 ID를 조회할 수 없습니다.");
        } catch (SQLException e) {
            throw new IllegalStateException("게임 저장에 실패했습니다.", e);
        }
    }

    @Override
    public void update(final Game game) {
        validateId(game);

        final String sql = """
            UPDATE game 
            SET turn_side = ?, status = ?
            WHERE id = ?;
            """;

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, game.turnSide().name());
            statement.setString(2, game.status().name());
            statement.setLong(3, game.id());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalArgumentException("수정할 게임이 존재하지 않습니다. id=" + game.id());
            }
        } catch (SQLException e) {
            throw new IllegalStateException("게임 수정에 실패했습니다.", e);
        }
    }

    @Override
    public Optional<Game> findById(final Long id) {
        final String sql = """
            SELECT id, turn_side, status
            FROM game
            WHERE id = ?
            """;

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(parseGame(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("게임 조회에 실패했습니다.", e);
        }
    }

    private void validateId(final Game game) {
        if (game.id() == null) {
            throw new IllegalArgumentException("수정할 게임 ID가 필요합니다.");
        }
    }

    private Game parseGame(final ResultSet resultSet) throws SQLException {
        return new Game(
            resultSet.getLong("id"),
            Side.valueOf(resultSet.getString("turn_side")),
            GameStatus.valueOf(resultSet.getString("status"))
        );
    }
}
