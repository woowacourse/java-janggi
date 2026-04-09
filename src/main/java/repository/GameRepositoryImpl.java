package repository;

import domain.Game;
import domain.board.TableSetting;
import dto.GameInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class GameRepositoryImpl implements GameRepository {
    private static final String FAILED_SAVE_GAME_DATA = "[ERROR] DB에 게임 데이터를 저장하는 도중, 오류가 발생했습니다.";
    private static final String FAILED_FIND_LATEST_GAME_DATA = "[ERROR] DB에서 최근 게임 데이터를 조회하는 도중, 오류가 발생했습니다.";
    private static final String FAILED_UPDATE_GAME_FINISHED = "[ERROR] DB에서 게임 종료 처리 도중, 오류가 발생했습니다.";

    @Override
    public Optional<Long> save(Connection connection, Game game) {
        String sql = "INSERT INTO game(created_at, cho_table_setting, han_table_setting) VALUES (?, ?, ?)";

        try (
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            GameInfo gameInfo = game.toSaveValues();
            statement.setObject(1, gameInfo.createdAt());
            statement.setString(2, gameInfo.choTableSetting().name());
            statement.setString(3, gameInfo.hanTableSetting().name());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return Optional.of(generatedKeys.getLong(1));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(FAILED_SAVE_GAME_DATA, exception);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Game> findLatest(Connection connection) {
        String sql = "SELECT  * FROM game WHERE is_finished = FALSE ORDER BY game_id DESC LIMIT 1";

        try (
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long gameId = resultSet.getLong("game_id");
                TableSetting choTableSetting = TableSetting.valueOf(resultSet.getString("cho_table_setting"));
                TableSetting hanTableSetting = TableSetting.valueOf(resultSet.getString("han_table_setting"));
                return Optional.of(new Game(gameId, choTableSetting, hanTableSetting));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(FAILED_FIND_LATEST_GAME_DATA, exception);
        }
        return Optional.empty();
    }

    @Override
    public void finishedGame(Connection connection, Long gameId) {
        String sql = "UPDATE game SET is_finished = TRUE WHERE game_id = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, gameId);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException(FAILED_UPDATE_GAME_FINISHED, exception);
        }
    }
}
