package dao;

import common.GameStatus;
import domain.player.Team;
import db.DbConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameDao {
    private static final String INITIAL_STATUS = "PROGRESS";
    private static final String INSERT_GAME_SQL = "INSERT INTO game(cho_name, han_name, current_turn, status) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_GAME_STATE_SQL = "UPDATE game SET current_turn = ?, status = ? WHERE game_id = ?";
    private static final String FIND_PROGRESS_GAME_SQL = "SELECT game_id FROM game WHERE status = ? ORDER BY created_at DESC LIMIT 1";
    private static final String FIND_ALL_PROGRESS_GAMES_SQL = "SELECT game_id, cho_name, han_name, current_turn FROM game WHERE status = ? ORDER BY created_at DESC";
    private static final String GET_CURRENT_TURN_SQL = "SELECT current_turn FROM game WHERE game_id = ?";
    private static final String GET_PLAYER_NAMES_SQL = "SELECT cho_name, han_name FROM game WHERE game_id = ?";

    public long createGame(Connection connection, String choName, String hanName) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_GAME_SQL, Statement.RETURN_GENERATED_KEYS)) {
            setCreateGameParameters(statement, choName, hanName);
            statement.executeUpdate();
            return extractGeneratedGameId(statement);
        } catch (SQLException e) {
            throw new IllegalStateException("게임 생성에 실패했습니다.", e);
        }
    }

    private void setCreateGameParameters(PreparedStatement statement, String choName, String hanName) throws SQLException {
        statement.setString(1, choName);
        statement.setString(2, hanName);
        statement.setString(3, Team.CHO.name());
        statement.setString(4, INITIAL_STATUS);
    }

    private long extractGeneratedGameId(PreparedStatement statement) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
        }
        throw new IllegalStateException("게임 생성 키를 조회하지 못했습니다.");
    }

    public void updateGameState(Connection connection, long gameId, Team currentTurn, GameStatus status) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_GAME_STATE_SQL)) {
            statement.setString(1, currentTurn.name());
            statement.setString(2, status.getValue());
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
            return executeQueryForOptionalLong(statement);
        } catch (SQLException e) {
            throw new IllegalStateException("진행 중인 게임 조회에 실패했습니다.", e);
        }
    }

    public Team getCurrentTurn(long gameId) {
        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CURRENT_TURN_SQL)) {
            statement.setLong(1, gameId);
            return executeQueryForTeam(statement)
                    .orElseThrow(() -> new IllegalStateException("해당 게임을 찾을 수 없습니다. gameId: " + gameId));
        } catch (SQLException e) {
            throw new IllegalStateException("게임 상태 조회에 실패했습니다.", e);
        }
    }

    public PlayerNames getPlayerNames(long gameId) {
        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PLAYER_NAMES_SQL)) {
            statement.setLong(1, gameId);
            return executeQueryForPlayerNames(statement)
                    .orElseThrow(() -> new IllegalStateException("해당 게임을 찾을 수 없습니다. gameId: " + gameId));
        } catch (SQLException e) {
            throw new IllegalStateException("플레이어 정보 조회에 실패했습니다.", e);
        }
    }

    private Optional<Long> executeQueryForOptionalLong(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return Optional.of(resultSet.getLong("game_id"));
            }
            return Optional.empty();
        }
    }

    private Optional<Team> executeQueryForTeam(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return Optional.of(Team.valueOf(resultSet.getString("current_turn")));
            }
            return Optional.empty();
        }
    }

    private Optional<PlayerNames> executeQueryForPlayerNames(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return Optional.of(new PlayerNames(resultSet.getString("cho_name"), resultSet.getString("han_name")));
            }
            return Optional.empty();
        }
    }

    public List<GameInfo> findAllProgressGames() {
        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_PROGRESS_GAMES_SQL)) {
            statement.setString(1, INITIAL_STATUS);
            return loadGamesFromResultSet(statement.executeQuery());
        } catch (SQLException e) {
            throw new IllegalStateException("진행 중인 게임 목록 조회에 실패했습니다.", e);
        }
    }

    private List<GameInfo> loadGamesFromResultSet(ResultSet resultSet) throws SQLException {
        List<GameInfo> games = new ArrayList<>();
        try (resultSet) {
            while (resultSet.next()) {
                games.add(new GameInfo(
                    resultSet.getLong("game_id"),
                    resultSet.getString("cho_name"),
                    resultSet.getString("han_name"),
                    resultSet.getString("current_turn")
                ));
            }
        }
        return games;
    }
}
