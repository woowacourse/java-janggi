package dao;

import domain.player.Player;
import domain.player.Score;
import domain.player.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public final class PlayerDAO {
    private final Connector connector;
    private final AtomicInteger counter = new AtomicInteger(0);

    public PlayerDAO(final Connector connector) {
        this.connector = Objects.requireNonNull(connector, "connecter가 null일 수 없습니다.");
        getNextId();
    }

    public int createWithGameId(final Team team, final int gameId) {
        final String query = "INSERT INTO player(team,score,is_turn,game_id) VALUES(?, ?, ?, ?)";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, team.name());
            preparedStatement.setDouble(2, Score.generateInitialScoreByTeam(team).value());
            preparedStatement.setBoolean(3, team.isFirst());
            preparedStatement.setInt(4, gameId);
            preparedStatement.executeUpdate();
            return counter.incrementAndGet();
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스에서 플레이어를 생성하는 데 실패했습니다: " + e);
        }
    }

    public void updateBatch(final List<Player> players) {
        final String query = "UPDATE player SET score = ?, is_turn = ? WHERE id = ?";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (Player player : players) {
                preparedStatement.setDouble(1, player.getScore().value());
                preparedStatement.setBoolean(2, player.isTurn());
                preparedStatement.setInt(3, player.getId());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스에서 플레이어를 수정하는 데 실패했습니다: " + e);
        }
    }

    public Player findById(final int id) {
        final String query = "SELECT * FROM player WHERE id = ?";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Player(
                        resultSet.getInt("id"),
                        Team.valueOf(resultSet.getString("team")),
                        new Score(resultSet.getDouble("score")),
                        resultSet.getBoolean("is_turn")
                );
            }
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스에서 플레이어를 조회하는 데 실패했습니다: " + e);
        }
        throw new RuntimeException("플레이어 정보를 찾을 수 없습니다: " + id);
    }

    public List<Player> findAllByGameId(final int gameId) {
        final String query = "SELECT * FROM player WHERE game_id = ?";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, gameId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            return convertResultSetToPlayers(resultSet);
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스에서 복수의 플레이어를 조회하는 데 실패했습니다: " + e);
        }
    }

    private void getNextId() {
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT MAX(id) AS last_id FROM player");
             final ResultSet resultSet = preparedStatement.executeQuery()) {
            incrementLastId(resultSet);
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스에서 플레이어의 Id를 조회하는 데 실패했습니다: " + e);
        }
    }

    private void incrementLastId(final ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            counter.addAndGet(resultSet.getInt("last_id"));
        }
    }

    private List<Player> convertResultSetToPlayers(final ResultSet resultSet) throws SQLException {
        final List<Player> players = new ArrayList<>();
        while (resultSet.next()) {
            final Player player = new Player(
                    resultSet.getInt("id"),
                    Team.valueOf(resultSet.getString("team")),
                    new Score(resultSet.getDouble("score")),
                    resultSet.getBoolean("is_turn")
            );
            players.add(player);
        }
        return players;
    }
}
