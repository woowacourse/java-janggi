package domain.piece;

import domain.board.Movement;
import domain.board.Offset;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class Cannon extends Piece {

    private static final int ALLOWED_OBSTACLE_COUNT = 1;
    private static final Score SCORE = new Score(7.0f);

    public Cannon(final Team team) {
        super(PieceType.CANNON, team);
    }

    @Override
    public List<Offset> findMovementRule(final Movement movement) {
        final Offset totalOffset = movement.calculateOffset();

        if (movement.isPalaceMovement()) {
            return totalOffset.calculateUnitSteps();
        }

        validateNonPalaceMovement(totalOffset);
        return totalOffset.calculateUnitSteps();
    }

    private void validateNonPalaceMovement(final Offset totalOffset) {
        if (!totalOffset.isLinear()) {
            throw new IllegalArgumentException("해당 말은 이동할 수 없습니다.");
        }
    }

    @Override
    public void validateMovementConditions(
        final List<Piece> obstacles,
        @Nullable
        final Piece destinationPiece
    ) {
        if (obstacles.size() != ALLOWED_OBSTACLE_COUNT) {
            throw new IllegalArgumentException("포는 장애물을 정확히 하나 넘어야 합니다.");
        }

        if (obstacles.getFirst()
            .getPieceType() == PieceType.CANNON) {
            throw new IllegalArgumentException("포는 포를 넘을 수 없습니다.");
        }

        if (destinationPiece != null && destinationPiece.getPieceType() == PieceType.CANNON) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
    }

    @Override
    public Score getScore() {
        return SCORE;
    }
}
