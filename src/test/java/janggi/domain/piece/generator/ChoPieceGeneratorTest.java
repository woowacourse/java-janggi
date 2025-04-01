package janggi.domain.piece.generator;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.ReplaceUnderBar;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
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
import janggi.domain.piece.movement.palace.PawnPalaceMovementStrategy;
import janggi.domain.piece.movement.palace.RookPalaceMovementStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@ReplaceUnderBar
public class ChoPieceGeneratorTest {

    private static final Side SIDE = Side.CHO;
    private static List<Piece> PIECES;
    private static Map<PieceType, MovementStrategyContext> MOVEMENT_STRATEGY_MAP;
    private final ChoPieceGenerator choPieceGenerator = new ChoPieceGenerator();

    @BeforeAll
    static void setUpMovementStrategyMap() {
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
            ),
            PieceType.ELEPHANT, new MovementStrategyContext(
                new ElephantMovementStrategy(),
                new ElephantPalaceMovementStrategy(new ElephantMovementStrategy())
            ),
            PieceType.KNIGHT, new MovementStrategyContext(
                new KnightMovementStrategy(),
                new KingPalaceMovementStrategy(new KnightMovementStrategy())
            )
        );
    }

    @BeforeEach
    void setUpDefaultPieces() {
        PIECES = new ArrayList<>();
        PIECES.add(new Piece(PieceType.PAWN, MOVEMENT_STRATEGY_MAP.get(PieceType.PAWN), SIDE, new Position(0, 6)));
        PIECES.add(new Piece(PieceType.PAWN, MOVEMENT_STRATEGY_MAP.get(PieceType.PAWN), SIDE, new Position(2, 6)));
        PIECES.add(new Piece(PieceType.PAWN, MOVEMENT_STRATEGY_MAP.get(PieceType.PAWN), SIDE, new Position(4, 6)));
        PIECES.add(new Piece(PieceType.PAWN, MOVEMENT_STRATEGY_MAP.get(PieceType.PAWN), SIDE, new Position(6, 6)));
        PIECES.add(new Piece(PieceType.PAWN, MOVEMENT_STRATEGY_MAP.get(PieceType.PAWN), SIDE, new Position(8, 6)));

        PIECES.add(new Piece(PieceType.CANNON, MOVEMENT_STRATEGY_MAP.get(PieceType.CANNON), SIDE, new Position(1, 7)));
        PIECES.add(new Piece(PieceType.CANNON, MOVEMENT_STRATEGY_MAP.get(PieceType.CANNON), SIDE, new Position(7, 7)));

        PIECES.add(new Piece(PieceType.ROOK, MOVEMENT_STRATEGY_MAP.get(PieceType.ROOK), SIDE, new Position(0, 9)));
        PIECES.add(new Piece(PieceType.ROOK, MOVEMENT_STRATEGY_MAP.get(PieceType.ROOK), SIDE, new Position(8, 9)));

        PIECES.add(new Piece(PieceType.GUARD, MOVEMENT_STRATEGY_MAP.get(PieceType.GUARD), SIDE, new Position(3, 9)));
        PIECES.add(new Piece(PieceType.GUARD, MOVEMENT_STRATEGY_MAP.get(PieceType.GUARD), SIDE, new Position(5, 9)));

        PIECES.add(new Piece(PieceType.KING, MOVEMENT_STRATEGY_MAP.get(PieceType.KING), SIDE, new Position(4, 8)));
    }

    @Test
    void 초나라_마상마상_배치를_생성한다() {
        List<Piece> generatedPieces = choPieceGenerator.generate(KnightElephantSetting.KNIGHT_ELEPHANT_KNIGHT_ELEPHANT);

        PIECES.addAll(List.of(
            new Piece(PieceType.KNIGHT, MOVEMENT_STRATEGY_MAP.get(PieceType.KNIGHT), SIDE, new Position(1, 9)),
            new Piece(PieceType.ELEPHANT, MOVEMENT_STRATEGY_MAP.get(PieceType.ELEPHANT), SIDE, new Position(2, 9)),
            new Piece(PieceType.KNIGHT, MOVEMENT_STRATEGY_MAP.get(PieceType.KNIGHT), SIDE, new Position(6, 9)),
            new Piece(PieceType.ELEPHANT, MOVEMENT_STRATEGY_MAP.get(PieceType.ELEPHANT), SIDE, new Position(7, 9))
        ));

        assertThat(generatedPieces).containsExactlyInAnyOrderElementsOf(PIECES);
    }

    @Test
    void 초나라_마상상마_배치를_생성한다() {
        List<Piece> generatedPieces = choPieceGenerator.generate(KnightElephantSetting.KNIGHT_ELEPHANT_ELEPHANT_KNIGHT);

        PIECES.addAll(List.of(
            new Piece(PieceType.KNIGHT, MOVEMENT_STRATEGY_MAP.get(PieceType.KNIGHT), SIDE, new Position(1, 9)),
            new Piece(PieceType.ELEPHANT, MOVEMENT_STRATEGY_MAP.get(PieceType.ELEPHANT), SIDE, new Position(2, 9)),
            new Piece(PieceType.ELEPHANT, MOVEMENT_STRATEGY_MAP.get(PieceType.ELEPHANT), SIDE, new Position(6, 9)),
            new Piece(PieceType.KNIGHT, MOVEMENT_STRATEGY_MAP.get(PieceType.KNIGHT), SIDE, new Position(7, 9))
        ));

        assertThat(generatedPieces).containsExactlyInAnyOrderElementsOf(PIECES);
    }

    @Test
    void 초나라_상마마상_배치를_생성한다() {
        List<Piece> generatedPieces = choPieceGenerator.generate(KnightElephantSetting.ELEPHANT_KNIGHT_KNIGHT_ELEPHANT);

        PIECES.addAll(List.of(
            new Piece(PieceType.ELEPHANT, MOVEMENT_STRATEGY_MAP.get(PieceType.ELEPHANT), SIDE, new Position(1, 9)),
            new Piece(PieceType.KNIGHT, MOVEMENT_STRATEGY_MAP.get(PieceType.KNIGHT), SIDE, new Position(2, 9)),
            new Piece(PieceType.KNIGHT, MOVEMENT_STRATEGY_MAP.get(PieceType.KNIGHT), SIDE, new Position(6, 9)),
            new Piece(PieceType.ELEPHANT, MOVEMENT_STRATEGY_MAP.get(PieceType.ELEPHANT), SIDE, new Position(7, 9))
        ));

        assertThat(generatedPieces).containsExactlyInAnyOrderElementsOf(PIECES);
    }

    @Test
    void 초나라_상마상마_배치를_생성한다() {
        List<Piece> generatedPieces = choPieceGenerator.generate(KnightElephantSetting.ELEPHANT_KNIGHT_ELEPHANT_KNIGHT);

        PIECES.addAll(List.of(
            new Piece(PieceType.ELEPHANT, MOVEMENT_STRATEGY_MAP.get(PieceType.ELEPHANT), SIDE, new Position(1, 9)),
            new Piece(PieceType.KNIGHT, MOVEMENT_STRATEGY_MAP.get(PieceType.KNIGHT), SIDE, new Position(2, 9)),
            new Piece(PieceType.ELEPHANT, MOVEMENT_STRATEGY_MAP.get(PieceType.ELEPHANT), SIDE, new Position(6, 9)),
            new Piece(PieceType.KNIGHT, MOVEMENT_STRATEGY_MAP.get(PieceType.KNIGHT), SIDE, new Position(7, 9))
        ));

        assertThat(generatedPieces).containsExactlyInAnyOrderElementsOf(PIECES);
    }
}
