package domain.game.judge;

import domain.board.Board;
import domain.game.Team;
import java.util.Optional;

public interface CaptureVictoryRule {
    Optional<Team> findWinner(Board board);
}
