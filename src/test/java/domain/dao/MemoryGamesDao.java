package domain.dao;

import domain.JanggiBoard;
import domain.JanggiGame;
import domain.piece.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemoryGamesDao implements GamesDao {

    private final Map<String, JanggiGame> games;

    public MemoryGamesDao(Map<String, JanggiGame> games) {
        this.games = games;
    }

    @Override
    public JanggiGame add(String name, Team team) {
        return games.put(name,
                new JanggiGame(new MemoryGameDao(), JanggiBoard.of(new MemoryPieceDao(new ArrayList<>()))));
    }

    @Override
    public JanggiGame findByName(String name) {
        return games.get(name);
    }

    @Override
    public List<String> findAllName() {
        return games.keySet().stream().toList();
    }

    @Override
    public Long countAll() {
        return (long) games.size();
    }
}
