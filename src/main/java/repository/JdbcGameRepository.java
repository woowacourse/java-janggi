package repository;

import domain.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcGameRepository implements GameRepository {

    private final Connection connection;

    public JdbcGameRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Team currentTurn, boolean isFinished, double choScore, double hanScore) {
        deleteAll();

        String sql = "INSERT INTO game_state (current_turn, is_finished, cho_score, han_score) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, currentTurn.name());
            preparedStatement.setBoolean(2, isFinished);
            preparedStatement.setDouble(3, choScore);
            preparedStatement.setDouble(4, hanScore);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("게임의 상태를 저장하는 과정에서 문제가 발생하였습니다. " + e);
        }
    }

    @Override
    public Team findCurrentTurn() {
        String sql = "SELECT current_turn FROM game_state";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return Team.valueOf(resultSet.getString("current_turn"));
            }
            return Team.CHO;
        } catch (SQLException e) {
            throw new RuntimeException("게임의 상태를 불러오는 과정에서 문제가 발생하였습니다. " + e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM game_state";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("게임의 상태를 초기화하는 과정에서 문제가 발생하였습니다. " + e);
        }
    }

    @Override
    public boolean isNotFinished() {
        String sql = "SELECT COUNT(*) FROM game_state WHERE is_finished = false";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("게임 중단 상태 결과를 불러오는 과정에서 문제가 발생하였습니다. " + e);
        }
    }
}
