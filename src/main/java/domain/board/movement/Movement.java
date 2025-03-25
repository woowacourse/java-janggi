package domain.board.movement;

import static domain.board.movement.PalaceMovement.PALACE_MOVEMENT_POSITIONS;

import domain.board.BoardPosition;
import domain.board.Offset;

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

    public abstract boolean isMoveOnLine();

    public abstract boolean isOneLineMovement();

    public boolean isMoveInPalaceArea() {
        return before.isPalaceArea() && after.isPalaceArea();
    }

    public Offset calcaulteOffset() {
        return after.calculateOffset(before);
    }

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
