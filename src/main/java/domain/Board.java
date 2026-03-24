package domain;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Position, Piece> pieces = new HashMap<Position, Piece>();

    public Board() {
        pieces.put(new Position(0,6), new Piece(PieceType.PAWN));
        pieces.put(new Position(2,6), new Piece(PieceType.PAWN));
        pieces.put(new Position(4,6), new Piece(PieceType.PAWN));
        pieces.put(new Position(6,6), new Piece(PieceType.PAWN));
        pieces.put(new Position(8,6), new Piece(PieceType.PAWN));

        pieces.put(new Position(1,7), new Piece(PieceType.HORSE));
        pieces.put(new Position(7,7), new Piece(PieceType.HORSE));


        pieces.put(new Position(0,9), new Piece(PieceType.ROOK));
        pieces.put(new Position(8,9), new Piece(PieceType.ROOK));


        pieces.put(new Position(1,9), new Piece(PieceType.HORSE));
        pieces.put(new Position(7,9), new Piece(PieceType.HORSE));


        pieces.put(new Position(2,9), new Piece(PieceType.ELEPHANT));
        pieces.put(new Position(6,9), new Piece(PieceType.ELEPHANT));

        pieces.put(new Position(3,9), new Piece(PieceType.ADVISOR));
        pieces.put(new Position(5,9), new Piece(PieceType.ADVISOR));

        pieces.put(new Position(4,8), new Piece(PieceType.KING));
    }

    public Piece getPiece(Position position) {
        return pieces.get(position);
    }

}
