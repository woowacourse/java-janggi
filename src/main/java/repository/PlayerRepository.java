package repository;

import domain.players.Player;

import java.util.List;

public interface PlayerRepository {
    void save(Long gameId, List<Player> players);
    void update(Long gameId, List<Player> players);
}
