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

public class DefaultChoPieceGenerator implements ChoPieceGenerator {

    private static final Side SIDE = Side.CHO;
    private static final Map<PieceType, MovementStrategyContext> MOVEMENT_STRATEGY_MAP;
    private static final List<Position> KNIGHT_ELEPHANT_POSITIONS;

    static {
        KNIGHT_ELEPHANT_POSITIONS = List.of(
            new Position(1, 9),
            new Position(2, 9),
            new Position(6, 9),
            new Position(7, 9)
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

    @Override
    public List<Piece> generate(KnightElephantSetting knightElephantSetting) {
        List<Piece> pieces = new ArrayList<>(generateDefaultPieces());
        pieces.addAll(knightElephantSetting.make(SIDE, KNIGHT_ELEPHANT_POSITIONS));
        return pieces;
    }

    private List<Piece> generateDefaultPieces() {
        return List.of(
            new Piece(PieceType.PAWN, MOVEMENT_STRATEGY_MAP.get(PieceType.PAWN), SIDE, 0, 6),
            new Piece(PieceType.PAWN, MOVEMENT_STRATEGY_MAP.get(PieceType.PAWN), SIDE, 2, 6),
            new Piece(PieceType.PAWN, MOVEMENT_STRATEGY_MAP.get(PieceType.PAWN), SIDE, 4, 6),
            new Piece(PieceType.PAWN, MOVEMENT_STRATEGY_MAP.get(PieceType.PAWN), SIDE, 6, 6),
            new Piece(PieceType.PAWN, MOVEMENT_STRATEGY_MAP.get(PieceType.PAWN), SIDE, 8, 6),

            new Piece(PieceType.CANNON, MOVEMENT_STRATEGY_MAP.get(PieceType.CANNON), SIDE, 1, 7),
            new Piece(PieceType.CANNON, MOVEMENT_STRATEGY_MAP.get(PieceType.CANNON), SIDE, 7, 7),

            new Piece(PieceType.ROOK, MOVEMENT_STRATEGY_MAP.get(PieceType.ROOK), SIDE, 0, 9),
            new Piece(PieceType.ROOK, MOVEMENT_STRATEGY_MAP.get(PieceType.ROOK), SIDE, 8, 9),

            new Piece(PieceType.GUARD, MOVEMENT_STRATEGY_MAP.get(PieceType.GUARD), SIDE, 3, 9),
            new Piece(PieceType.GUARD, MOVEMENT_STRATEGY_MAP.get(PieceType.GUARD), SIDE, 5, 9),

            new Piece(PieceType.KING, MOVEMENT_STRATEGY_MAP.get(PieceType.KING), SIDE, 4, 8)
        );
    }
}
