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
import java.util.Map;

public class JdbcGameInfoRepository implements GameInfoRepository {
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
                double cho_score = resultSet.getDouble("cho_score");
                double han_score = resultSet.getDouble("han_score");
                GameInfo gameInfo = new GameInfo(id, turn, cho_score, han_score);
                gameInfos.add(gameInfo);
            }
            return gameInfos;
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 모든 보드를 불러오는 데 실패했습니다.");
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

            if (!resultSet.next()) {
                throw new IllegalArgumentException("[ERROR] 해당 번호의 board가 없습니다.");
            }

            String turn = resultSet.getString("turn");
            double cho_score = resultSet.getDouble("cho_score");
            double han_score = resultSet.getDouble("han_score");

            return new GameInfo(id, turn, cho_score, han_score);
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드를 불러오는 데 실패했습니다.", e);
        }
    }

    @Override
    public int saveGameInfo(Connection connection) {
        String sql = "INSERT INTO `game_info`(`turn`, `cho_score`, `han_score`) VALUES (?, ?, ?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, CountryType.CHO.toString());
            preparedStatement.setDouble(2, CountryType.CHO.getInitScore());
            preparedStatement.setDouble(3, CountryType.HAN.getInitScore());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (!resultSet.next()) {
                throw new IllegalStateException("[ERROR] 생성된 보드 ID를 가져오는 데 실패했습니다.");
            }
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 생성에 실패했습니다.", e);
        }
    }

    @Override
    public void updateGameInfo(CountryType countryType, Map<CountryType, Double> scores, int id,
                               Connection connection) {
        String sql = "UPDATE `game_info` SET `turn` = ?, `cho_score` = ?, `han_score` = ? WHERE `id` = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, countryType.toString());
            preparedStatement.setDouble(2, scores.get(CountryType.CHO));
            preparedStatement.setDouble(3, scores.get(CountryType.HAN));
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 업데이트에 실패했습니다.", e);
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
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 삭제에 실패했습니다.", e);
        }
    }

}
