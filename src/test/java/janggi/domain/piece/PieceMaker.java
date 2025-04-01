package janggi.domain.piece;

import janggi.domain.piece.movement.MovementStrategy;
import janggi.domain.piece.movement.MovementStrategyContext;
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
import janggi.domain.piece.movement.palace.PalaceMovementStrategy;
import janggi.domain.piece.movement.palace.PawnPalaceMovementStrategy;
import janggi.domain.piece.movement.palace.RookPalaceMovementStrategy;
import java.util.Arrays;

public final class PieceMaker {

    private PieceMaker() throws IllegalAccessException {
        throw new IllegalAccessException("유틸 클래스는 초기화될 수 없습니다.");
    }

    public static Piece createPiece(PieceType pieceType, Side side, Position position) {
        return new Piece(
            pieceType,
            createMovementStrategyContext(pieceType),
            side,
            position
        );
    }

    private static MovementStrategyContext createMovementStrategyContext(PieceType pieceType) {
        MovementStrategy defaultMovementStrategy = DefaultStrategies.get(pieceType);
        PalaceMovementStrategy palaceMovementStrategy = PalaceStrategies.get(pieceType);
        return new MovementStrategyContext(defaultMovementStrategy, palaceMovementStrategy);
    }

    private enum DefaultStrategies {
        CANNON(PieceType.CANNON, new CannonMovementStrategy()),
        ELEPHANT(PieceType.ELEPHANT, new ElephantMovementStrategy()),
        GUARD(PieceType.GUARD, new GuardMovementStrategy()),
        KING(PieceType.KING, new KingMovementStrategy()),
        KNIGHT(PieceType.KNIGHT, new KnightMovementStrategy()),
        PAWN(PieceType.PAWN, new PawnMovementStrategy()),
        ROOK(PieceType.ROOK, new RookMovementStrategy());

        private final PieceType pieceType;
        private final MovementStrategy movementStrategy;

        DefaultStrategies(PieceType pieceType, MovementStrategy movementStrategy) {
            this.pieceType = pieceType;
            this.movementStrategy = movementStrategy;
        }

        static MovementStrategy get(PieceType pieceType) {
            return Arrays.stream(values())
                .filter(strategy -> strategy.pieceType == pieceType)
                .map(strategy -> strategy.movementStrategy)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기물입니다."));
        }
    }

    private enum PalaceStrategies {
        CANNON(PieceType.CANNON, new CannonPalaceMovementStrategy(DefaultStrategies.CANNON.movementStrategy)),
        ELEPHANT(PieceType.ELEPHANT, new ElephantPalaceMovementStrategy(DefaultStrategies.ELEPHANT.movementStrategy)),
        GUARD(PieceType.GUARD, new GuardPalaceMovementStrategy(DefaultStrategies.GUARD.movementStrategy)),
        KING(PieceType.KING, new KingPalaceMovementStrategy(DefaultStrategies.KING.movementStrategy)),
        KNIGHT(PieceType.KNIGHT, new KnightPalaceMovementStrategy(DefaultStrategies.KNIGHT.movementStrategy)),
        PAWN(PieceType.PAWN, new PawnPalaceMovementStrategy(DefaultStrategies.PAWN.movementStrategy)),
        ROOK(PieceType.ROOK, new RookPalaceMovementStrategy(DefaultStrategies.ROOK.movementStrategy));

        private final PieceType pieceType;
        private final PalaceMovementStrategy palaceMovementStrategy;

        PalaceStrategies(PieceType pieceType, PalaceMovementStrategy palaceMovementStrategy) {
            this.pieceType = pieceType;
            this.palaceMovementStrategy = palaceMovementStrategy;
        }

        static PalaceMovementStrategy get(PieceType pieceType) {
            return Arrays.stream(values())
                .filter(strategy -> strategy.pieceType == pieceType)
                .map(strategy -> strategy.palaceMovementStrategy)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기물입니다."));
        }
    }
}
