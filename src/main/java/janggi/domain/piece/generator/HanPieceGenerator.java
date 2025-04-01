package janggi.domain.piece.generator;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.MovementStrategyContext;
import janggi.domain.piece.movement.normal.dynamic.CannonMovementStrategy;
import janggi.domain.piece.movement.normal.dynamic.PawnMovementStrategy;
import janggi.domain.piece.movement.normal.dynamic.RookMovementStrategy;
import janggi.domain.piece.movement.normal.fixed.GuardMovementStrategy;
import janggi.domain.piece.movement.normal.fixed.KingMovementStrategy;
import janggi.domain.piece.movement.palace.CannonPalaceMovementStrategy;
import janggi.domain.piece.movement.palace.GuardPalaceMovementStrategy;
import janggi.domain.piece.movement.palace.KingPalaceMovementStrategy;
import janggi.domain.piece.movement.palace.PawnPalaceMovementStrategy;
import janggi.domain.piece.movement.palace.RookPalaceMovementStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HanPieceGenerator {

    private static final Side SIDE = Side.HAN;
    private static final Map<PieceType, MovementStrategyContext> MOVEMENT_STRATEGY_MAP;
    private static final List<Position> KNIGHT_ELEPHANT_POSITIONS;

    static {
        KNIGHT_ELEPHANT_POSITIONS = List.of(
            new Position(1, 0),
            new Position(2, 0),
            new Position(6, 0),
            new Position(7, 0)
        );

        MOVEMENT_STRATEGY_MAP = Map.of(
            PieceType.PAWN, new MovementStrategyContext(
                new PawnMovementStrategy(),
                new PawnPalaceMovementStrategy(new PawnMovementStrategy())
            ),
            PieceType.CANNON,
            new MovementStrategyContext(
                new CannonMovementStrategy(),
                new CannonPalaceMovementStrategy(new CannonMovementStrategy())
            ),
            PieceType.ROOK, new MovementStrategyContext(
                new RookMovementStrategy(),
                new RookPalaceMovementStrategy(new RookMovementStrategy())
            ),
            PieceType.GUARD, new MovementStrategyContext(
                new GuardMovementStrategy(),
                new GuardPalaceMovementStrategy(new GuardMovementStrategy())
            ),
            PieceType.KING, new MovementStrategyContext(
                new KingMovementStrategy(),
                new KingPalaceMovementStrategy(new KingMovementStrategy())
            )
        );
    }

    public List<Piece> generate(KnightElephantSetting knightElephantSetting) {
        List<Piece> pieces = new ArrayList<>(generateDefaultPieces());
        pieces.addAll(knightElephantSetting.make(SIDE, KNIGHT_ELEPHANT_POSITIONS));
        return pieces;
    }

    private List<Piece> generateDefaultPieces() {
        return List.of(
            new Piece(PieceType.PAWN, MOVEMENT_STRATEGY_MAP.get(PieceType.PAWN), SIDE, new Position(0, 3)),
            new Piece(PieceType.PAWN, MOVEMENT_STRATEGY_MAP.get(PieceType.PAWN), SIDE, new Position(2, 3)),
            new Piece(PieceType.PAWN, MOVEMENT_STRATEGY_MAP.get(PieceType.PAWN), SIDE, new Position(4, 3)),
            new Piece(PieceType.PAWN, MOVEMENT_STRATEGY_MAP.get(PieceType.PAWN), SIDE, new Position(6, 3)),
            new Piece(PieceType.PAWN, MOVEMENT_STRATEGY_MAP.get(PieceType.PAWN), SIDE, new Position(8, 3)),

            new Piece(PieceType.CANNON, MOVEMENT_STRATEGY_MAP.get(PieceType.CANNON), SIDE, new Position(1, 2)),
            new Piece(PieceType.CANNON, MOVEMENT_STRATEGY_MAP.get(PieceType.CANNON), SIDE, new Position(7, 2)),

            new Piece(PieceType.ROOK, MOVEMENT_STRATEGY_MAP.get(PieceType.ROOK), SIDE, new Position(0, 0)),
            new Piece(PieceType.ROOK, MOVEMENT_STRATEGY_MAP.get(PieceType.ROOK), SIDE, new Position(8, 0)),

            new Piece(PieceType.GUARD, MOVEMENT_STRATEGY_MAP.get(PieceType.GUARD), SIDE, new Position(3, 0)),
            new Piece(PieceType.GUARD, MOVEMENT_STRATEGY_MAP.get(PieceType.GUARD), SIDE, new Position(5, 0)),

            new Piece(PieceType.KING, MOVEMENT_STRATEGY_MAP.get(PieceType.KING), SIDE, new Position(4, 1))
        );
    }
}
