package repository;

import domain.country.CountryType;
import dto.TurnHistory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcTurnHistoryRepository implements TurnHistoryRepository {
    private static final String FAIL_TO_LOAD_TURN_HISTORY = "[ERROR] 턴 상태를 불러오는 데 실패했습니다.";
    private static final String FAIL_TO_CREATE_TURN_HISTORY = "[ERROR] 턴 상태 생성에 실패했습니다.";
    private static final String FAIL_TO_DELETE_TURN_HISTORY = "[ERROR] 턴 상태 삭제에 실패했습니다.";

    @Override
    public List<TurnHistory> findTurnHistoriesByGameInfoId(int gameInfoId, Connection connection) {
        List<TurnHistory> turnHistories = new ArrayList<>();
        String sql = "SELECT * FROM `turn_history` WHERE `game_info_id` = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, gameInfoId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String turn = resultSet.getString("turn");
                TurnHistory turnHistory = new TurnHistory(id, turn);
                turnHistories.add(turnHistory);
            }
            return turnHistories;
        } catch (SQLException exception) {
            throw new IllegalStateException(FAIL_TO_LOAD_TURN_HISTORY, exception);
        }
    }

    @Override
    public int saveTurnHistory(int gameInfoId, CountryType turn, Connection connection) {
        String sql = "INSERT INTO `turn_history`(`turn`, `game_info_id`) VALUES (?, ?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, turn.name());
            preparedStatement.setInt(2, gameInfoId);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException exception) {
            throw new IllegalStateException(FAIL_TO_CREATE_TURN_HISTORY, exception);
        }
    }

    @Override
    public void deleteAllTurnHistoriesByGameInfoId(int gameInfoId, Connection connection) {
        String sql = "DELETE FROM `turn_history` WHERE `game_info_id` = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, gameInfoId);

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException(FAIL_TO_DELETE_TURN_HISTORY, exception);
        }
    }
}
