package fixture;

import domain.Cannon;
import domain.Chariot;
import domain.Elephant;
import domain.Horse;
import domain.King;
import domain.Location;
import domain.Piece;
import domain.Scholar;
import domain.Soldier;
import java.util.HashMap;
import java.util.Map;

public class BoardFixture {

    public static Map<Location, ? super Piece> createHanPieces() {
        Map<Location, ? super Piece> pieces = new HashMap<>();
        pieces.put(new Location(1, 1), new Chariot());
        pieces.put(new Location(2, 1), new Horse());
        pieces.put(new Location(3, 1), new Elephant());
        pieces.put(new Location(4, 1), new Scholar());
        pieces.put(new Location(6, 1), new Scholar());
        pieces.put(new Location(7, 1), new Elephant());
        pieces.put(new Location(8, 1), new Horse());
        pieces.put(new Location(9, 1), new Chariot());
        pieces.put(new Location(5, 2), new King());
        pieces.put(new Location(2, 3), new Cannon());
        pieces.put(new Location(8, 3), new Cannon());
        pieces.put(new Location(1, 4), new Soldier());
        pieces.put(new Location(3, 4), new Soldier());
        pieces.put(new Location(5, 4), new Soldier());
        pieces.put(new Location(7, 4), new Soldier());
        pieces.put(new Location(9, 4), new Soldier());
        return pieces;
    }
}
