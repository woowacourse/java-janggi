package piece;

import java.util.Set;
import java.util.stream.Collectors;

import board.Board;
import board.Position;
import piece.movement.PalaceMovement;

public class King extends Piece {

    public King(final Team team) {
        super(team, PieceType.KING);
    }

    @Override
    protected Set<Position> getMovablePositions(final Position startPosition, final Board board) {
        if (!startPosition.isPalacePosition()) {
            throw new IllegalStateException("궁은 궁성 영역 밖에 존재할 수 없습니다.");
        }
        return PalaceMovement.applyMovement(startPosition)
                .stream()
                .filter(candidatePosition -> isMovable(candidatePosition, board))
                .collect(Collectors.toSet());
    }

    @Override
    public PieceType getType() {
        return this.pieceType;
    }

    private boolean isMovable(final Position position, final Board board) {
        return position.isPalacePosition() && !board.isSameTeamPosition(this.team, position);
    }

}
