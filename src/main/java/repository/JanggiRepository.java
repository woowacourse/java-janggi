package repository;

import model.game.Janggi;

public interface JanggiRepository {

    Long saveGame(Janggi janggi);

    void updateGame(Long gameId, Janggi janggi);
}
