package domain.game.condition;

import domain.board.Board;
import domain.game.Team;
import domain.game.progress.GameProgress;
import domain.position.Position;

public class BikjangCondition implements GameEndCondition {

    @Override
    public boolean isSatisfied(Board board, GameProgress progress) {
        if (!board.hasEssentialPieceOf(Team.CHO) || !board.hasEssentialPieceOf(Team.HAN)) {
            return false;
        }

        Position choGeneral = board.findGeneralPosition(Team.CHO);
        Position hanGeneral = board.findGeneralPosition(Team.HAN);

        return choGeneral.isSameColumn(hanGeneral)
                && board.hasNoPieceBetween(choGeneral, hanGeneral);
    }
}
