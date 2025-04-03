package domain.dao;

import domain.JanggiBoard;
import domain.JanggiGame;
import domain.boardgenerator.JanggiBoardGenerator;
import domain.piece.Team;
import java.util.List;
import java.util.Map;

public class MemoryGameDao implements GameDao {

    private final Map<String, JanggiGame> games;
    private Team turn = Team.CHO;
    private Long id = 1L;

    @Override
    public Team findTurn(Long gameId) {
        return turn;
    }

    @Override
    public void changeTurn(Long gameId, Team turn) {
        this.turn = turn;
    }

    public MemoryGameDao(Map<String, JanggiGame> games) {
        this.games = games;
    }

    @Override
    public Long add(String name, Team team) {
        games.put(name, JanggiGame.init(id, JanggiBoard.init(new JanggiBoardGenerator())));
        return id++;
    }

    @Override
    public Long findIdByName(String name) {
        if (!games.containsKey(name)) {
            throw new IllegalArgumentException("게임방이 존재하지 않습니다.");
        }
        return games.get(name).getGameId();
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
