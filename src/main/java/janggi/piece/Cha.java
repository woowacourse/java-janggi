package janggi.piece;

import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cha extends Piece {

    public Cha(final PieceProfile pieceProfile, final Position position) {
        super(pieceProfile, position);
    }

    @Override
    public void updatePiecePositionBy(Position position) {
        this.position = position;
    }

    @Override
    public void checkObstacle(final Position futurePosition, final Map<Position, Piece> janggiBoard) {
        List<Position> moveRoute = makeRoute(futurePosition);
        for (Position position : moveRoute) {
            validateObstacle(janggiBoard, position);
        }
    }

    private void validateObstacle(final Map<Position, Piece> janggiBoard, final Position position) {
        if (janggiBoard.containsKey(position)) {
            throw new IllegalArgumentException("[ERROR] 차를 이동할 수 없습니다. 차는 다른 기물을 넘어 다닐 수 없습니다.");
        }
    }

    @Override
    public List<Position> makeRoute(final Position position) {
        List<Position> route = new ArrayList<>();
        int dx = getBoardPosition().getRow() - position.getRow();
        int dy = getBoardPosition().getCol() - position.getCol();
        int presentCol = getBoardPosition().getCol();
        int presentRow = getBoardPosition().getRow();

        if (dx == 0 && dy > 0) {
            for (int i = 1; i <= dy; i++) {
                route.add(new Position(presentRow, presentCol - i));
            }
        }

        if (dx == 0 && dy < 0) {
            for (int i = 1; i <= Math.abs(dy); i++) {
                route.add(new Position(presentRow, presentCol + i));
            }
        }

        if (dx > 0 && dy == 0) {
            for (int i = 1; i <= dx; i++) {
                route.add(new Position(presentRow - i, presentCol));
            }
        }

        if (dx < 0 && dy == 0) {
            for (int i = 1; i <= Math.abs(dx); i++) {
                route.add(new Position(presentRow + i, presentCol));
            }
        }

        return route;
    }

    @Override
    public boolean isMove(Position position) {
        if ((getBoardPosition().getRow() == position.getRow())
                || (getBoardPosition().getCol() == position.getCol())) {
            return true;
        }

        throw new IllegalArgumentException("[ERROR] 차가 움직일 수 없는 위치 입니다.");
    }

}
