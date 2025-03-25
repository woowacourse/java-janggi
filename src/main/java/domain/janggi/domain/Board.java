package domain.janggi.domain;

import domain.janggi.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public class Board {

    private final List<Piece> pieces;

    public Board() {
        this.pieces = new ArrayList<>();
    }

    public Board(final List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public void putPieces(final List<Piece> pieces) {
        this.pieces.addAll(pieces);
    }

    public boolean isExists(final Position position) {
        return pieces.stream().anyMatch(piece -> piece.isSamePosition(position));
    }

    public boolean anyMatchSameTeam(final Piece piece, final Position newPosition) {
        return pieces.stream().anyMatch(p -> p.isSamePosition(newPosition) && p.isSameTeam(piece));
    }

    public void remove(final Position position) {
        pieces.removeIf(piece -> piece.isSamePosition(position));
    }

    public Piece findPiece(final Position position) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 기물의 위치를 입력해주세요."));
    }
}
