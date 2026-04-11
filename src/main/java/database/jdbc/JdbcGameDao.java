package database.jdbc;

import database.dao.GameDao;
import database.dto.GameDto;
import domain.game.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class JdbcGameDao implements GameDao {
    private final DatabaseConnector connector;

    public JdbcGameDao(DatabaseConnector connector) {
        this.connector = connector;
    }

    @Override
    public int createGame(Team initialTurn) {
        String sql = "INSERT INTO game (status, current_turn) VALUES ('PLAYING', ?)";
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, initialTurn.name());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
            throw new RuntimeException("게임 생성 후 ID를 가져오지 못했습니다.");
        } catch (SQLException e) {
            throw new RuntimeException("게임 생성에 실패했습니다.", e);
        }
    }

    @Override
    public Optional<GameDto> findLatestPlaying() {
        String sql = "SELECT id, status, current_turn FROM game WHERE status = 'PLAYING' ORDER BY id DESC LIMIT 1";
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                GameDto entity = new GameDto(
                        resultSet.getInt("id"),
                        Team.valueOf(resultSet.getString("current_turn"))
                );
                return Optional.of(entity);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("진행중인 게임 조회에 실패했습니다.", e);
        }
    }

    @Override
    public void updateTurn(Connection connection, int gameId, Team team) {
        String sql = "UPDATE game SET current_turn = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, team.name());
            statement.setInt(2, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("턴 업데이트에 실패했습니다. gameId: " + gameId, e);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM game WHERE id = ?";
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("게임 삭제에 실패했습니다. gameId: " + id, e);
        }
    }
}
