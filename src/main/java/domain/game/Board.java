package domain.game;

import domain.vo.Arrangements;

public class Board {
    private final Pieces pieces;

    public Board(Pieces pieces) {
        this.pieces = pieces;
    }

    public static Board of(Arrangements arrangements) {
        return new Board(Pieces.of(arrangements));
    }

    public Piece getPieceAt(Position position) {
        return pieces.getPieceAt(position);
    }
}
