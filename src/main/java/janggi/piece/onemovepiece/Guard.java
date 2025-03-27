package janggi.piece.onemovepiece;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.List;

public class Guard extends Piece {

    public Guard(final Team team) {
        super(PieceType.GUARD, team);
    }

    @Override
    public List<Position> makeRoute(final Position presentPosition, final Position targetPosition) {
        return List.of();
    }

    @Override
    public void canMoveBy(final Position currentPosition, final Position targetPosition) {
        if (isNotMove(currentPosition, targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 사가 움직일 수 없는 위치 입니다.");
        }
    }

    private boolean isNotMove(final Position presentPosition, final Position position) {
        return !presentPosition.isOneStep(position);
    }
}
