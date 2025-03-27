package janggi.domain.piece.generator;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.ReplaceUnderBar;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@ReplaceUnderBar
public class DefaultChoPieceGeneratorTest {

    private static final Side SIDE = Side.CHO;
    private static List<Piece> PIECES;
    private final ChoPieceGenerator choPieceGenerator = new DefaultChoPieceGenerator();

    @BeforeEach
    void setUpDefaultPieces() {
        PIECES = new ArrayList<>();
        PIECES.add(new Piece(PieceType.PAWN, new PawnMovementStrategy(), SIDE, 0, 6));
        PIECES.add(new Piece(PieceType.PAWN, new PawnMovementStrategy(), SIDE, 2, 6));
        PIECES.add(new Piece(PieceType.PAWN, new PawnMovementStrategy(), SIDE, 4, 6));
        PIECES.add(new Piece(PieceType.PAWN, new PawnMovementStrategy(), SIDE, 6, 6));
        PIECES.add(new Piece(PieceType.PAWN, new PawnMovementStrategy(), SIDE, 8, 6));
        PIECES.add(new Piece(PieceType.CANNON, new CannonMovementStrategy(), SIDE, 1, 7));
        PIECES.add(new Piece(PieceType.CANNON, new CannonMovementStrategy(), SIDE, 7, 7));
        PIECES.add(new Piece(PieceType.ROOK, new RookMovementStrategy(), SIDE, 0, 9));
        PIECES.add(new Piece(PieceType.ROOK, new RookMovementStrategy(), SIDE, 8, 9));
        PIECES.add(new Piece(PieceType.GUARD, new GuardMovementStrategy(), SIDE, 3, 9));
        PIECES.add(new Piece(PieceType.GUARD, new GuardMovementStrategy(), SIDE, 5, 9));
        PIECES.add(new Piece(PieceType.KING, new KingMovementStrategy(), SIDE, 4, 8));
    }

    @Test
    void 초나라_마상마상_배치를_생성한다() {
        List<Piece> generatedPieces = choPieceGenerator.generate(KnightElephantSetting.KNIGHT_ELEPHANT_KNIGHT_ELEPHANT);

        PIECES.addAll(List.of(
            new Piece(PieceType.KNIGHT, new KingMovementStrategy(), SIDE, 1, 9),
            new Piece(PieceType.ELEPHANT, new ElephantMovementStrategy(), SIDE, 2, 9),
            new Piece(PieceType.KNIGHT, new KingMovementStrategy(), SIDE, 6, 9),
            new Piece(PieceType.ELEPHANT, new ElephantMovementStrategy(), SIDE, 7, 9)
        ));

        assertThat(generatedPieces).containsExactlyInAnyOrderElementsOf(PIECES);
    }

    @Test
    void 초나라_마상상마_배치를_생성한다() {
        List<Piece> generatedPieces = choPieceGenerator.generate(KnightElephantSetting.KNIGHT_ELEPHANT_ELEPHANT_KNIGHT);

        PIECES.addAll(List.of(
            new Piece(PieceType.KNIGHT, new KingMovementStrategy(), SIDE, 1, 9),
            new Piece(PieceType.ELEPHANT, new ElephantMovementStrategy(), SIDE, 2, 9),
            new Piece(PieceType.ELEPHANT, new ElephantMovementStrategy(), SIDE, 6, 9),
            new Piece(PieceType.KNIGHT, new KingMovementStrategy(), SIDE, 7, 9)
        ));

        assertThat(generatedPieces).containsExactlyInAnyOrderElementsOf(PIECES);
    }

    @Test
    void 초나라_상마마상_배치를_생성한다() {
        List<Piece> generatedPieces = choPieceGenerator.generate(KnightElephantSetting.ELEPHANT_KNIGHT_KNIGHT_ELEPHANT);

        PIECES.addAll(List.of(
            new Piece(PieceType.ELEPHANT, new ElephantMovementStrategy(), SIDE, 1, 9),
            new Piece(PieceType.KNIGHT, new KingMovementStrategy(), SIDE, 2, 9),
            new Piece(PieceType.KNIGHT, new KingMovementStrategy(), SIDE, 6, 9),
            new Piece(PieceType.ELEPHANT, new ElephantMovementStrategy(), SIDE, 7, 9)
        ));

        assertThat(generatedPieces).containsExactlyInAnyOrderElementsOf(PIECES);
    }

    @Test
    void 초나라_상마상마_배치를_생성한다() {
        List<Piece> generatedPieces = choPieceGenerator.generate(KnightElephantSetting.ELEPHANT_KNIGHT_ELEPHANT_KNIGHT);

        PIECES.addAll(List.of(
            new Piece(PieceType.ELEPHANT, new ElephantMovementStrategy(), SIDE, 1, 9),
            new Piece(PieceType.KNIGHT, new KingMovementStrategy(), SIDE, 2, 9),
            new Piece(PieceType.ELEPHANT, new ElephantMovementStrategy(), SIDE, 6, 9),
            new Piece(PieceType.KNIGHT, new KingMovementStrategy(), SIDE, 7, 9)
        ));

        assertThat(generatedPieces).containsExactlyInAnyOrderElementsOf(PIECES);
    }
}
