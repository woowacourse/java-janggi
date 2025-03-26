package janggi.domain.piece;

import janggi.domain.Placement;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.rule.MoveRule;
import janggi.domain.rule.block.BasicBlockStrategy;
import janggi.domain.rule.move.OneStepMoveStrategy;

public class Soldier extends Piece {


    public Soldier(final Team team) {
        super(team, PieceType.SOLDIER, new MoveRule(new OneStepMoveStrategy(), new BasicBlockStrategy()));
    }

    @Override
    public void checkCanMove(final Placement placement, final Position departure, final Position destination) {
        validateMove(placement, departure, destination);
        validateSoldierRestrict(departure, destination);
    }

    private void validateSoldierRestrict(final Position departure, Position destination) {
        int diffRow = destination.subtractRow(departure);

        if (this.team.isRed() && diffRow == -1) {
            throw new IllegalArgumentException("병은 본진을 향할 수 없습니다.");
        }

        if (this.team.isGreen() && diffRow == 1) {
            throw new IllegalArgumentException("졸은 본진을 향할 수 없습니다.");
        }
    }
}
