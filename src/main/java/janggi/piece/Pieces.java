package janggi.piece;

import janggi.value.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pieces {

    private static final int X_MAX = 8;
    private static final int X_MIN = 0;
    private static final int Y_MAX = 9;
    private static final int Y_MIN = 0;

    private final List<Piece> pieces;

    public Pieces(final List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public void movePiece(final List<Piece> enemyPieces, final Position targetPiecePosition,
            final Position destination) {
        validatePositionInRange(targetPiecePosition);
        validatePositionInRange(destination);

        Piece target = pieces.stream().filter(piece -> piece.getPosition().equals(targetPiecePosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 이동할 말이 존재하지 않습니다."));
        pieces.remove(target);
        Piece movedTarget = target.move(destination, enemyPieces, pieces);
        pieces.add(movedTarget);
    }

    private void validatePositionInRange(Position position) {
        if (position.x() < X_MIN || position.x() > X_MAX || position.y() < Y_MIN || position.y() > Y_MAX) {
            throw new IllegalArgumentException("[ERROR] x좌표는 0~8, y좌표는 0~9 사이로 입력해주세요.");
        }
    }

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }
}
