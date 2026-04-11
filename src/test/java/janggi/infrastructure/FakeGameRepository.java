package janggi.infrastructure;

import janggi.domain.GameRepository;
import janggi.domain.Team;
import janggi.domain.board.Board;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeGameRepository implements GameRepository {

    private final Map<Long, Board> boardStore = new HashMap<>();
    private final Map<Long, Team> turnStore = new HashMap<>();
    private long idSequence = 1;

    @Override
    public long save(Board board, Team currentTeam) {
        long id = idSequence++;
        boardStore.put(id, board);
        turnStore.put(id, currentTeam);
        return id;
    }

    @Override
    public void update(long gameId, Board board, Team currentTeam) {
        boardStore.put(gameId, board);
        turnStore.put(gameId, currentTeam);
    }

    @Override
    public Optional<Board> findBoardById(long gameId) {
        return Optional.ofNullable(boardStore.get(gameId));
    }

    @Override
    public Team findTurnById(long gameId) {
        Team team = turnStore.get(gameId);
        if (team == null) {
            throw new IllegalArgumentException("[ERROR] 게임을 찾을 수 없습니다: " + gameId);
        }
        return team;
    }
}
