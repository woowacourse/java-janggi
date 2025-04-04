package janggi.piece;

import janggi.movement.Direction;
import janggi.movement.LimitedMovement;
import janggi.movement.Movement;
import janggi.movement.UnLimitedMovement;
import janggi.position.PalacePosition;
import janggi.position.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum PieceType {
    CHARIOT( 13,false,
            List.of(new UnLimitedMovement(Collections.nCopies(10, Direction.UP)),
                    new UnLimitedMovement(Collections.nCopies(10, Direction.DOWN)),
                    new UnLimitedMovement(Collections.nCopies(10, Direction.RIGHT)),
                    new UnLimitedMovement(Collections.nCopies(10, Direction.LEFT))),
            List.of(new LimitedMovement(Collections.nCopies(2, Direction.RIGHT_UP)),
                    new LimitedMovement(Collections.nCopies(2, Direction.RIGHT_DOWN)),
                    new LimitedMovement(Collections.nCopies(2, Direction.LEFT_UP)),
                    new LimitedMovement(Collections.nCopies(2, Direction.LEFT_DOWN)),
                    new LimitedMovement(List.of(Direction.RIGHT_UP)),
                    new LimitedMovement(List.of(Direction.RIGHT_DOWN)),
                    new LimitedMovement(List.of(Direction.LEFT_UP)),
                    new LimitedMovement(List.of(Direction.LEFT_DOWN)))),
    CANNON( 7,true,
            List.of(new UnLimitedMovement(Collections.nCopies(10, Direction.UP)),
                    new UnLimitedMovement(Collections.nCopies(10, Direction.DOWN)),
                    new UnLimitedMovement(Collections.nCopies(10, Direction.RIGHT)),
                    new UnLimitedMovement(Collections.nCopies(10, Direction.LEFT))),
            List.of(new UnLimitedMovement(Collections.nCopies(2, Direction.RIGHT_UP)),
                    new UnLimitedMovement(Collections.nCopies(2, Direction.RIGHT_DOWN)),
                    new UnLimitedMovement(Collections.nCopies(2, Direction.LEFT_UP)),
                    new UnLimitedMovement(Collections.nCopies(2, Direction.LEFT_DOWN))
            )),
    HORSE( 5, false,List.of(
            new LimitedMovement(List.of(Direction.UP, Direction.RIGHT_UP)),
            new LimitedMovement(List.of(Direction.UP, Direction.LEFT_UP)),
            new LimitedMovement(List.of(Direction.LEFT, Direction.LEFT_UP)),
            new LimitedMovement(List.of(Direction.LEFT, Direction.LEFT_DOWN)),
            new LimitedMovement(List.of(Direction.RIGHT, Direction.RIGHT_UP)),
            new LimitedMovement(List.of(Direction.RIGHT, Direction.RIGHT_DOWN)),
            new LimitedMovement(List.of(Direction.DOWN, Direction.RIGHT_DOWN)),
            new LimitedMovement(List.of(Direction.DOWN, Direction.LEFT_DOWN))
    ), List.of()),
    ELEPHANT( 3, false,List.of(
            new LimitedMovement(List.of(Direction.UP, Direction.RIGHT_UP, Direction.RIGHT_UP)),
            new LimitedMovement(List.of(Direction.UP, Direction.LEFT_UP, Direction.LEFT_UP)),
            new LimitedMovement(List.of(Direction.LEFT, Direction.LEFT_UP, Direction.LEFT_UP)),
            new LimitedMovement(List.of(Direction.LEFT, Direction.LEFT_DOWN, Direction.LEFT_DOWN)),
            new LimitedMovement(List.of(Direction.RIGHT, Direction.RIGHT_UP, Direction.RIGHT_UP)),
            new LimitedMovement(List.of(Direction.RIGHT, Direction.RIGHT_DOWN, Direction.RIGHT_DOWN)),
            new LimitedMovement(List.of(Direction.DOWN, Direction.RIGHT_DOWN, Direction.RIGHT_DOWN)),
            new LimitedMovement(List.of(Direction.DOWN, Direction.LEFT_DOWN, Direction.LEFT_DOWN))
    ), List.of()),
    GUARD( 3, false,
            List.of(new LimitedMovement(List.of(Direction.UP)),
            new LimitedMovement(List.of(Direction.DOWN)),
            new LimitedMovement(List.of(Direction.RIGHT)),
            new LimitedMovement(List.of(Direction.LEFT))),
            List.of(new LimitedMovement(List.of(Direction.RIGHT_UP)),
            new LimitedMovement(List.of(Direction.RIGHT_DOWN)),
            new LimitedMovement(List.of(Direction.LEFT_UP)),
            new LimitedMovement(List.of(Direction.LEFT_DOWN))
    )),
        SOLDIER( 2,false,
            List.of(new LimitedMovement(List.of(Direction.UP)),
            new LimitedMovement(List.of(Direction.DOWN)),
            new LimitedMovement(List.of(Direction.RIGHT)),
            new LimitedMovement(List.of(Direction.LEFT))),
            List.of(new LimitedMovement(List.of(Direction.RIGHT_UP)),
            new LimitedMovement(List.of(Direction.LEFT_UP)),
            new LimitedMovement(List.of(Direction.RIGHT_DOWN)),
            new LimitedMovement(List.of(Direction.LEFT_DOWN))
    )),
    KING( 0, false,
            List.of(new LimitedMovement(List.of(Direction.UP)),
            new LimitedMovement(List.of(Direction.DOWN)),
            new LimitedMovement(List.of(Direction.RIGHT)),
            new LimitedMovement(List.of(Direction.LEFT))),
            List.of(new LimitedMovement(List.of(Direction.RIGHT_UP)),
            new LimitedMovement(List.of(Direction.RIGHT_DOWN)),
            new LimitedMovement(List.of(Direction.LEFT_UP)),
            new LimitedMovement(List.of(Direction.LEFT_DOWN)))
    );

//    private final Function<BoardPieceDto, Piece> instance;
    private final int score;
    private final boolean isJumpable;
    private final List<Movement> movements;
    private final List<Movement> palaceMovements;
/*
    PieceType(Function<BoardPieceDto, Piece> instance, int score, List<Movement> movements, List<Movement> palaceMovements) {
        this.instance = instance;
        this.score = score;
        this.movements = movements;
        this.palaceMovements = palaceMovements;
    }*/

    PieceType(int score, boolean isJumpable, List<Movement> movements, List<Movement> palaceMovements) {
        this.score = score;
        this.isJumpable = isJumpable;
        this.movements = movements;
        this.palaceMovements = palaceMovements;
    }

    public List<Movement> generateMovements(Position startPosition) {
        if (PalacePosition.isContains(startPosition)) {
            List<Movement> totalMovements = new ArrayList<>();
            totalMovements.addAll(movements);
            totalMovements.addAll(palaceMovements);
            return totalMovements;
        }
        return movements;
    }

    public boolean canNotMoveBack() {
        return this == SOLDIER;
    }

    public boolean isJumpable() {
        return isJumpable;
    }

//    public Piece createInstance(BoardPieceDto boardPieceDto) {
//        return instance.apply(boardPieceDto);
//    }

    public int getScore() {
        return score;
    }
}
