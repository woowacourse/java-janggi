package janggi.db.dao;

import janggi.db.DbConnector;
import janggi.db.entity.GameEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDao {

    public Long save(GameEntity game) {
        String sql = "INSERT INTO games(turn, is_finished) VALUES (?, ?)";

        try (Connection connection = DbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql,
                     java.sql.Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, game.getTurn().name());
            preparedStatement.setBoolean(2, game.isFinished());
            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 저장 중 오류가 발생했습니다", e);
        }
        throw new RuntimeException("[ERROR] ID를 생성하지 못했습니다.");
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
}
