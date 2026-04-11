package repository;

import domain.country.CountryType;
import dto.GameInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcGameInfoRepository implements GameInfoRepository {
    private static final String FAIL_TO_LOAD_ALL_BOARD = "[ERROR] 모든 보드를 불러오는 데 실패했습니다.";
    private static final String FAIL_TO_LOAD_BOARD = "[ERROR] 보드를 불러오는 데 실패했습니다.";
    private static final String FAIL_TO_CREATE_BOARD = "[ERROR] 보드 생성에 실패했습니다.";
    private static final String FAIL_TO_UPDATE_BOARD = "[ERROR] 보드 업데이트에 실패했습니다.";
    private static final String FAIL_TO_DELETE_BOARD = "[ERROR] 보드 삭제에 실패했습니다.";

    @Override
    public List<GameInfo> findAllGameInfos(Connection connection) {
        String sql = "SELECT * FROM `game_info`";
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            List<GameInfo> gameInfos = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String turn = resultSet.getString("turn");
                GameInfo gameInfo = new GameInfo(id, turn);
                gameInfos.add(gameInfo);
            }
            return gameInfos;
        } catch (SQLException exception) {
            throw new IllegalStateException(FAIL_TO_LOAD_ALL_BOARD);
        }
    }

    @Override
    public GameInfo findGameInfoById(int id, Connection connection) {
        String sql = "SELECT * FROM `game_info` WHERE `id` = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            String turn = resultSet.getString("turn");

            return new GameInfo(id, turn);
        } catch (SQLException exception) {
            throw new IllegalStateException(FAIL_TO_LOAD_BOARD, exception);
        }
    }

    @Override
    public int saveGameInfo(Connection connection) {
        String sql = "INSERT INTO `game_info`(`turn`) VALUES (?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, CountryType.CHO.toString());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException exception) {
            throw new IllegalStateException(FAIL_TO_CREATE_BOARD, exception);
        }
    }

    @Override
    public void updateGameInfo(CountryType countryType, int id, Connection connection) {
        String sql = "UPDATE `game_info` SET `turn` = ? WHERE `id` = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, countryType.toString());
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException(FAIL_TO_UPDATE_BOARD, exception);
        }
    }

    @Override
    public void deleteGameInfo(int id, Connection connection) {
        String sql = "DELETE FROM `game_info` WHERE `id` = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException(FAIL_TO_DELETE_BOARD, exception);
        }
    }

}
