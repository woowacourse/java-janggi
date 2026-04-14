package domain.game.condition;

import domain.board.Board;
import domain.game.Team;
import domain.game.progress.GameProgress;

public class GeneralCapturedCondition implements GameEndCondition {

    @Override
    public boolean isSatisfied(Board board, GameProgress progress) {
        return !board.hasEssentialPieceOf(Team.CHO) || !board.hasEssentialPieceOf(Team.HAN);
    }
}
