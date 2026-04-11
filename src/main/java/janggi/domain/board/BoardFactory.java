package janggi.domain.board;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.space.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardFactory {

    private BoardFactory() {
    }

    public static Board create(Formation choFormation, Formation hanFormation) {
        Map<Position, Piece> allPieces = new HashMap<>();
        allPieces.putAll(createSidePieces(Side.CHO, choFormation));
        allPieces.putAll(createSidePieces(Side.HAN, hanFormation));
        return new Board(allPieces);
    }

    private static Map<Position, Piece> createSidePieces(Side side, Formation formation) {
        SideLayout layout = SideLayout.from(side);
        Map<Position, Piece> sidePieces = new HashMap<>();

        sidePieces.putAll(getFixedPieces(side, layout));
        sidePieces.putAll(getFormationPieces(side, formation, layout));

        return sidePieces;
    }

    private static Map<Position, Piece> getFixedPieces(Side side, SideLayout layout) {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(4, layout.getGeneralY()), PieceFactory.createGeneral(side));
        putChariotsAndGuards(pieces, side, layout.getBaseY());
        putCannons(pieces, side, layout.getCannonY());
        putSoldiers(pieces, side, layout.getSoldierY());
        return pieces;
    }

    private static void putChariotsAndGuards(Map<Position, Piece> pieces, Side side, int baseY) {
        pieces.put(Position.of(0, baseY), PieceFactory.createChariot(side));
        pieces.put(Position.of(8, baseY), PieceFactory.createChariot(side));
        pieces.put(Position.of(3, baseY), PieceFactory.createGuard(side));
        pieces.put(Position.of(5, baseY), PieceFactory.createGuard(side));
    }

    private static void putCannons(Map<Position, Piece> pieces, Side side, int cannonY) {
        pieces.put(Position.of(1, cannonY), PieceFactory.createCannon(side));
        pieces.put(Position.of(7, cannonY), PieceFactory.createCannon(side));
    }

    private static void putSoldiers(Map<Position, Piece> pieces, Side side, int soldierY) {
        for (int x = 0; x <= 8; x += 2) {
            pieces.put(Position.of(x, soldierY), PieceFactory.createSoldier(side));
        }
    }

    private static Map<Position, Piece> getFormationPieces(Side side, Formation formation, SideLayout layout) {
        List<Piece> orders = formation.getPieceOrders(side);
        List<Integer> xCoordinates = layout.getFormationX();
        int y = layout.getBaseY();

        Map<Position, Piece> formationPieces = new HashMap<>();
        for (int i = 0; i < orders.size(); i++) {
            formationPieces.put(Position.of(xCoordinates.get(i), y), orders.get(i));
        }
        return formationPieces;
    }
}
