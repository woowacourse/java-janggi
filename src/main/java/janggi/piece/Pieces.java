package janggi.piece;

import janggi.board.BoardPositionRange;
import janggi.value.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(final List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public void movePiece(List<Piece> enemyPieces, Position targetPiecePosition, Position destination) {
        validatePositionInRange(targetPiecePosition);
        validatePositionInRange(destination);
        Piece target = searchPiece(targetPiecePosition);
        pieces.remove(target);
        Piece movedTarget = target.move(destination, enemyPieces, pieces);
        pieces.add(movedTarget);
    }

    private Piece searchPiece(Position targetPiecePosition) {
        return pieces.stream().filter(piece -> piece.getPosition().equals(targetPiecePosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 이동할 말이 존재하지 않습니다."));
    }

    private void validatePositionInRange(Position position) {
        boolean isXOutOfRange = position.x() < BoardPositionRange.X_MIN.getValue()
                || position.x() > BoardPositionRange.X_MAX.getValue();
        boolean isYOutOfRange = position.y() < BoardPositionRange.Y_MIN.getValue()
                || position.y() > BoardPositionRange.Y_MAX.getValue();
        if (isXOutOfRange || isYOutOfRange) {
            throw new IllegalArgumentException("[ERROR] x좌표는 0~8, y좌표는 0~9 사이로 입력해주세요.");
        }
    }

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }
}
