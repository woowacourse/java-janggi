package domain.board;

import domain.Position;
import domain.Side;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardFactory {

    private BoardFactory() {
    }

    public static Board create(Formation choFormation, Formation hanFormation) {
        Map<Position, Piece> pieces = new HashMap<>();
        placeFixedPieces(pieces, Side.CHO);
        placeFormationPieces(pieces, Side.CHO, choFormation);
        placeFixedPieces(pieces, Side.HAN);
        placeFormationPieces(pieces, Side.HAN, hanFormation);
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

    private static void placeFormationPieces(Map<Position, Piece> pieces, Side side, Formation formation) {
        List<Integer> formationX = side.formationX();
        List<FormationPiece> orders = formation.getOrders();
        for (int i = 0; i < orders.size(); i++) {
            Position position = Position.of(formationX.get(i), side.baseY());
            pieces.put(position, createFormationPiece(orders.get(i), side));
        }
    }

    private static Piece createFormationPiece(FormationPiece piece, Side side) {
        if (piece == FormationPiece.HORSE) {
            return PieceFactory.createHorse(side);
        }
        if (piece == FormationPiece.ELEPHANT) {
            return PieceFactory.createElephant(side);
        }
        throw new IllegalArgumentException("지원하지 않는 포메이션 기물입니다.");
    }
}
