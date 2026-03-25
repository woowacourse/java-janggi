package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import java.util.List;

public class Ma implements Piece {

    private final PieceType pieceType;
    private final List<MovePath> paths;

    public Ma() {
        pieceType = PieceType.MA;
        this.paths = List.of(
            new MovePath(List.of(Delta.createUp(), Delta.createRightUp())),
            new MovePath(List.of(Delta.createUp(), Delta.createLeftUp())),
            new MovePath(List.of(Delta.createDown(), Delta.createRightDown())),
            new MovePath(List.of(Delta.createDown(), Delta.createLeftDown())),
            new MovePath(List.of(Delta.createLeft(), Delta.createLeftUp())),
            new MovePath(List.of(Delta.createLeft(), Delta.createLeftDown())),
            new MovePath(List.of(Delta.createRight(), Delta.createRightUp())),
            new MovePath(List.of(Delta.createRight(), Delta.createRightDown()))
        );
    }

    @Override
    public boolean canMove(int startX, int startY, int endX, int endY) {
        int distanceX = Math.abs(endX - startX);
        int distanceY = Math.abs(endY - startY);

        // 마의 이동 거리 공식: (X가 1칸이면 Y는 2칸) 또는 (X가 2칸이면 Y는 1칸)
        return (distanceX == 1 && distanceY == 2) || (distanceX == 2 && distanceY == 1);
    }
}
