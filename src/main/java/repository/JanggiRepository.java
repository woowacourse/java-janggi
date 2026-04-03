package repository;

import domain.JanggiGame;

public interface JanggiRepository {
    void save(JanggiGame janggiGame);
    JanggiGame findJanggiGame();
}
