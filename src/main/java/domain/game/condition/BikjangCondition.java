package domain.game.condition;

import domain.board.Board;
import domain.game.GameRecord;
import domain.game.Team;
import domain.position.Position;

public class BikjangCondition implements GameEndCondition {

    @Override
    public boolean isSatisfied(Board board, GameRecord record) {
        if (!board.hasGeneral(Team.CHO) || !board.hasGeneral(Team.HAN)) {
            return false;
        }

        Position choGeneral = board.findGeneralPosition(Team.CHO);
        Position hanGeneral = board.findGeneralPosition(Team.HAN);

        return choGeneral.isSameColumn(hanGeneral)
                && board.hasNoPieceBetween(choGeneral, hanGeneral);
    }
}
