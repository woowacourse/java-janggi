package repository.jdbc;

import domain.player.Player;
import domain.player.PlayerRepository;
import domain.player.Players;
import domain.player.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcPlayerRepository implements PlayerRepository {

    private static final String FIND_BY_GAME_ID = """
            SELECT player_id, game_id, name, team, score
            FROM player
            WHERE game_id = ?
            ORDER BY player_id
            """;

    private static final String INSERT = """
            INSERT INTO player (game_id, name, team, score)
            VALUES (?, ?, ?, ?)
            """;

    private static final String UPDATE = """
            UPDATE player
            SET score = ?
            WHERE game_id = ? AND player_id = ?
            """;

    private static final String FIND_PLAYER_FAILED = "플레이어 조회에 실패했습니다.";
    private static final String UPDATE_PLAYER_FAILED = "플레이어 갱신에 실패했습니다.";
    private static final String SAVE_PLAYER_FAILED = "플레이어 저장에 실패했습니다.";
    private static final String GET_PLAYER_ID_FAILED = "생성된 player_id를 가져오지 못했습니다.";

    @Override
    public List<Player> findByGameId(final Connection connection, final long gameId) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_GAME_ID)) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                return toPlayers(resultSet);
            }
        } catch (final SQLException exception) {
            throw new RuntimeException(FIND_PLAYER_FAILED, exception);
        }
    }

    @Override
    public List<Player> saveAll(
            final Connection connection,
            final long gameId,
            final List<Player> players
    ) {
        final List<Player> savedPlayers = new ArrayList<>();

        for (final Player player : players) {
            savedPlayers.add(save(connection, gameId, player));
        }

        return savedPlayers;
    }

    @Override
    public void update(
            final Connection connection,
            final long gameId,
            final Players players
    ) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            for (final Player player : players.getPlayers()) {
                statement.setInt(1, player.getScore());
                statement.setLong(2, gameId);
                statement.setLong(3, player.getPlayerId());
                statement.addBatch();
            }

            statement.executeBatch();
        } catch (final SQLException exception) {
            throw new RuntimeException(UPDATE_PLAYER_FAILED, exception);
        }
    }

    private Player save(
            final Connection connection,
            final long gameId,
            final Player player
    ) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, gameId);
            statement.setString(2, player.getName().name());
            statement.setString(3, player.getTeam().name());
            statement.setInt(4, player.getScore());

            statement.executeUpdate();

            return savedPlayer(statement, player);
        } catch (final SQLException exception) {
            throw new RuntimeException(SAVE_PLAYER_FAILED, exception);
        }
    }

    private Player savedPlayer(
            final PreparedStatement statement,
            final Player player
    ) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return Player.loadPlayer(
                        generatedKeys.getLong(1),
                        player.getName().name(),
                        player.getTeam(),
                        player.getScore()
                );
            }

            throw new IllegalStateException(GET_PLAYER_ID_FAILED);
        }
    }

    private List<Player> toPlayers(final ResultSet resultSet) throws SQLException {
        final List<Player> players = new ArrayList<>();

        while (resultSet.next()) {
            players.add(toPlayer(resultSet));
        }

        return players;
    }

    private Player toPlayer(final ResultSet resultSet) throws SQLException {
        return Player.loadPlayer(
                resultSet.getLong("player_id"),
                resultSet.getString("name"),
                Team.valueOf(resultSet.getString("team")),
                resultSet.getInt("score")
        );
    }
}
