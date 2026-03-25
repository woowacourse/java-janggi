package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import java.util.List;

public class Cha implements Piece {

    private final PieceType pieceType;
    private final List<MovePath> paths;

    public Cha() {
        pieceType = PieceType.CHA;
        paths = List.of(
            new MovePath(List.of(Delta.createUp())),
            new MovePath(List.of(Delta.createDown())),
            new MovePath(List.of(Delta.createLeft())),
            new MovePath(List.of(Delta.createRight()))
        );
    }

    // TODO: start 좌표는 유효한게 보장 되어 있는지? Pieces에서 Map 조회 후 반환하기.
    @Override
    public boolean canMove(int startX, int startY, int endX, int endY) {
        if (isSamePosition(startX, startY, endX, endY)) {
            return false;
        }
        return isStraightDirection(startX, startY, endX, endY);
    }

    private boolean isSamePosition(int startX, int startY, int endX, int endY) {
        return startX == endX && startY == endY;
    }

    private boolean isStraightDirection(int startX, int startY, int endX, int endY) {
        return startX == endX || startY == endY;
    }
}
