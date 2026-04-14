package domain.game.judge;

import domain.board.Board;
import domain.game.Team;

public interface ScoringRule {
    double score(Board board, Team team);
}
