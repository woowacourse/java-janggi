package db.dao;

import db.connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JanggiGameDao {
    private final DBConnection dbConnection;

    public JanggiGameDao(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Long save(String currentDynasty) {
        String sql = "INSERT INTO janggi_game (current_dynasty) VALUES (?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, currentDynasty);
            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
            throw new RuntimeException("[ERROR] 생성된 게임 ID를 조회하지 못했습니다.");

        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 저장에 실패하였습니다.");
        }
    }

    public List<GameDto> findNotFinishedGames() {
        String sql = "SELECT * FROM janggi_game WHERE is_finished = FALSE ORDER BY updated_at DESC";
        List<GameDto> result = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                result.add(mapToDto(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 완료되지 않은 게임 조회에 실패하였습니다.");
        }
        return result;
    }

    public Optional<GameDto> findById(Long id) {
        String sql = "SELECT * FROM janggi_game WHERE id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapToDto(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 조회에 실패하였습니다 id: " + id);
        }
        return Optional.empty();
    }

    public void updateGame(Long gameId, String currentDynasty, boolean wasLastPassed) {
        String sql = "UPDATE janggi_game SET current_dynasty = ?, was_last_passed = ? WHERE id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, currentDynasty);
            preparedStatement.setBoolean(2, wasLastPassed);
            preparedStatement.setLong(3, gameId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 상태 업데이트에 실패하였습니다. id: " + gameId);
        }
    }


    public void markAsFinished(Long gameId) {
        String sql = "UPDATE janggi_game SET is_finished = TRUE WHERE id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결에 실패했습니다.");
        }
    }

    private GameDto mapToDto(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String currentDynasty = resultSet.getString("current_dynasty");
        boolean wasLastPassed = resultSet.getBoolean("was_last_passed");
        Timestamp updatedAt = resultSet.getTimestamp("updated_at");
        return new GameDto(id, currentDynasty, wasLastPassed, updatedAt);
    }

    public record GameDto(Long id, String currentDynasty, boolean wasLastPassed, Timestamp updatedAt) {
    }
}
