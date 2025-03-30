package janggi.domain;

import static janggi.domain.movestep.MoveStep.DOWN;
import static janggi.domain.movestep.MoveStep.LEFT;
import static janggi.domain.movestep.MoveStep.LEFT_DOWN;
import static janggi.domain.movestep.MoveStep.LEFT_UP;
import static janggi.domain.movestep.MoveStep.RIGHT;
import static janggi.domain.movestep.MoveStep.RIGHT_DOWN;
import static janggi.domain.movestep.MoveStep.RIGHT_UP;
import static janggi.domain.movestep.MoveStep.UP;

import janggi.domain.board.PieceSearcher;
import janggi.domain.movement.CastleBounded;
import janggi.domain.movement.FollowsCastleRoad;
import janggi.domain.movement.Movement;
import janggi.domain.movement.OnceMovement;
import janggi.domain.movement.PoMovement;
import janggi.domain.movement.SeveralMovement;
import janggi.domain.movestep.InfiniteMoveProcess;
import janggi.domain.movestep.MoveProcess;
import java.util.Set;

public enum PieceType {
    BYEONG("병", 2,
        new OnceMovement(LEFT, RIGHT, DOWN),
        new FollowsCastleRoad(new OnceMovement(LEFT_DOWN, RIGHT_DOWN))
    ),
    JOL("졸", 2,
        new OnceMovement(LEFT, RIGHT, UP),
        new FollowsCastleRoad(new OnceMovement(LEFT_UP, RIGHT_UP))
    ),
    GOONG("궁", 0,
        new CastleBounded(new OnceMovement(LEFT, RIGHT, UP, DOWN)),
        new FollowsCastleRoad(new OnceMovement(LEFT_UP, RIGHT_UP, LEFT_DOWN, RIGHT_DOWN))
    ),
    SA("사", 3,
        new CastleBounded(new OnceMovement(LEFT, RIGHT, UP, DOWN)),
        new FollowsCastleRoad(new OnceMovement(LEFT_UP, RIGHT_UP, LEFT_DOWN, RIGHT_DOWN))
    ),
    CHA("차", 13,
        new SeveralMovement(
            new InfiniteMoveProcess(LEFT),
            new InfiniteMoveProcess(RIGHT),
            new InfiniteMoveProcess(UP),
            new InfiniteMoveProcess(DOWN)
        ),
        new FollowsCastleRoad(
            new SeveralMovement(
                new InfiniteMoveProcess(LEFT_UP),
                new InfiniteMoveProcess(LEFT_DOWN),
                new InfiniteMoveProcess(RIGHT_UP),
                new InfiniteMoveProcess(RIGHT_DOWN)
            )
        )
    ),
    MA("마", 5,
        new SeveralMovement(
            new MoveProcess(LEFT, LEFT_UP),
            new MoveProcess(LEFT, LEFT_DOWN),
            new MoveProcess(RIGHT, RIGHT_UP),
            new MoveProcess(RIGHT, RIGHT_DOWN),
            new MoveProcess(UP, LEFT_UP),
            new MoveProcess(UP, RIGHT_UP),
            new MoveProcess(DOWN, LEFT_DOWN),
            new MoveProcess(DOWN, RIGHT_DOWN)
        )
    ),
    PO("포", 7,
        new PoMovement(
            new SeveralMovement(
                new InfiniteMoveProcess(LEFT),
                new InfiniteMoveProcess(RIGHT),
                new InfiniteMoveProcess(UP),
                new InfiniteMoveProcess(DOWN)
            )
        ),
        new FollowsCastleRoad(
            new PoMovement(
                new SeveralMovement(
                    new InfiniteMoveProcess(LEFT_UP),
                    new InfiniteMoveProcess(LEFT_DOWN),
                    new InfiniteMoveProcess(RIGHT_UP),
                    new InfiniteMoveProcess(RIGHT_DOWN)
                )
            )
        )
    ),
    SANG("상", 3,
        new SeveralMovement(
            new MoveProcess(LEFT, LEFT_UP, LEFT_UP),
            new MoveProcess(LEFT, LEFT_DOWN, LEFT_DOWN),
            new MoveProcess(RIGHT, RIGHT_UP, RIGHT_UP),
            new MoveProcess(RIGHT, RIGHT_DOWN, RIGHT_DOWN),
            new MoveProcess(UP, LEFT_UP, LEFT_UP),
            new MoveProcess(UP, RIGHT_UP, RIGHT_UP),
            new MoveProcess(DOWN, LEFT_DOWN, LEFT_DOWN),
            new MoveProcess(DOWN, RIGHT_DOWN, RIGHT_DOWN)
        )
    );

    private final String canonicalName;
    private final int score;
    private final Set<Movement> movements;

    PieceType(String canonicalName, int score, Movement... movements) {
        this.canonicalName = canonicalName;
        this.score = score;
        this.movements = Set.of(movements);
    }

    public boolean canMove(Coordinate departure, Coordinate arrival, PieceSearcher pieceSearcher) {
        return movements.stream().anyMatch(
            movement -> movement.canMove(departure, arrival, pieceSearcher)
        );
    }

    public String canonicalName() {
        return canonicalName;
    }

    public int getScore() {
        return score;
    }
}
