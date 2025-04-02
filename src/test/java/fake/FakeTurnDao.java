package fake;

import dao.TurnDao;
import domain.piece.Team;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class FakeTurnDao implements TurnDao {

    private final List<Team> turns = new ArrayList<>(List.of(Team.CHO));

    @Override
    public Team load() {
        return turns.getLast();
    }

    @Override
    public void save(final Connection connection, final Team turn) {
        turns.add(turn);
    }

    @Override
    public void remove(final Connection connection) {
        turns.clear();
    }
}
