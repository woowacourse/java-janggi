package janggi.domain.piece.generator;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.dynamic.CannonMovementStrategy;
import janggi.domain.piece.movement.dynamic.PawnMovementStrategy;
import janggi.domain.piece.movement.dynamic.RookMovementStrategy;
import janggi.domain.piece.movement.fixed.GuardMovementStrategy;
import janggi.domain.piece.movement.fixed.KingMovementStrategy;
import java.util.ArrayList;
import java.util.List;

public class DefaultHanPieceGenerator implements HanPieceGenerator {

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

    @Override
    public List<Piece> generate(KnightElephantSetting knightElephantSetting) {
        List<Piece> pieces = new ArrayList<>(generateDefaultPieces());
        pieces.addAll(knightElephantSetting.make(SIDE, KNIGHT_ELEPHANT_POSITIONS));
        return pieces;
    }

    private List<Piece> generateDefaultPieces() {
        return List.of(
            new Piece(PieceType.PAWN, new PawnMovementStrategy(), SIDE, 0, 3),
            new Piece(PieceType.PAWN, new PawnMovementStrategy(), SIDE, 2, 3),
            new Piece(PieceType.PAWN, new PawnMovementStrategy(), SIDE, 4, 3),
            new Piece(PieceType.PAWN, new PawnMovementStrategy(), SIDE, 6, 3),
            new Piece(PieceType.PAWN, new PawnMovementStrategy(), SIDE, 8, 3),

            new Piece(PieceType.CANNON, new CannonMovementStrategy(), SIDE, 1, 2),
            new Piece(PieceType.CANNON, new CannonMovementStrategy(), SIDE, 7, 2),

            new Piece(PieceType.ROOK, new RookMovementStrategy(), SIDE, 0, 0),
            new Piece(PieceType.ROOK, new RookMovementStrategy(), SIDE, 8, 0),

            new Piece(PieceType.GUARD, new GuardMovementStrategy(), SIDE, 3, 0),
            new Piece(PieceType.GUARD, new GuardMovementStrategy(), SIDE, 5, 0),

            new Piece(PieceType.KING, new KingMovementStrategy(), SIDE, 4, 1)
        );
    }
}
