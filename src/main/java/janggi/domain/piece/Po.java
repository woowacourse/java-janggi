package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import java.util.List;

public class Po implements Piece {

    private final PieceType pieceType;
    private final List<MovePath> paths;

    public Po() {
        pieceType = PieceType.PO;
        paths = List.of(
            new MovePath(List.of(Delta.createUp())),
            new MovePath(List.of(Delta.createDown())),
            new MovePath(List.of(Delta.createLeft())),
            new MovePath(List.of(Delta.createRight()))
        );
    }

    // TODO: Pieces에서 사이에 기물이 없다면 애초에 호출하지 않음. Pieces에 Po를 움직일 경우 중간에 좌표가 있는지 확인하는 로직이 필요함.
    @Override
    public boolean canMove(int startX, int startY, int endX, int endY) {
        if (startX != endX && startY != endY) {
            return false;
        }
        return startX != endX || startY != endY;
    }

    @Override
    public String nickname() {
        return pieceType.getNickname();
    }
}
