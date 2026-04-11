package janggi.domain;

import janggi.domain.board.Board;
import java.util.Optional;

public interface GameRepository {
    long save(Board board, Team currentTeam);
    void update(long gameId, Board board, Team currentTeam);
    Optional<Board> findBoardById(long gameId);
    Team findTurnById(long gameId);
}
