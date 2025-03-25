package domain.board.movement;

import static domain.board.movement.PalaceMovement.PALACE_MOVEMENT_POSITIONS;

import domain.board.BoardPosition;

public abstract class Movement {

    protected final BoardPosition before;
    protected final BoardPosition after;

    protected Movement(
            final BoardPosition before,
            final BoardPosition after
    ) {
        this.before = before;
        this.after = after;
    }

    abstract boolean isMoveOnLine();

    abstract boolean isOneLineMovement();

    public static Movement of(
            final BoardPosition before,
            final BoardPosition after
    ) {
        if (PALACE_MOVEMENT_POSITIONS.contains(before) && PALACE_MOVEMENT_POSITIONS.contains(after)) {
            return new PalaceMovement(before, after);
        }

        return new NormalMovement(before, after);
    }
}
