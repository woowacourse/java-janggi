package janggi.domain.piece;

import janggi.domain.Placement;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Team;
import janggi.domain.rule.MoveRule;
import janggi.domain.rule.Movement;
import janggi.domain.rule.block.BlockOnlyOnceStrategy;
import janggi.domain.rule.move.StraightMoveStrategy;
import java.util.List;

public class Cannon extends Piece {

    private static final Movement MOVEMENT = new Movement(List.of(0, 1));

    public Cannon(final Team team) {
        super(team, PieceType.CANNON, new MoveRule(new StraightMoveStrategy(), new BlockOnlyOnceStrategy()));
    }

    @Override
    public void checkCanMove(final Placement placement, final Position departure, final Position destination) {
        validateMove(placement, departure, destination, MOVEMENT);
        validateCannonRestrict(placement, departure, destination);
    }

    private void validateCannonRestrict(final Placement placement,
                                        final Position departure,
                                        final Position destination) {
        checkExistCannonInDestination(placement, destination);
        checkIsOverCannon(placement, Route.of(departure, destination));
    }

    private void checkExistCannonInDestination(final Placement placement, final Position destination) {
        if (placement.exists(destination) && placement.getPiece(destination).isSameType(this.pieceType)) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
    }

    private void checkIsOverCannon(final Placement placement, final Route route) {
        if (route.hasSameTypePiece(placement, this.pieceType)) {
            throw new IllegalArgumentException("포는 포를 넘을 수 없습니다.");
        }
    }
}
