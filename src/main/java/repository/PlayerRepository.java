package repository;

import domain.Player;

public interface PlayerRepository {
    void save(final String gameName, final Player player);
}
