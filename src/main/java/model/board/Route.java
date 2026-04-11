package model.board;

import model.piece.Piece;

import java.util.List;

public class Route {
    private final List<Piece> pieces;

    public Route(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public int countPieces() {
        return pieces.size();
    }

    public boolean hasPiece() {
        return !pieces.isEmpty();
    }

    public boolean hasCannon() {
        return pieces.stream().anyMatch(Piece::isCannon);
    }
}
