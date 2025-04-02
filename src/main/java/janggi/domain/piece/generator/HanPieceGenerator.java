package janggi.domain.piece.generator;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.PieceMovementStrategyMap;
import java.util.ArrayList;
import java.util.List;

public class HanPieceGenerator {

    private static final Side SIDE = Side.HAN;
    private static final List<Position> KNIGHT_ELEPHANT_POSITIONS;

    static {
        KNIGHT_ELEPHANT_POSITIONS = List.of(
            new Position(1, 0),
            new Position(2, 0),
            new Position(6, 0),
            new Position(7, 0)
        );
    }

    public List<Piece> generate(KnightElephantSetting knightElephantSetting) {
        List<Piece> pieces = new ArrayList<>(generateDefaultPieces());
        pieces.addAll(knightElephantSetting.make(SIDE, KNIGHT_ELEPHANT_POSITIONS));
        return pieces;
    }

    private List<Piece> generateDefaultPieces() {
        return List.of(
            new Piece(PieceType.PAWN, PieceMovementStrategyMap.get(PieceType.PAWN), SIDE, new Position(0, 3)),
            new Piece(PieceType.PAWN, PieceMovementStrategyMap.get(PieceType.PAWN), SIDE, new Position(2, 3)),
            new Piece(PieceType.PAWN, PieceMovementStrategyMap.get(PieceType.PAWN), SIDE, new Position(4, 3)),
            new Piece(PieceType.PAWN, PieceMovementStrategyMap.get(PieceType.PAWN), SIDE, new Position(6, 3)),
            new Piece(PieceType.PAWN, PieceMovementStrategyMap.get(PieceType.PAWN), SIDE, new Position(8, 3)),

            new Piece(PieceType.CANNON, PieceMovementStrategyMap.get(PieceType.CANNON), SIDE, new Position(1, 2)),
            new Piece(PieceType.CANNON, PieceMovementStrategyMap.get(PieceType.CANNON), SIDE, new Position(7, 2)),

            new Piece(PieceType.ROOK, PieceMovementStrategyMap.get(PieceType.ROOK), SIDE, new Position(0, 0)),
            new Piece(PieceType.ROOK, PieceMovementStrategyMap.get(PieceType.ROOK), SIDE, new Position(8, 0)),

            new Piece(PieceType.GUARD, PieceMovementStrategyMap.get(PieceType.GUARD), SIDE, new Position(3, 0)),
            new Piece(PieceType.GUARD, PieceMovementStrategyMap.get(PieceType.GUARD), SIDE, new Position(5, 0)),

            new Piece(PieceType.KING, PieceMovementStrategyMap.get(PieceType.KING), SIDE, new Position(4, 1))
        );
    }
}
