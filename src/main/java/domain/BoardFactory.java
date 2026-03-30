package domain;

import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    private BoardFactory() {
    }

    public static Board create(Formation choFormation, Formation hanFormation) {
        Map<Position, Piece> pieces = new HashMap<>();

        placeFixedPieces(pieces, Side.CHO);
        choFormation.placeElephant(pieces, Side.CHO);

        placeFixedPieces(pieces, Side.HAN);
        hanFormation.placeElephant(pieces, Side.HAN);

        return new Board(pieces);
    }

    private static void placeFixedPieces(Map<Position, Piece> pieces, Side side) {
        placeGeneral(pieces, side);
        placeChariots(pieces, side);
        placeCannons(pieces, side);
        placeGuards(pieces, side);
        placeSoldiers(pieces, side);
    }

    private static void placeGeneral(Map<Position, Piece> pieces, Side side) {
        pieces.put(new Position(4, side.generalY()), new General(side));
    }

    private static void placeChariots(Map<Position, Piece> pieces, Side side) {
        pieces.put(new Position(0, side.baseY()), new Chariot(side));
        pieces.put(new Position(8, side.baseY()), new Chariot(side));
    }

    private static void placeCannons(Map<Position, Piece> pieces, Side side) {
        pieces.put(new Position(1, side.cannonY()), new Cannon(side));
        pieces.put(new Position(7, side.cannonY()), new Cannon(side));
    }

    private static void placeGuards(Map<Position, Piece> pieces, Side side) {
        pieces.put(new Position(3, side.baseY()), new Guard(side));
        pieces.put(new Position(5, side.baseY()), new Guard(side));
    }

    private static void placeSoldiers(Map<Position, Piece> pieces, Side side) {
        for (int x = 0; x <= 8; x += 2) {
            pieces.put(new Position(x, side.soldierY()), new Soldier(side));
        }
    }
}
