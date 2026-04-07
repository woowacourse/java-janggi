package repository;

import domain.piece.Side;
import domain.players.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PlayerRepository {
    void saveAll(Long gameId, List<Player> players);
    void update(Long gameId, List<Player> players);
    Optional<Map<Side, Player>> findPlayersByGameId(Long gameId);
}
