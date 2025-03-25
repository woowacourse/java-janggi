package domain.board.movement;

import domain.board.BoardPosition;

public class NormalMovement extends Movement {

    protected NormalMovement(
            final BoardPosition before,
            final BoardPosition after
    ) {
        super(before, after);
    }

    @Override
    boolean isMoveOnLine() {
        return false;
    }

    @Override
    boolean isOneLineMovement() {
        return false;
    }
}
