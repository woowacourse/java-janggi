package domain.board.movement;

import domain.board.BoardPosition;
import java.util.Set;

public abstract class Movement {

    private static final Set<BoardPosition> PALACE_MOVEMENT_POSITIONS = Set.of(
            new BoardPosition(3, 0), new BoardPosition(5, 0), new BoardPosition(4, 1),
            new BoardPosition(3, 2), new BoardPosition(5, 2),
            new BoardPosition(3, 9), new BoardPosition(5, 9), new BoardPosition(4, 8),
            new BoardPosition(3, 7), new BoardPosition(5, 7)
    );

    private final BoardPosition before;
    private final BoardPosition after;

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
