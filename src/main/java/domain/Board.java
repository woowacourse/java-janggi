package domain;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final List<Piece> pieces;

    public Board(final List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public void putPiece(Piece piece) {
        pieces.add(piece);
    }

    public boolean isExists(Position position) {
        return pieces.stream()
                .anyMatch(piece -> piece.isSamePosition(position));
    }

    public boolean isSameTeam(Piece piece, final Position newPosition) {
        return pieces.stream()
                .anyMatch(p -> p.isSamePosition(newPosition) && p.isSameTeam(piece));
    }

    public void remove(final Position position) {
        int target = 0;
        for (int i = 0; i < pieces.size(); i++) {
            if (pieces.get(i).isSamePosition(position)) {
                target = i;
                break;
            }
        }
        pieces.remove(target);
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
