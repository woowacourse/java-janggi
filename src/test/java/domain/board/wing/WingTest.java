package domain.board.wing;

import static domain.util.AssertUtils.assertThatNoException;
import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class WingTest {

    private static final Piece DEFAULT_PIECE = new Piece(PieceType.SOLDIER, Side.CHO);

    @Nested
    class 기물_개수가_2개가_아니면_예외를_던진다 {

        @ParameterizedTest
        @MethodSource("lessPieces")
        void 기물_개수가_2개_미만이면_예외를_던진다(List<Piece> lessPieces) {
            assertThatThrownBy(() -> new LeftWing(lessPieces))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @MethodSource("morePieces")
        void 기물_개수가_2개_초과면_예외를_던진다(List<Piece> morePieces) {
            assertThatThrownBy(() -> new LeftWing(morePieces))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        private static Stream<Arguments> lessPieces() {
            return Stream.of(
                    Arguments.of(EMPTY_LIST),
                    Arguments.of(List.of(
                            new Piece(PieceType.HORSE, Side.CHO)
                    ))
            );
        }

        private static Stream<Arguments> morePieces() {
            return Stream.of(
                    Arguments.of(List.of(
                            new Piece(PieceType.HORSE, Side.CHO),
                            new Piece(PieceType.ELEPHANT, Side.CHO),
                            new Piece(PieceType.HORSE, Side.CHO)
                    )),
                    Arguments.of(List.of(
                            new Piece(PieceType.HORSE, Side.CHO),
                            new Piece(PieceType.ELEPHANT, Side.CHO),
                            new Piece(PieceType.HORSE, Side.CHO),
                            new Piece(PieceType.ELEPHANT, Side.CHO)
                    )),
                    Arguments.of(List.of(
                            new Piece(PieceType.HORSE, Side.CHO),
                            new Piece(PieceType.ELEPHANT, Side.CHO),
                            new Piece(PieceType.HORSE, Side.CHO),
                            new Piece(PieceType.ELEPHANT, Side.CHO),
                            DEFAULT_PIECE
                    ))
            );
        }
    }

    @Nested
    class 상의_개수가_1개가_아니면_예외를_던진다 {

        @Test
        void 상의_개수가_1개_미만이면_예외를_던진다() {
            // given
            List<Piece> illegalPieces = List.of(
                    new Piece(PieceType.HORSE, Side.CHO),
                    new Piece(PieceType.HORSE, Side.CHO)
            );

            // when and then
            assertThatThrownBy(() -> new LeftWing(illegalPieces))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 상의_개수가_1개_초과면_예외를_던진다() {
            // given
            List<Piece> illegalPieces = List.of(
                    new Piece(PieceType.ELEPHANT, Side.CHO),
                    new Piece(PieceType.ELEPHANT, Side.CHO)
            );

            // when and then
            assertThatThrownBy(() -> new LeftWing(illegalPieces))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class 마의_개수가_1개가_아니면_예외를_던진다 {

        @Test
        void 마의_개수가_1개_미만이면_예외를_던진다() {
            // given
            List<Piece> illegalPieces = List.of(
                    new Piece(PieceType.ELEPHANT, Side.CHO),
                    DEFAULT_PIECE);

            // when and then
            assertThatThrownBy(() -> new LeftWing(illegalPieces))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 마의_개수가_1개_초과면_예외를_던진다() {
            // given
            List<Piece> illegalPieces = List.of(
                    new Piece(PieceType.HORSE, Side.CHO),
                    new Piece(PieceType.HORSE, Side.CHO)
            );

            // when and then
            assertThatThrownBy(() -> new LeftWing(illegalPieces))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void 상과_마가_1개씩_있다면_정상적으로_생성된다() {
        List<Piece> pieces = List.of(
                new Piece(PieceType.HORSE, Side.CHO),
                new Piece(PieceType.ELEPHANT, Side.CHO)
        );

        assertThatNoException(() -> new LeftWing(pieces));
    }
}
