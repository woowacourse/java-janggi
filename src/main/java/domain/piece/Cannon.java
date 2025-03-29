package domain.piece;

import domain.board.BoardPosition;
import domain.board.Offset;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class Cannon extends Piece {

    private static final int ALLOWED_OBSTACLE_COUNT = 1;

    public Cannon(final Team team) {
        super(PieceType.CANNON, team);
    }

    @Override
    public List<Offset> findMovementRule(
        final BoardPosition selectedPosition,
        final BoardPosition destinationBoardPosition
    ) {
        final Offset totalOffset = destinationBoardPosition.calculateOffset(selectedPosition);

        if (!totalOffset.isLinear()) {
            throw new IllegalArgumentException("해당 말은 이동할 수 없습니다.");
        }

        return totalOffset.calculateLinearOffsets();
    }

    @Override
    public void validateMoveRule(
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
}
