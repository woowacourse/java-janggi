package repository;

import domain.piece.Side;
import domain.players.Player;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PlayerRepository {
    void saveAll(Connection connection, Long gameId, List<Player> players);
    void update(Connection connection, Long gameId, List<Player> players);
    Optional<Map<Side, Player>> findPlayersByGameId(Long gameId);
}
