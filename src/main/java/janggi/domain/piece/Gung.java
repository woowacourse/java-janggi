package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import java.util.List;

public class Gung implements Piece {

    private final PieceType pieceType;
    private final List<MovePath> paths;

    public Gung() {
        pieceType = PieceType.GUNG;
        paths = List.of(
            new MovePath(List.of(Delta.createUp())),
            new MovePath(List.of(Delta.createDown())),
            new MovePath(List.of(Delta.createLeft())),
            new MovePath(List.of(Delta.createRight())),
            new MovePath(List.of(Delta.createRightUp())),
            new MovePath(List.of(Delta.createRightDown())),
            new MovePath(List.of(Delta.createLeftUp())),
            new MovePath(List.of(Delta.createLeftDown()))
        );
    }

    @Override
    public boolean canMove(int startX, int startY, int endX, int endY) {
        int distanceX = Math.abs(endX - startX);
        int distanceY = Math.abs(endY - startY);
        if (isSamePosition(distanceX, distanceY)) {
            return false;
        }
        return isOneStep(distanceX, distanceY);
    }

    private boolean isSamePosition(int distanceX, int distanceY) {
        return distanceX == 0 && distanceY == 0;
    }

    private boolean isOneStep(int distanceX, int distanceY) {
        return distanceX <= 1 && distanceY <= 1;
    }
}
