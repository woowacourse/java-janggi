package domain.dao;

import domain.piece.Team;

public interface GameDao {

    Team findTurn();

    void changeTurn(final Team turn);
}
