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
        pieces.put(Position.of(4, side.generalY()), PieceFactory.createGeneral(side));
    }

    private static void placeChariots(Map<Position, Piece> pieces, Side side) {
        pieces.put(Position.of(0, side.baseY()), PieceFactory.createChariot(side));
        pieces.put(Position.of(8, side.baseY()), PieceFactory.createChariot(side));
    }

    private static void placeCannons(Map<Position, Piece> pieces, Side side) {
        pieces.put(Position.of(1, side.cannonY()), PieceFactory.createCannon(side));
        pieces.put(Position.of(7, side.cannonY()), PieceFactory.createCannon(side));
    }

    private static void placeGuards(Map<Position, Piece> pieces, Side side) {
        pieces.put(Position.of(3, side.baseY()), PieceFactory.createGuard(side));
        pieces.put(Position.of(5, side.baseY()), PieceFactory.createGuard(side));
    }

    private static void placeSoldiers(Map<Position, Piece> pieces, Side side) {
        for (int x = 0; x <= 8; x += 2) {
            pieces.put(Position.of(x, side.soldierY()), PieceFactory.createSoldier(side));
        }
    }
}
