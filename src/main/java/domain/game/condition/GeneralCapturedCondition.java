package domain.game.condition;

import domain.board.Board;
import domain.game.GameRecord;
import domain.game.Team;

public class GeneralCapturedCondition implements GameEndCondition {

    @Override
    public boolean isSatisfied(Board board, GameRecord record) {
        return !board.hasEssentialPieceOf(Team.CHO) || !board.hasEssentialPieceOf(Team.HAN);
    }
}
