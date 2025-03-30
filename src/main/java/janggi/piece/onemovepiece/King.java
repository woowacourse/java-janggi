package janggi.piece.onemovepiece;

import janggi.board.palace.Palace;
import janggi.piece.PalaceAwarePiece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.List;

public class King extends PalaceAwarePiece {

    public King(final Team team) {
        super(PieceType.KING, team);
    }

    @Override
    public List<Position> makeRoute(final Position presentPosition, final Position targetPosition) {
        return List.of();
    }

    @Override
    public void canMoveBy(final Position currentPosition, final Position targetPosition, final Palace palace) {
        validateInPalace(palace, targetPosition);
        if (isNotMove(currentPosition, targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 왕이 움직일 수 없는 위치 입니다.");
        }
    }

    private void validateInPalace(final Palace palace, final Position targetPosition) {
        if (!palace.isInPalace(targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 왕은 궁성을 벗어날 수 없습니다.");
        }
    }

    private boolean isNotMove(final Position currentPosition, final Position targetPosition) {
        return !currentPosition.isOneStep(targetPosition) && !currentPosition.isOneDiagonal(targetPosition);
    }

}
