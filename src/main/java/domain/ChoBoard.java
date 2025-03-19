package domain;

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
        horseBoardLocations.forEach(horseLocation -> pieces.put(horseLocation, new Horse()));
        elephantBoardLocations.forEach(elephantLocation -> pieces.put(elephantLocation, new Elephant()));
        pieces.put(new BoardLocation(1, 10), new Chariot());
        pieces.put(new BoardLocation(4, 10), new Scholar());
        pieces.put(new BoardLocation(6, 10), new Scholar());
        pieces.put(new BoardLocation(9, 10), new Chariot());
        pieces.put(new BoardLocation(5, 9), new King());
        pieces.put(new BoardLocation(2, 8), new Cannon());
        pieces.put(new BoardLocation(8, 8), new Cannon());
        pieces.put(new BoardLocation(1, 7), new Soldier());
        pieces.put(new BoardLocation(3, 7), new Soldier());
        pieces.put(new BoardLocation(5, 7), new Soldier());
        pieces.put(new BoardLocation(7, 7), new Soldier());
        pieces.put(new BoardLocation(9, 7), new Soldier());
        return new ChoBoard(pieces);
    }

    public Map<BoardLocation, Piece> getPieces() {
        return pieces;
    }

}
