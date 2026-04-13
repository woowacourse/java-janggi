package repository.jdbc;

import domain.player.Player;
import domain.player.PlayerRepository;

import java.sql.Connection;
import java.util.List;

public class PlayerRepositoryImpl implements PlayerRepository {
    @Override
    public List<Player> findByGameId(final Connection connection, final long gameId) {
        return List.of();
    }

    @Override
    public void saveAll(final Connection connection, final long gameId, final List<Player> players) {

    }

    @Override
    public void updateScore(final Connection connection, final long gameId, final Player player) {

    }
}
