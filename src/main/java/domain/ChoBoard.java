package domain;

import static domain.piece.PieceType.CANNON;
import static domain.piece.PieceType.CHARIOT;
import static domain.piece.PieceType.CHO_PAWN;
import static domain.piece.PieceType.ELEPHANT;
import static domain.piece.PieceType.HORSE;
import static domain.piece.PieceType.KING;
import static domain.piece.PieceType.SCHOLAR;

import domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChoBoard {
    private Map<BoardLocation, Piece> pieces;

    private ChoBoard(Map<BoardLocation, Piece> pieces) {
        this.pieces = pieces;
    }

    public static ChoBoard createWithPieces(List<BoardLocation> horseBoardLocations, List<BoardLocation> elephantBoardLocations) {
        Map<BoardLocation, Piece> pieces = new HashMap<>();
        horseBoardLocations.forEach(horseLocation -> pieces.put(horseLocation, new Piece(HORSE)));
        elephantBoardLocations.forEach(elephantLocation -> pieces.put(elephantLocation, new Piece(ELEPHANT)));
        pieces.put(new BoardLocation(1, 10), new Piece(CHARIOT));
        pieces.put(new BoardLocation(4, 10), new Piece(SCHOLAR));
        pieces.put(new BoardLocation(6, 10), new Piece(SCHOLAR));
        pieces.put(new BoardLocation(9, 10), new Piece(CHARIOT));
        pieces.put(new BoardLocation(5, 9), new Piece(KING));
        pieces.put(new BoardLocation(2, 8), new Piece(CANNON));
        pieces.put(new BoardLocation(8, 8), new Piece(CANNON));
        pieces.put(new BoardLocation(1, 7), new Piece(CHO_PAWN));
        pieces.put(new BoardLocation(3, 7), new Piece(CHO_PAWN));
        pieces.put(new BoardLocation(5, 7), new Piece(CHO_PAWN));
        pieces.put(new BoardLocation(7, 7), new Piece(CHO_PAWN));
        pieces.put(new BoardLocation(9, 7), new Piece(CHO_PAWN));
        return new ChoBoard(pieces);
    }

    public Map<BoardLocation, Piece> getPieces() {
        return pieces;
    }

}
