package domain.piece;

import domain.coordination.Coordination;
import domain.movement.DiagonalPalaceMovement;
import domain.movement.PalaceMovement;
import domain.piece.error.PieceException;

import java.util.List;

public abstract class DiagonalPalaceMovementPiece extends Piece {

    protected static final String UNRESOLVABLE_PATH_MESSAGE = "이동 경로를 확인할 수 없습니다.";

    protected final PalaceMovement palaceMovement;

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
        throw new IllegalStateException(UNRESOLVABLE_PATH_MESSAGE);
    }

    private void validateNormalRule(Coordination from, Coordination to) {
        boolean movable = from.isHorizontal(to) || from.isVertical(to);
        if (!movable) {
            throw new PieceException(IMPOSSIBLE_MOVE_MESSAGE);
        }
    }
}
