package domain.dao;

import domain.JanggiGame;
import domain.piece.Team;
import java.util.List;

public class MemoryGamesDao implements GamesDao {

    @Override
    public JanggiGame add(String name, Team team) {
        return null;
    }

    @Override
    public JanggiGame findByName(String name) {
        return null;
    }

    @Override
    public List<String> findAllName() {
        return List.of();
    }

    @Override
    public Long countAll() {
        return 0L;
    }
}
