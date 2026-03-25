package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import java.util.List;

public class Sang implements Piece {

    private final PieceType pieceType;
    private final List<MovePath> paths;

    public Sang() {
        pieceType = PieceType.SANG;
        paths = List.of(
            new MovePath(List.of(Delta.createUp(), Delta.createRightUp(), Delta.createRightUp())),
            new MovePath(List.of(Delta.createUp(), Delta.createLeftUp(), Delta.createLeftUp())),
            new MovePath(List.of(Delta.createDown(), Delta.createRightDown(), Delta.createRightDown())),
            new MovePath(List.of(Delta.createDown(), Delta.createLeftDown(), Delta.createLeftDown())),
            new MovePath(List.of(Delta.createLeft(), Delta.createLeftUp(), Delta.createLeftUp())),
            new MovePath(List.of(Delta.createLeft(), Delta.createLeftDown(), Delta.createLeftDown())),
            new MovePath(List.of(Delta.createRight(), Delta.createRightUp(), Delta.createRightUp())),
            new MovePath(List.of(Delta.createRight(), Delta.createRightDown(), Delta.createRightDown()))
        );
    }

    @Override
    public boolean canMove(int startX, int startY, int endX, int endY) {
        int distanceX = Math.abs(endX - startX);
        int distanceY = Math.abs(endY - startY);

        return (distanceX == 2 && distanceY == 3) || (distanceX == 3 && distanceY == 2);
    }
}
