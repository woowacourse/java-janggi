package janggi.domain.piece;

import janggi.domain.Dynasty;
import java.util.ArrayList;
import java.util.List;

public class PiecesOnPath {

    private final List<Piece> pieces;

    public PiecesOnPath(Piece... pieces) {
        this(List.of(pieces));
    }

    public PiecesOnPath(List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public boolean isDestinationOfDynasty(Dynasty dynasty) {
        return destination().isDynasty(dynasty);
    }

    public boolean isAllEmptyWithoutDestination() {
        return withoutDestination().stream()
                .allMatch(Piece::isEmptyPiece);
    }

    public boolean isExistSamePiece(Piece piece) {
        return pieces.stream()
                .anyMatch(each -> each.isSameType(piece));
    }

    public int countNotSamePieceWithoutDestination(Piece piece) {
        return Math.toIntExact(withoutDestination().stream()
                .filter(each -> !each.isEmptyPiece())
                .filter(each -> !each.isSameType(piece))
                .count());
    }

    private Piece destination() {
        if (pieces.isEmpty()) {
            return new EmptyPiece();
        }
        return pieces.getLast();
    }

    private List<Piece> withoutDestination() {
        return pieces.stream()
                .filter(piece -> !piece.equals(destination()))
                .toList();
    }
}
