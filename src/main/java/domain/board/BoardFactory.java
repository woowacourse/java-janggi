package domain.board;

import domain.game.Position;
import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.PieceType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardFactory {
    private static final BoardLayoutMapper BOARD_LAYOUT_MAPPER = new BoardLayoutMapper();

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
        BoardLayout boardLayout = BOARD_LAYOUT_MAPPER.get(side);
        pieces.put(Position.of(4, boardLayout.generalY()), PieceFactory.createGeneral(side));
    }

    private static void placeChariots(Map<Position, Piece> pieces, Side side) {
        BoardLayout boardLayout = BOARD_LAYOUT_MAPPER.get(side);
        pieces.put(Position.of(0, boardLayout.baseY()), PieceFactory.createChariot(side));
        pieces.put(Position.of(8, boardLayout.baseY()), PieceFactory.createChariot(side));
    }

    private static void placeCannons(Map<Position, Piece> pieces, Side side) {
        BoardLayout boardLayout = BOARD_LAYOUT_MAPPER.get(side);
        pieces.put(Position.of(1, boardLayout.cannonY()), PieceFactory.createCannon(side));
        pieces.put(Position.of(7, boardLayout.cannonY()), PieceFactory.createCannon(side));
    }

    private static void placeGuards(Map<Position, Piece> pieces, Side side) {
        BoardLayout boardLayout = BOARD_LAYOUT_MAPPER.get(side);
        pieces.put(Position.of(3, boardLayout.baseY()), PieceFactory.createGuard(side));
        pieces.put(Position.of(5, boardLayout.baseY()), PieceFactory.createGuard(side));
    }

    private static void placeSoldiers(Map<Position, Piece> pieces, Side side) {
        BoardLayout boardLayout = BOARD_LAYOUT_MAPPER.get(side);
        for (int x = 0; x <= 8; x += 2) {
            pieces.put(Position.of(x, boardLayout.soldierY()), PieceFactory.createSoldier(side));
        }
    }

    private static void placeFormationPieces(Map<Position, Piece> pieces, Side side, Formation formation) {
        BoardLayout boardLayout = BOARD_LAYOUT_MAPPER.get(side);
        List<Integer> formationX = boardLayout.formationX();
        List<PieceType> orders = formation.getOrders();
        for (int i = 0; i < orders.size(); i++) {
            Position position = Position.of(formationX.get(i), boardLayout.baseY());
            pieces.put(position, createFormationPiece(orders.get(i), side));
        }
    }

    private static Piece createFormationPiece(PieceType piece, Side side) {
        if (piece == PieceType.HORSE) {
            return PieceFactory.createHorse(side);
        }
        if (piece == PieceType.ELEPHANT) {
            return PieceFactory.createElephant(side);
        }
        throw new IllegalArgumentException("지원하지 않는 포메이션 기물입니다.");
    }
}
