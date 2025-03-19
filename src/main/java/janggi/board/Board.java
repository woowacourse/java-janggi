package janggi.board;

import janggi.piece.Piece;
import janggi.position.Position;
import java.util.Map;

public class Board {

    private final Map<Piece, Position> positions;
    private final Map<Position, Piece> pieces;

    public Board(Map<Piece, Position> positions, Map<Position, Piece> pieces) {
        this.positions = positions;
        this.pieces = pieces;
    }

    public Map<Piece, Position> getPositions() {
        return positions;
    }

    public Map<Position, Piece> getPieces() {
        return pieces;
    }
}
