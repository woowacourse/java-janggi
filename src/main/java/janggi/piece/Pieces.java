package janggi.piece;

import janggi.value.Position;
import janggi.value.PositionRange;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(final List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public void movePiece(List<Piece> enemyPieces, Position targetPiecePosition, Position destination) {
        PositionRange.validatePositionInRange(targetPiecePosition);
        PositionRange.validatePositionInRange(destination);
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

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }
}
