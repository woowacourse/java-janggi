package janggi.db.dao;

import janggi.db.DbConnector;
import janggi.db.entity.GameEntity;
import janggi.domain.common.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameDao {

    public Long save(GameEntity game) {
        String sql = "INSERT INTO games(turn, is_finished) VALUES (?, ?)";

        try (Connection connection = DbConnector.getConnection();
             PreparedStatement preparedStatement = prepareSaveStatement(connection, sql, game)) {
            preparedStatement.executeUpdate();
            return findGeneratedId(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 저장 중 오류가 발생했습니다", e);
        }
    }

    public void update(GameEntity game) {
        String sql = "UPDATE games SET turn = ?, is_finished = ? WHERE id = ?";

        try (Connection connection = DbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, game.getTurn().name());
            preparedStatement.setBoolean(2, game.isFinished());
            preparedStatement.setLong(3, game.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 업데이트 중 오류가 발생했습니다", e);
        }
    }

    public List<GameEntity> findOngoingGames() {
        String sql = "SELECT * FROM games WHERE is_finished = false ORDER BY id DESC LIMIT 5";

        try (Connection connection = DbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            return collectOngoingGames(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 진행 중인 게임 목록을 가져오는 데 실패했습니다.");
        }
    }

    public Optional<GameEntity> findById(Long id) {
        String sql = "SELECT * FROM games WHERE id = ?";
        try (Connection connection = DbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            return executeFindByIdQuery(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 조회에 실패했습니다.", e);
        }
    }

    private PreparedStatement prepareSaveStatement(Connection connection, String sql, GameEntity game)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql,
                java.sql.Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, game.getTurn().name());
        preparedStatement.setBoolean(2, game.isFinished());
        return preparedStatement;
    }

    private Long findGeneratedId(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
            return getGeneratedId(resultSet);
        }
    }

    private Long getGeneratedId(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getLong(1);
        }
        throw new RuntimeException("[ERROR] ID를 생성하지 못했습니다.");
    }

    private List<GameEntity> collectOngoingGames(ResultSet resultSet) throws SQLException {
        List<GameEntity> ongoingGames = new ArrayList<>();
        while (resultSet.next()) {
            ongoingGames.add(mapToEntity(resultSet));
        }
        return ongoingGames;
    }

    private Optional<GameEntity> executeFindByIdQuery(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            return getGameEntity(resultSet);
        }
    }

    private Optional<GameEntity> getGameEntity(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Optional.of(mapToEntity(resultSet));
        }
        return Optional.empty();
    }

    private GameEntity mapToEntity(ResultSet resultSet) throws SQLException {
        return new GameEntity(
                resultSet.getLong("id"),
                Team.valueOf(resultSet.getString("turn")),
                resultSet.getBoolean("is_finished"));
    }
}
