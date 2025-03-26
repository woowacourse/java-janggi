package janggi.domain.piece;

import janggi.domain.Palace;
import janggi.domain.Placement;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.rule.MoveRule;
import janggi.domain.rule.block.BasicBlockStrategy;
import janggi.domain.rule.move.OneStepMoveStrategy;

public class Guard extends Piece {

    private final Palace palace;

    public Guard(final Team team) {
        super(team, PieceType.GUARD, new MoveRule(new OneStepMoveStrategy(), new BasicBlockStrategy()));
        this.palace = Palace.from(team);
    }

    @Override
    public void checkCanMove(final Placement placement, final Position departure, final Position destination) {
        validateInnerPalace(destination);
        validateMove(placement, departure, destination);
    }

    private void validateInnerPalace(final Position destination) {
        if (palace.isOuterPalace(destination)) {
            throw new IllegalArgumentException("사는 궁성 내부로만 이동할 수 있습니다.");
        }
    }
}
