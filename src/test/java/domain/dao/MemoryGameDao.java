package domain.dao;

import domain.piece.Team;

public class MemoryGameDao implements GameDao {

    private Team turn = Team.CHO;

    @Override
    public Team findTurn() {
        return turn;
    }

    @Override
    public void changeTurn(Team turn) {
        this.turn = turn;
    }
}
