package janggi.domain.piece.gererator;

import janggi.domain.ReplaceUnderBar;
import janggi.domain.Side;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Guard;
import janggi.domain.piece.King;
import janggi.domain.piece.Knight;
import janggi.domain.piece.Pawn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ReplaceUnderBar
public class ChoPieceGeneratorTest {

    private static final Side SIDE = Side.CHO;
    private static List<Piece> PIECES;
    private final ChoPieceGenerator choPieceGenerator = new ChoPieceGenerator();

    @BeforeEach
    void setUpDefaultPieces() {
        PIECES = new ArrayList<>();
        PIECES.add(new Pawn(SIDE, 0, 6));
        PIECES.add(new Pawn(SIDE, 2, 6));
        PIECES.add(new Pawn(SIDE, 4, 6));
        PIECES.add(new Pawn(SIDE, 6, 6));
        PIECES.add(new Pawn(SIDE, 8, 6));
        PIECES.add(new Cannon(SIDE, 1, 7));
        PIECES.add(new Cannon(SIDE, 7, 7));
        PIECES.add(new Rook(SIDE, 0, 9));
        PIECES.add(new Rook(SIDE, 8, 9));
        PIECES.add(new Guard(SIDE, 3, 9));
        PIECES.add(new Guard(SIDE, 5, 9));
        PIECES.add(new King(SIDE, 4, 8));
    }

    @Test
    void 초나라_마상마상_배치를_생성한다() {
        List<Piece> generatedPieces = choPieceGenerator.generate(KnightElephantSetting.KNIGHT_ELEPHANT_KNIGHT_ELEPHANT);

        PIECES.addAll(List.of(
            new Knight(SIDE, 1, 9),
            new Elephant(SIDE, 2, 9),
            new Knight(SIDE, 6, 9),
            new Elephant(SIDE, 7, 9)
        ));

        assertThat(generatedPieces).containsExactlyInAnyOrderElementsOf(PIECES);
    }

    @Test
    void 초나라_마상상마_배치를_생성한다() {
        List<Piece> generatedPieces = choPieceGenerator.generate(KnightElephantSetting.KNIGHT_ELEPHANT_ELEPHANT_KNIGHT);

        PIECES.addAll(List.of(
            new Knight(SIDE, 1, 9),
            new Elephant(SIDE, 2, 9),
            new Elephant(SIDE, 6, 9),
            new Knight(SIDE, 7, 9)
        ));

        assertThat(generatedPieces).containsExactlyInAnyOrderElementsOf(PIECES);
    }

    @Test
    void 초나라_상마마상_배치를_생성한다() {
        List<Piece> generatedPieces = choPieceGenerator.generate(KnightElephantSetting.ELEPHANT_KNIGHT_KNIGHT_ELEPHANT);

        PIECES.addAll(List.of(
            new Elephant(SIDE, 1, 9),
            new Knight(SIDE, 2, 9),
            new Knight(SIDE, 6, 9),
            new Elephant(SIDE, 7, 9)
        ));

        assertThat(generatedPieces).containsExactlyInAnyOrderElementsOf(PIECES);
    }

    @Test
    void 초나라_상마상마_배치를_생성한다() {
        List<Piece> generatedPieces = choPieceGenerator.generate(KnightElephantSetting.ELEPHANT_KNIGHT_ELEPHANT_KNIGHT);

        PIECES.addAll(List.of(
            new Elephant(SIDE, 1, 9),
            new Knight(SIDE, 2, 9),
            new Elephant(SIDE, 6, 9),
            new Knight(SIDE, 7, 9)
        ));

        assertThat(generatedPieces).containsExactlyInAnyOrderElementsOf(PIECES);
    }
}
