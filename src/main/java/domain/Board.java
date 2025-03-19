package domain;

import java.util.HashSet;
import java.util.Set;

public class Board {

//    public static final int MIN_ROW = 1;
//    public static final int MIN_COLUMN = 1;
//    public static final int MAX_ROW = 10;
//    public static final int MAX_COLUMN = 9;

    private final Set<Piece> pieces;

    public Board(final Set<Piece> pieces) {
        this.pieces = new HashSet<>(pieces);
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

    public Set<Piece> getPieces() {
        return pieces;
    }

//    public boolean isValidPosition(Position position) {
//        int nextColumn = position.getX();
//        int nextRow = position.getY();
//        return (nextColumn < Board.MIN_COLUMN ||nextColumn > Board.MAX_COLUMN ||
//                nextRow < Board.MIN_ROW || nextRow > Board.MAX_ROW
//        );
//    }

}
