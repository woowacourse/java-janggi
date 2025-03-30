package dao;

import domain.piece.Team;

import java.util.ArrayList;
import java.util.List;

class FakeTurnDao implements TurnDao {

    private final List<Team> turns = new ArrayList<>(List.of(Team.CHO));

    @Override
    public Team load() {
        return turns.getLast();
    }

    @Override
    public void save(Team turn) {
        turns.add(turn);
    }

    @Override
    public void update(final Team turn) {
        turns.set(0, turn);
    }

    @Override
    public void remove() {
        turns.clear();
    }
}
