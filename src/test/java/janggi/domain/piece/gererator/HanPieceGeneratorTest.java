package janggi.domain.piece.gererator;

import static org.assertj.core.api.Assertions.assertThat;

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
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@ReplaceUnderBar
class HanPieceGeneratorTest {

    private static final Side SIDE = Side.HAN;
    private static List<Piece> PIECES;
    private final HanPieceGenerator hanPieceGenerator = new HanPieceGenerator();

    @BeforeEach
    void setUpDefaultPieces() {
        PIECES = new ArrayList<>();
        PIECES.add(new Pawn(SIDE, 0, 3));
        PIECES.add(new Pawn(SIDE, 2, 3));
        PIECES.add(new Pawn(SIDE, 4, 3));
        PIECES.add(new Pawn(SIDE, 6, 3));
        PIECES.add(new Pawn(SIDE, 8, 3));
        PIECES.add(new Cannon(SIDE, 1, 2));
        PIECES.add(new Cannon(SIDE, 7, 2));
        PIECES.add(new Rook(SIDE, 0, 0));
        PIECES.add(new Rook(SIDE, 8, 0));
        PIECES.add(new Guard(SIDE, 3, 0));
        PIECES.add(new Guard(SIDE, 5, 0));
        PIECES.add(new King(SIDE, 4, 1));
    }

    @Test
    void 한나라_마상마상_배치를_생성한다() {
        List<Piece> generatedPieces = hanPieceGenerator.generate(KnightElephantSetting.KNIGHT_ELEPHANT_KNIGHT_ELEPHANT);

        PIECES.addAll(List.of(
                new Knight(SIDE, 1, 0),
                new Elephant(SIDE, 2, 0),
                new Knight(SIDE, 6, 0),
                new Elephant(SIDE, 7, 0)
        ));

        assertThat(generatedPieces).containsExactlyInAnyOrderElementsOf(PIECES);
    }

    @Test
    void 한나라_마상상마_배치를_생성한다() {
        List<Piece> generatedPieces = hanPieceGenerator.generate(KnightElephantSetting.KNIGHT_ELEPHANT_ELEPHANT_KNIGHT);

        PIECES.addAll(List.of(
                new Knight(SIDE, 1, 0),
                new Elephant(SIDE, 2, 0),
                new Elephant(SIDE, 6, 0),
                new Knight(SIDE, 7, 0)
        ));

        assertThat(generatedPieces).containsExactlyInAnyOrderElementsOf(PIECES);
    }

    @Test
    void 한나라_상마마상_배치를_생성한다() {
        List<Piece> generatedPieces = hanPieceGenerator.generate(KnightElephantSetting.ELEPHANT_KNIGHT_KNIGHT_ELEPHANT);

        PIECES.addAll(List.of(
                new Elephant(SIDE, 1, 0),
                new Knight(SIDE, 2, 0),
                new Knight(SIDE, 6, 0),
                new Elephant(SIDE, 7, 0)
        ));

        assertThat(generatedPieces).containsExactlyInAnyOrderElementsOf(PIECES);
    }

    @Test
    void 한나라_상마상마_배치를_생성한다() {
        List<Piece> generatedPieces = hanPieceGenerator.generate(KnightElephantSetting.ELEPHANT_KNIGHT_ELEPHANT_KNIGHT);

        PIECES.addAll(List.of(
                new Elephant(SIDE, 1, 0),
                new Knight(SIDE, 2, 0),
                new Elephant(SIDE, 6, 0),
                new Knight(SIDE, 7, 0)
        ));

        assertThat(generatedPieces).containsExactlyInAnyOrderElementsOf(PIECES);
    }
}
