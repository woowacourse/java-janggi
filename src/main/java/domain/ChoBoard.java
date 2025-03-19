package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChoBoard {
    private Map<Location, Piece> pieces;

    private ChoBoard(Map<Location, Piece> pieces) {
        this.pieces = pieces;
    }

    public static ChoBoard createWithPieces(List<Location> horseLocations, List<Location> elephantLocations) {
        Map<Location, Piece> pieces = new HashMap<>();
        horseLocations.forEach(horseLocation -> pieces.put(horseLocation, new Horse()));
        elephantLocations.forEach(elephantLocation -> pieces.put(elephantLocation, new Elephant()));
        pieces.put(new Location(1, 10), new Chariot());
        pieces.put(new Location(4, 10), new Scholar());
        pieces.put(new Location(6, 10), new Scholar());
        pieces.put(new Location(9, 10), new Chariot());
        pieces.put(new Location(5, 9), new King());
        pieces.put(new Location(2, 8), new Cannon());
        pieces.put(new Location(8, 8), new Cannon());
        pieces.put(new Location(1, 7), new Soldier());
        pieces.put(new Location(3, 7), new Soldier());
        pieces.put(new Location(5, 7), new Soldier());
        pieces.put(new Location(7, 7), new Soldier());
        pieces.put(new Location(9, 7), new Soldier());
        return new ChoBoard(pieces);
    }

    public Map<Location, Piece> getPieces() {
        return pieces;
    }

}
