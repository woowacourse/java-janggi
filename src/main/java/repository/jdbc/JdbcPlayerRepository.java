package repository.jdbc;

import domain.player.Player;
import domain.player.PlayerRepository;
import domain.player.Players;
import repository.entity.PlayerEntity;
import repository.mapper.PlayerMapper;

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

    private final PlayerMapper playerMapper;

    public JdbcPlayerRepository() {
        this.playerMapper = new PlayerMapper();
    }

    @Override
    public List<Player> findByGameId(final Connection connection, final long gameId) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_GAME_ID)) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                final List<PlayerEntity> playerEntities = toEntities(resultSet);
                return playerMapper.toPlayers(playerEntities);
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
        final List<PlayerEntity> playerEntities = playerMapper.toEntities(gameId, players);
        final List<PlayerEntity> savedEntities = new ArrayList<>();

        for (final PlayerEntity playerEntity : playerEntities) {
            savedEntities.add(save(connection, playerEntity));
        }

        return playerMapper.toPlayers(savedEntities);
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

    private PlayerEntity save(
            final Connection connection,
            final PlayerEntity playerEntity
    ) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, playerEntity.getGameId());
            statement.setString(2, playerEntity.getName());
            statement.setString(3, playerEntity.getTeam());
            statement.setInt(4, playerEntity.getScore());

            statement.executeUpdate();

            return savedEntity(statement, playerEntity);
        } catch (final SQLException exception) {
            throw new RuntimeException(SAVE_PLAYER_FAILED, exception);
        }
    }

    private PlayerEntity savedEntity(
            final PreparedStatement statement,
            final PlayerEntity playerEntity
    ) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return new PlayerEntity(
                        generatedKeys.getLong(1),
                        playerEntity.getGameId(),
                        playerEntity.getName(),
                        playerEntity.getTeam(),
                        playerEntity.getScore()
                );
            }

            throw new IllegalStateException(GET_PLAYER_ID_FAILED);
        }
    }

    private List<PlayerEntity> toEntities(final ResultSet resultSet) throws SQLException {
        final List<PlayerEntity> playerEntities = new ArrayList<>();

        while (resultSet.next()) {
            playerEntities.add(toEntity(resultSet));
        }

        return playerEntities;
    }

    private PlayerEntity toEntity(final ResultSet resultSet) throws SQLException {
        return new PlayerEntity(
                resultSet.getLong("player_id"),
                resultSet.getLong("game_id"),
                resultSet.getString("name"),
                resultSet.getString("team"),
                resultSet.getInt("score")
        );
    }
}
