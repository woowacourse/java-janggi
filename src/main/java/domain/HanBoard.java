package domain;

import static domain.piece.PieceType.CANNON;
import static domain.piece.PieceType.CHARIOT;
import static domain.piece.PieceType.ELEPHANT;
import static domain.piece.PieceType.HAN_PAWN;
import static domain.piece.PieceType.HORSE;
import static domain.piece.PieceType.KING;
import static domain.piece.PieceType.SCHOLAR;

import domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HanBoard {

    private Map<BoardLocation, Piece> pieces;

    private HanBoard(Map<BoardLocation, Piece> pieces) {
        this.pieces = pieces;
    }

    public static HanBoard createWithPieces(List<BoardLocation> horseBoardLocations, List<BoardLocation> elephantBoardLocations) {
        Map<BoardLocation, Piece> pieces = new HashMap<>();
        horseBoardLocations.forEach(horseLocation -> pieces.put(horseLocation, new Piece(HORSE)));
        elephantBoardLocations.forEach(elephantLocation -> pieces.put(elephantLocation, new Piece(ELEPHANT)));
        pieces.put(new BoardLocation(1, 1), new Piece(CHARIOT));
        pieces.put(new BoardLocation(4, 1), new Piece(SCHOLAR));
        pieces.put(new BoardLocation(6, 1), new Piece(SCHOLAR));
        pieces.put(new BoardLocation(9, 1), new Piece(CHARIOT));
        pieces.put(new BoardLocation(5, 2), new Piece(KING));
        pieces.put(new BoardLocation(2, 3), new Piece(CANNON));
        pieces.put(new BoardLocation(8, 3), new Piece(CANNON));
        pieces.put(new BoardLocation(1, 4), new Piece(HAN_PAWN));
        pieces.put(new BoardLocation(3, 4), new Piece(HAN_PAWN));
        pieces.put(new BoardLocation(5, 4), new Piece(HAN_PAWN));
        pieces.put(new BoardLocation(7, 4), new Piece(HAN_PAWN));
        pieces.put(new BoardLocation(9, 4), new Piece(HAN_PAWN));
        return new HanBoard(pieces);
    }

    public Map<BoardLocation, Piece> getPieces() {
        return pieces;
    }
}
