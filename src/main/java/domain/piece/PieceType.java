package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import domain.move.Path;
import domain.move.rule.BasicRule;
import domain.move.rule.JumpingRule;
import domain.move.rule.MoveRule;
import domain.move.strategy.Movement;
import domain.move.strategy.OrthogonalThenDiagonalMovement;
import domain.move.strategy.OrthogonalThenTwoDiagonalMovement;
import domain.move.strategy.SingleStepExcludeBackwardMovement;
import domain.move.strategy.SingleStepMovement;
import domain.move.strategy.StraightLineMovement;
import java.util.List;

public enum PieceType {
    CANNON(new StraightLineMovement(), new JumpingRule()),
    CHARIOT(new StraightLineMovement(), new BasicRule()),
    ELEPHANT(new OrthogonalThenTwoDiagonalMovement(), new BasicRule()),
    GENERAL(new SingleStepMovement(), new BasicRule()),
    GUARD(new SingleStepMovement(), new BasicRule()),
    HORSE(new OrthogonalThenDiagonalMovement(), new BasicRule()),
    SOLDIER(new SingleStepExcludeBackwardMovement(), new BasicRule()),
    EMPTY(
            new Movement() {
                @Override
                protected List<Path> candidatePaths(Intersection from, Side side) {
                    return List.of();
                }
            },
            ((side, candidatePaths, alivePieces) -> List.of())),
    ;

    private final Movement movement;
    private final MoveRule moveRule;

    PieceType(Movement movement, MoveRule moveRule) {
        this.movement = movement;
        this.moveRule = moveRule;
    }

    public final List<Path> movablePaths(Intersection from, Side side) {
        return movement.movablePaths(from, side);
    }

    public final List<Intersection> movableDestinations(
            Side side,
            List<Path> candidatePaths,
            AlivePieces alivePieces
    ) {
        return moveRule.movableDestinations(side, candidatePaths, alivePieces);
    }
}
