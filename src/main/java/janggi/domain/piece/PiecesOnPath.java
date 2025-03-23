package janggi.domain.piece;

import janggi.domain.Dynasty;
import java.util.List;

public class PiecesOnPath {

    private final List<Piece> pieces;

    public PiecesOnPath(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public boolean isDestinationOfDynasty(Dynasty dynasty) {
        return destination().isDynasty(dynasty);
    }

    public boolean isAllEmptyWithoutDestination() {
        return withoutDestination().stream()
                .allMatch(Piece::isEmptyPiece);
    }

    public int countSamePieceWithoutDestination(Piece piece) {
        return Math.toIntExact(withoutDestination().stream()
                .filter(each -> each.isSamePiece(piece))
                .count());
    }

    public int countNotSamePieceWithoutDestination(Piece piece) {
        return pieces.size() - countSamePieceWithoutDestination(piece);
    }

    public boolean isNotSameDestination(Piece piece) {
        return !destination().isSamePiece(piece);
    }

    private Piece destination() {
        return pieces.getLast();
    }

    private List<Piece> withoutDestination() {
        return pieces.stream()
                .filter(piece -> !piece.equals(destination()))
                .toList();
    }
}
