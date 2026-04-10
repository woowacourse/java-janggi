package domain.board;

import static domain.util.AssertUtils.assertThatNoException;
import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.game.Side;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Soldier;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class WingTest {

    private static final Soldier DEFAULT_PIECE = new Soldier(Side.CHO);

    private final Piece wingPiece1 = new Elephant(Side.CHO);
    private final Piece wingPiece2 = new Horse(Side.CHO);
    private final Piece notWingPiece = new Soldier(Side.CHO);

    @Nested
    class 기물_개수를_검증한다 {

        private static final String ILLEGAL_PIECE_AMOUNT_MESSAGE = "진의 기물 수는 2개여야 합니다";

        @ParameterizedTest
        @MethodSource("lessPieces")
        void 기물_개수가_2개_미만이면_예외를_던진다(List<Piece> lessPieces) {
            assertThatThrownBy(() -> new LeftWing(lessPieces))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ILLEGAL_PIECE_AMOUNT_MESSAGE);
        }

        @ParameterizedTest
        @MethodSource("morePieces")
        void 기물_개수가_2개_초과면_예외를_던진다(List<Piece> morePieces) {
            assertThatThrownBy(() -> new LeftWing(morePieces))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ILLEGAL_PIECE_AMOUNT_MESSAGE);
        }

        private static Stream<Arguments> lessPieces() {
            return Stream.of(
                    Arguments.of(EMPTY_LIST),
                    Arguments.of(List.of(
                            new Horse(Side.CHO)
                    ))
            );
        }

        private static Stream<Arguments> morePieces() {
            return Stream.of(
                    Arguments.of(List.of(
                            new Horse(Side.CHO),
                            new Elephant(Side.CHO),
                            new Horse(Side.CHO)
                    )),
                    Arguments.of(List.of(
                            new Horse(Side.CHO),
                            new Elephant(Side.CHO),
                            new Horse(Side.CHO),
                            new Elephant(Side.CHO)
                    )),
                    Arguments.of(List.of(
                            new Horse(Side.CHO),
                            new Elephant(Side.CHO),
                            new Horse(Side.CHO),
                            new Elephant(Side.CHO),
                            DEFAULT_PIECE
                    ))
            );
        }
    }

    @Test
    void 진에_속할_수_있는_기물이_아니면_예외를_던진다() {
        // given
        List<Piece> illegalPieces = List.of(notWingPiece, wingPiece1);

        // when and then
        assertThatThrownBy(() -> new LeftWing(illegalPieces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("진에 소속될 수 없는 기물이 포함되어 있습니다");
    }

    @Test
    void 기물이_중복되면_예외를_던진다() {
        // given
        List<Piece> illegalPieces = List.of(wingPiece1, wingPiece1);

        // when and then
        assertThatThrownBy(() -> new LeftWing(illegalPieces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("하나의 진에는 중복되지 않은 기물들만 포함될 수 있습니다");
    }

    @Test
    void 진에_속할_수_있는_기물이_1개씩_총_2개_있다면_정상적으로_생성된다() {
        List<Piece> pieces = List.of(wingPiece1, wingPiece2);

        assertThatNoException(() -> new LeftWing(pieces));
    }
}
