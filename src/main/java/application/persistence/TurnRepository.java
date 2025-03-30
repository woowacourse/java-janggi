package application.persistence;

import domain.game.Turn;

public interface TurnRepository {

    Turn findTurn();

    void updateTurn(Turn current);

    void save(Turn current);

    void delete();
}
