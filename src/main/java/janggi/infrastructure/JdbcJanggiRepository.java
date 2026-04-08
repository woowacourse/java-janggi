package janggi.infrastructure;

import janggi.domain.board.Board;
import janggi.domain.game.Players;
import janggi.domain.game.Turn;
import janggi.domain.repository.JanggiRepository;
import java.util.List;
import java.util.Optional;

public class JdbcJanggiRepository implements JanggiRepository {
    @Override
    public Long save(Players players) {
        return 0L;
    }

    @Override
    public void updateGameStatus(Long gameId, Board board, Turn turn) {

    }

    @Override
    public Optional<Long> findInProgressGameId() {
        return Optional.empty();
    }

    @Override
    public List<Long> findAllInProgressGameIds() {
        return List.of();
    }

    @Override
    public Board findBoardById(Long gameId) {
        return null;
    }

    @Override
    public Players findPlayersById(Long gameId) {
        return null;
    }

    @Override
    public Turn findTurnById(Long gameId) {
        return null;
    }

    @Override
    public void finishGame(Long gameId) {

    }
}
