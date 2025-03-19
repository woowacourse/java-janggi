package fixture;

import domain.BoardLocation;
import domain.Cannon;
import domain.Chariot;
import domain.Elephant;
import domain.Horse;
import domain.King;
import domain.Piece;
import domain.Scholar;
import domain.Soldier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardFixture {

    public static Map<BoardLocation, Piece> createHanPieces(List<BoardLocation> horseBoardLocations, List<BoardLocation> elephantBoardLocations) {
        Map<BoardLocation, Piece> pieces = new HashMap<>();
        horseBoardLocations.forEach(horseLocation -> pieces.put(horseLocation, new Horse()));
        elephantBoardLocations.forEach(elephantLocation -> pieces.put(elephantLocation, new Elephant()));
        pieces.put(new BoardLocation(1, 1), new Chariot());
        pieces.put(new BoardLocation(4, 1), new Scholar());
        pieces.put(new BoardLocation(6, 1), new Scholar());
        pieces.put(new BoardLocation(9, 1), new Chariot());
        pieces.put(new BoardLocation(5, 2), new King());
        pieces.put(new BoardLocation(2, 3), new Cannon());
        pieces.put(new BoardLocation(8, 3), new Cannon());
        pieces.put(new BoardLocation(1, 4), new Soldier());
        pieces.put(new BoardLocation(3, 4), new Soldier());
        pieces.put(new BoardLocation(5, 4), new Soldier());
        pieces.put(new BoardLocation(7, 4), new Soldier());
        pieces.put(new BoardLocation(9, 4), new Soldier());
        return pieces;
    }

    public static Map<BoardLocation, Piece> createChoPieces(List<BoardLocation> horseBoardLocations, List<BoardLocation> elephantBoardLocations) {
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
        return pieces;
    }

}
