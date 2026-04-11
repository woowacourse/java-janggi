package janggi.dao.h2;

import janggi.dao.GameDao;
import janggi.dao.JdbcDataSource;
import janggi.dao.entity.GameEntity;
import janggi.domain.game.Status;
import janggi.domain.side.Side;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class H2GameDao implements GameDao {

    private final JdbcDataSource dataSource;

    public H2GameDao(JdbcDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public GameEntity save(GameEntity game) {
        String sql = "INSERT INTO game (NAME,TURN, STATUS, WINNER) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, game.name());
            stmt.setString(2, game.turn().name());
            stmt.setString(3, game.status().name());
            stmt.setString(4, getWinnerName(game));

            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return new GameEntity(
                        generatedKeys.getInt(1),
                        game.name(),
                        game.turn(),
                        game.status(),
                        game.winner());
            }
            throw new IllegalStateException("ID 생성 실패");
        } catch (SQLException e) {
            throw new IllegalStateException("게임 저장에 실패했습니다.", e);
        }
    }

    @Override
    public Optional<GameEntity> findByName(String name) {
        String sql = "SELECT * FROM game WHERE NAME = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapResultSetToGameEntity(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException("Game 이름을 찾을 수 없습니다. name: " + name, e);
        }
    }

    @Override
    public List<String> findAllNames() {
        String sql = "SELECT name FROM game";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet resultSet = stmt.executeQuery();
            List<String> gameNames = new ArrayList<>();
            while (resultSet.next()) {
                gameNames.add(resultSet.getString("name"));
            }
            return gameNames;
        } catch (SQLException e) {
            throw new IllegalStateException("Game 이름 조회에 실패했습니다.", e);
        }
    }

    @Override
    public void updateWinner(Integer id, Side side) {
        String sql = "UPDATE game SET WINNER = ? WHERE ID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, side.name());
            stmt.setInt(2, id);

            int updated = stmt.executeUpdate();
            if (updated == 0) {
                throw new IllegalStateException("해당 id의 게임이 존재하지 않습니다. id= " + id);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("게임 승자 업데이트 실패 id=" + id, e);
        }
    }

    @Override
    public void update(GameEntity gameEntity) {
        String sql = """
                    UPDATE game
                    SET turn = ?,
                        status = ?,
                        winner = ?
                    WHERE id = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, gameEntity.turn().name());
            stmt.setString(2, gameEntity.status().name());
            stmt.setString(3,
                    gameEntity.winner() != null ? gameEntity.winner().name() : null
            );
            stmt.setLong(4, gameEntity.id());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Game 업데이트 실패 gameId: " + gameEntity.id(), e
            );
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM GAME WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private GameEntity mapResultSetToGameEntity(ResultSet resultSet) throws SQLException {
        return new GameEntity(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                Side.valueOf(resultSet.getString("turn")),
                Status.valueOf(resultSet.getString("status")),
                getWinner(resultSet)
        );
    }

    private Side getWinner(ResultSet resultSet) throws SQLException {
        if (resultSet.getString("winner") == null) {
            return null;
        }

        return Side.valueOf(resultSet.getString("winner"));
    }

    private String getWinnerName(GameEntity game) {
        if (game.winner() == null) {
            return null;
        }
        return game.winner().getName();
    }

}
