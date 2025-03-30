package repository;

import domain.Player;
import java.util.List;

public interface PlayerRepository {
    void save(final String gameName, final Player player);

    List<Player> findAllByGameName(final String gameName);
}
