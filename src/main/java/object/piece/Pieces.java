package object.piece;

import java.util.Collections;
import java.util.List;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public int size() {
        return pieces.size();
    }

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }

    public Piece getFirstPiece() {
        return pieces.getFirst();
    }

    public Piece getLastPiece() {
        return pieces.getLast();
    }

    public void killPieceFrom(Piece killerPiece) {
        for (int i = 0; i < pieces.size(); i++) {
            Piece piece = pieces.get(i);
            if (killerPiece.isSamePosition(piece) && !killerPiece.isSameTeam(piece)) {
                pieces.remove(i);
                return;
            }
        }
    }
}
