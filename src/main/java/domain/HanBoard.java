package domain;

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
//        horseBoardLocations.forEach(horseLocation -> pieces.put(horseLocation, new Horse()));
//        elephantBoardLocations.forEach(elephantLocation -> pieces.put(elephantLocation, new Elephant()));
//        pieces.put(new BoardLocation(1, 1), new Piece(PieceType.CHARIOT));
//        pieces.put(new BoardLocation(4, 1), new Scholar());
//        pieces.put(new BoardLocation(6, 1), new Scholar());
//        pieces.put(new BoardLocation(9, 1), new Chariot());
//        pieces.put(new BoardLocation(5, 2), new King());
//        pieces.put(new BoardLocation(2, 3), new Cannon());
//        pieces.put(new BoardLocation(8, 3), new Cannon());
//        pieces.put(new BoardLocation(1, 4), new Soldier());
//        pieces.put(new BoardLocation(3, 4), new Soldier());
//        pieces.put(new BoardLocation(5, 4), new Soldier());
//        pieces.put(new BoardLocation(7, 4), new Soldier());
//        pieces.put(new BoardLocation(9, 4), new Soldier());
        return new HanBoard(pieces);
    }

    public Map<BoardLocation, Piece> getPieces() {
        return pieces;
    }
}
