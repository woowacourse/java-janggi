package janggi.domain.piece.movement;

import janggi.domain.piece.PieceType;
import janggi.domain.piece.movement.normal.dynamic.CannonMovementStrategy;
import janggi.domain.piece.movement.normal.dynamic.PawnMovementStrategy;
import janggi.domain.piece.movement.normal.dynamic.RookMovementStrategy;
import janggi.domain.piece.movement.normal.fixed.ElephantMovementStrategy;
import janggi.domain.piece.movement.normal.fixed.GuardMovementStrategy;
import janggi.domain.piece.movement.normal.fixed.KingMovementStrategy;
import janggi.domain.piece.movement.normal.fixed.KnightMovementStrategy;
import janggi.domain.piece.movement.palace.CannonPalaceMovementStrategy;
import janggi.domain.piece.movement.palace.ElephantPalaceMovementStrategy;
import janggi.domain.piece.movement.palace.GuardPalaceMovementStrategy;
import janggi.domain.piece.movement.palace.KingPalaceMovementStrategy;
import janggi.domain.piece.movement.palace.KnightPalaceMovementStrategy;
import janggi.domain.piece.movement.palace.PawnPalaceMovementStrategy;
import janggi.domain.piece.movement.palace.RookPalaceMovementStrategy;
import java.util.Map;

public final class PieceMovementStrategyMap {

    private static final Map<PieceType, MovementStrategyContext> MOVEMENT_STRATEGY_MAP;

    static {
        MOVEMENT_STRATEGY_MAP = Map.of(
            PieceType.KNIGHT, new MovementStrategyContext(
                new KnightMovementStrategy(),
                new KnightPalaceMovementStrategy(new KnightMovementStrategy())
            ),
            PieceType.ELEPHANT, new MovementStrategyContext(
                new ElephantMovementStrategy(),
                new ElephantPalaceMovementStrategy(new ElephantMovementStrategy())
            ),
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

    private PieceMovementStrategyMap() {
        throw new IllegalStateException("인스턴스화 할 수 없습니다.");
    }

    public static MovementStrategyContext get(PieceType pieceType) {
        return MOVEMENT_STRATEGY_MAP.get(pieceType);
    }
}
