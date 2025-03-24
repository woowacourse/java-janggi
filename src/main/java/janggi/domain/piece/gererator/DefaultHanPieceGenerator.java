package janggi.domain.piece.gererator;

import static janggi.domain.piece.gererator.KnightElephantSetting.KNIGHT_ELEPHANT_KNIGHT_ELEPHANT;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.dynamic.CannonMovementStrategy;
import janggi.domain.piece.movement.dynamic.PawnMovementStrategy;
import janggi.domain.piece.movement.dynamic.RookMovementStrategy;
import janggi.domain.piece.movement.fixed.ElephantMovementStrategy;
import janggi.domain.piece.movement.fixed.GuardMovementStrategy;
import janggi.domain.piece.movement.fixed.KingMovementStrategy;
import java.util.ArrayList;
import java.util.List;

public class DefaultHanPieceGenerator implements HanPieceGenerator {

    private static final Side SIDE = Side.HAN;

    @Override
    public List<Piece> generate(KnightElephantSetting knightElephantSetting) {
        List<Piece> pieces = new ArrayList<>(generateDefaultPieces());
        pieces.addAll(generateKnightElephants(knightElephantSetting));
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

    private List<Piece> generateKnightElephants(KnightElephantSetting knightElephantSetting) {

        if (knightElephantSetting == KNIGHT_ELEPHANT_KNIGHT_ELEPHANT) {
            return generateKnightElephantKnightElephant();
        }
        if (knightElephantSetting == KnightElephantSetting.KNIGHT_ELEPHANT_ELEPHANT_KNIGHT) {
            return generateKnightElephantElephantKnight();
        }
        if (knightElephantSetting == KnightElephantSetting.ELEPHANT_KNIGHT_KNIGHT_ELEPHANT) {
            return generateElephantKnightKnightElephant();
        }
        return generateElephantKnightElephantKnight();
    }

    private List<Piece> generateKnightElephantKnightElephant() {
        return List.of(
            new Piece(PieceType.KNIGHT, new KingMovementStrategy(), SIDE, 1, 0),
            new Piece(PieceType.ELEPHANT, new ElephantMovementStrategy(), SIDE, 2, 0),
            new Piece(PieceType.KNIGHT, new KingMovementStrategy(), SIDE, 6, 0),
            new Piece(PieceType.ELEPHANT, new ElephantMovementStrategy(), SIDE, 7, 0)
        );
    }

    private List<Piece> generateKnightElephantElephantKnight() {
        return List.of(
            new Piece(PieceType.KNIGHT, new KingMovementStrategy(), SIDE, 1, 0),
            new Piece(PieceType.ELEPHANT, new ElephantMovementStrategy(), SIDE, 2, 0),
            new Piece(PieceType.ELEPHANT, new ElephantMovementStrategy(), SIDE, 6, 0),
            new Piece(PieceType.KNIGHT, new KingMovementStrategy(), SIDE, 7, 0)
        );
    }

    private List<Piece> generateElephantKnightKnightElephant() {
        return List.of(
            new Piece(PieceType.ELEPHANT, new ElephantMovementStrategy(), SIDE, 1, 0),
            new Piece(PieceType.KNIGHT, new KingMovementStrategy(), SIDE, 2, 0),
            new Piece(PieceType.KNIGHT, new KingMovementStrategy(), SIDE, 6, 0),
            new Piece(PieceType.ELEPHANT, new ElephantMovementStrategy(), SIDE, 7, 0)
        );
    }

    private List<Piece> generateElephantKnightElephantKnight() {
        return List.of(
            new Piece(PieceType.ELEPHANT, new ElephantMovementStrategy(), SIDE, 1, 0),
            new Piece(PieceType.KNIGHT, new KingMovementStrategy(), SIDE, 2, 0),
            new Piece(PieceType.ELEPHANT, new ElephantMovementStrategy(), SIDE, 6, 0),
            new Piece(PieceType.KNIGHT, new KingMovementStrategy(), SIDE, 7, 0)
        );
    }
}
