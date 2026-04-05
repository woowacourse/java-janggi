package dao;

import domain.player.Team;
import infra.db.DbConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameRoom {
    private static final String INITIAL_STATUS = "PROGRESS";
    private static final String INSERT_GAME_SQL = "INSERT INTO game(cho_name, han_name, current_turn, status) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_GAME_STATE_SQL = "UPDATE game SET current_turn = ?, status = ? WHERE game_id = ?";
    private static final String FIND_PROGRESS_GAME_SQL = "SELECT game_id FROM game WHERE status = ? ORDER BY created_at DESC LIMIT 1";
    private static final String FIND_ALL_PROGRESS_GAMES_SQL = "SELECT game_id, cho_name, han_name, current_turn FROM game WHERE status = ? ORDER BY created_at DESC";
    private static final String GET_CURRENT_TURN_SQL = "SELECT current_turn FROM game WHERE game_id = ?";
    private static final String GET_PLAYER_NAMES_SQL = "SELECT cho_name, han_name FROM game WHERE game_id = ?";

    public long createGame(String choName, String hanName) {
        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_GAME_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, choName);
            statement.setString(2, hanName);
            statement.setString(3, Team.CHO.name());
            statement.setString(4, INITIAL_STATUS);
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
            throw new IllegalStateException("게임 생성 키를 조회하지 못했습니다.");
        } catch (SQLException e) {
            throw new IllegalStateException("게임 생성에 실패했습니다.", e);
        }
    }

    public void updateGameState(long gameId, Team currentTurn, String status) {
        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_GAME_STATE_SQL)) {
            statement.setString(1, currentTurn.name());
            statement.setString(2, status);
            statement.setLong(3, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("게임 상태 업데이트에 실패했습니다.", e);
        }
    }

    public Optional<Long> findProgressGame() {
        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PROGRESS_GAME_SQL)) {
            statement.setString(1, INITIAL_STATUS);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getLong("game_id"));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException("진행 중인 게임 조회에 실패했습니다.", e);
        }
    }

    public Team getCurrentTurn(long gameId) {
        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CURRENT_TURN_SQL)) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Team.valueOf(resultSet.getString("current_turn"));
                }
            }
            throw new IllegalStateException("해당 게임을 찾을 수 없습니다. gameId: " + gameId);
        } catch (SQLException e) {
            throw new IllegalStateException("게임 상태 조회에 실패했습니다.", e);
        }
    }

    public PlayerNames getPlayerNames(long gameId) {
        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PLAYER_NAMES_SQL)) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String choName = resultSet.getString("cho_name");
                    String hanName = resultSet.getString("han_name");
                    return new PlayerNames(choName, hanName);
                }
            }
            throw new IllegalStateException("해당 게임을 찾을 수 없습니다. gameId: " + gameId);
        } catch (SQLException e) {
            throw new IllegalStateException("플레이어 정보 조회에 실패했습니다.", e);
        }
    }

    public List<GameInfo> findAllProgressGames() {
        List<GameInfo> games = new ArrayList<>();
        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_PROGRESS_GAMES_SQL)) {
            statement.setString(1, INITIAL_STATUS);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long gameId = resultSet.getLong("game_id");
                    String choName = resultSet.getString("cho_name");
                    String hanName = resultSet.getString("han_name");
                    String currentTurn = resultSet.getString("current_turn");
                    games.add(new GameInfo(gameId, choName, hanName, currentTurn));
                }
            }
            return games;
        } catch (SQLException e) {
            throw new IllegalStateException("진행 중인 게임 목록 조회에 실패했습니다.", e);
        }
    }
}
