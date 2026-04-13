package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import domain.move.Path;
import domain.move.rule.BasicRule;
import domain.move.rule.JumpingRule;
import domain.move.rule.MoveRule;
import domain.move.rule.PalaceRule;
import domain.move.strategy.Movement;
import domain.move.strategy.OrthogonalThenDiagonalMovement;
import domain.move.strategy.OrthogonalThenTwoDiagonalMovement;
import domain.move.strategy.SingleStepExcludeBackwardMovement;
import domain.move.strategy.SingleStepMovement;
import domain.move.strategy.StraightLineMovement;
import java.util.Arrays;
import java.util.List;

public enum PieceType {
    CANNON(7, new StraightLineMovement(), new JumpingRule()),
    CHARIOT(13, new StraightLineMovement(), new BasicRule()),
    ELEPHANT(3, new OrthogonalThenTwoDiagonalMovement(), new BasicRule()),
    GENERAL(0, new SingleStepMovement(), new PalaceRule()),
    GUARD(3, new SingleStepMovement(), new PalaceRule()),
    HORSE(5, new OrthogonalThenDiagonalMovement(), new BasicRule()),
    SOLDIER(2, new SingleStepExcludeBackwardMovement(), new BasicRule()),
    EMPTY(0,
            new Movement() {
                @Override
                protected List<Path> candidatePaths(Intersection from, Side side) {
                    return List.of();
                }
            },
            ((side, candidatePaths, alivePieces) -> List.of())),
    ;

    private final int point;
    private final Movement movement;
    private final MoveRule moveRule;

    PieceType(int point, Movement movement, MoveRule moveRule) {
        this.point = point;
        this.movement = movement;
        this.moveRule = moveRule;
    }

    public static PieceType from(String typeName) {
        return Arrays.stream(values())
                .filter(type -> type.name().equals(typeName))
                .findAny()
                .orElse(EMPTY);
    }

    public final List<Intersection> movableDestinations(
            Side side,
            Intersection from,
            AlivePieces alivePieces
    ) {
        return moveRule.movableDestinations(
                side,
                movablePaths(from, side),
                alivePieces
        );
    }

    private List<Path> movablePaths(Intersection from, Side side) {
        return movement.movablePaths(from, side);
    }

    public int point() {
        return point;
    }
}
