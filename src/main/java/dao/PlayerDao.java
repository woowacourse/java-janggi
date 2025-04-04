package dao;

import domain.Player;
import java.util.List;

public interface PlayerDao {
    void save(final String gameName, final Player player);

    List<Player> findAllByGameName(final String gameName);
}
