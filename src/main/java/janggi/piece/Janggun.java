package janggi.piece;

import janggi.position.Position;
import java.util.List;
import java.util.Map;

public class Janggun extends Piece {

    public Janggun(final Team team, final Position position) {
        super(new PieceProfile(PieceType.JANGGUN, team), position);
    }

    @Override
    public void checkObstacle(final Position futurePosition, final Map<Position, Piece> janggiBoard) {
        final List<Position> moveRoute = makeRoute(futurePosition);
        for (final Position position : moveRoute) {
            validateObstacle(janggiBoard, position);
        }
    }

    private void validateObstacle(final Map<Position, Piece> janggiBoard, final Position position) {
        if (janggiBoard.containsKey(position)) {
            throw new IllegalArgumentException("[ERROR] 왕을 이동할 수 없습니다. 이동하려는 경로에 장애물이 존재합니다.");
        }
    }

    @Override
    public List<Position> makeRoute(final Position position) {
        return List.of();
    }

    @Override
    public boolean isMove(final Position position) {
        final int dx = getBoardPosition().getRow() - position.getRow();
        final int dy = getBoardPosition().getCol() - position.getCol();

        if (Math.abs(dx) == 1 && dy == 0 || Math.abs(dy) == 1 && dx == 0) {
            return true;
        }

        throw new IllegalArgumentException("[ERROR] 왕이 움직일 수 없는 위치 입니다.");
    }

    @Override
    protected boolean isPo() {
        return false;
    }

}
