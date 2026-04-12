package domain.piece;

import domain.coordination.Coordination;
import domain.movement.DiagonalPalaceMovement;
import domain.movement.PalaceMovement;
import domain.piece.error.InvalidMovementException;

import java.util.List;

public abstract class DiagonalPalaceMovementPiece extends Piece {

    private static final String INVALID_MOVEMENT_PATH_MESSAGE = "두 좌표 간의 이동이 기물의 이동 규칙에 위배됩니다.";
    private static final String IMPOSSIBLE_MOVE_MESSAGE = "기물이 움직일 수 없는 위치입니다.";

    private final PalaceMovement palaceMovement;

    protected DiagonalPalaceMovementPiece(Team team) {
        super(team);
        this.palaceMovement = new DiagonalPalaceMovement();
    }

    @Override
    public void validateRule(Coordination from, Coordination to) {
        if (palaceMovement.isPalace(from, to)) {
            palaceMovement.validateRule(from, to);
            return;
        }
        validateNormalRule(from, to);
    }

    @Override
    public List<Coordination> resolvePath(Coordination from, Coordination to) {
        if (palaceMovement.isPalace(from, to)) {
            if (from.isDiagonal(to)) {
                return from.diagonalPathTo(to);
            }
        }
        if (from.isVertical(to)) {
            return from.verticalPathTo(to);
        }
        if (from.isHorizontal(to)) {
            return from.horizontalPathTo(to);
        }
        throw new IllegalStateException(INVALID_MOVEMENT_PATH_MESSAGE);
    }

    private void validateNormalRule(Coordination from, Coordination to) {
        boolean movable = from.isHorizontal(to) || from.isVertical(to);
        if (!movable) {
            throw new InvalidMovementException(IMPOSSIBLE_MOVE_MESSAGE);
        }
    }
}
