package dao;

import game.Janggi;
import game.Turn;
import java.util.Optional;

public interface JanggiDao {

    void save(final Janggi janggi);
    void clear();
    Optional<Turn> findTurn();
}
