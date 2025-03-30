package janggi.piece.onemovepiece;

import janggi.board.palace.Palace;
import janggi.piece.PalaceAwarePiece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.List;

public class Pawn extends PalaceAwarePiece {

    public Pawn() {
        super(PieceType.PAWN, Team.CHU);
    }

    @Override
    public List<Position> makeRoute(final Position presentPosition, final Position targetPosition) {
        return List.of();
    }

    @Override
    public void canMoveBy(final Position currentPosition, final Position targetPosition, final Palace palace) {
        if (palace.isInPalace(currentPosition, targetPosition)) {
            validatePalaceMove(currentPosition, targetPosition);
            return;
        }
        if (isNotMove(currentPosition, targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 졸이 움직일 수 없는 위치입니다.");
        }
    }

    private void validatePalaceMove(final Position currentPosition, final Position targetPosition) {
        if (targetPosition.isBehind(currentPosition)) {
            throw new IllegalArgumentException("[ERROR] 졸이 움직일 수 없는 위치 입니다.");
        }
    }

    private boolean isNotMove(final Position currentPosition, final Position targetPosition) {
        return targetPosition.isBehind(currentPosition) || currentPosition.isDiagonal(targetPosition);
    }
}
