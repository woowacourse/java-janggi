package janggi.piece;

import janggi.value.JanggiPosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pieces {


    private final List<Piece> pieces;

    public Pieces(final List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public void movePiece(final List<Piece> enemyPieces, final JanggiPosition targetPieceJanggiPosition,
            final JanggiPosition destination) {
        Piece target = findTargetPiece(targetPieceJanggiPosition);

        pieces.remove(target);
        Piece movedTarget = target.move(destination, enemyPieces, pieces);
        pieces.add(movedTarget);
    }

    private Piece findTargetPiece(JanggiPosition targetPieceJanggiPosition) {
        return pieces.stream().filter(piece -> piece.getPosition().equals(targetPieceJanggiPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 이동할 말이 존재하지 않습니다."));
    }


    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }
}
