package domain.board.wing;

import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class WingPiecesTest {

    private static final Piece DEFAULT_PIECE = Piece.of(PieceType.SOLDIER, Side.CHO);

    @DisplayName("기물 전체 개수 검증")
    @Nested
    class 기물_전체_개수_검증 {

        @ParameterizedTest
        @MethodSource("lessPieces")
        void 기물_개수가_2개_미만이면_예외를_던진다(List<Piece> lessPieces) {
            assertThatThrownBy(() -> WingPieces.of(lessPieces))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @MethodSource("morePieces")
        void 기물_개수가_2개_초과면_예외를_던진다(List<Piece> morePieces) {
            assertThatThrownBy(() -> WingPieces.of(morePieces))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        private static Stream<Arguments> lessPieces() {
            return Stream.of(
                    Arguments.of(EMPTY_LIST),
                    Arguments.of(List.of(
                            Piece.of(PieceType.HORSE, Side.CHO)
                    ))
            );
        }

        private static Stream<Arguments> morePieces() {
            return Stream.of(
                    Arguments.of(List.of(
                            Piece.of(PieceType.HORSE, Side.CHO),
                            Piece.of(PieceType.ELEPHANT, Side.CHO),
                            Piece.of(PieceType.HORSE, Side.CHO)
                    )),
                    Arguments.of(List.of(
                            Piece.of(PieceType.HORSE, Side.CHO),
                            Piece.of(PieceType.ELEPHANT, Side.CHO),
                            Piece.of(PieceType.HORSE, Side.CHO),
                            Piece.of(PieceType.ELEPHANT, Side.CHO)
                    )),
                    Arguments.of(List.of(
                            Piece.of(PieceType.HORSE, Side.CHO),
                            Piece.of(PieceType.ELEPHANT, Side.CHO),
                            Piece.of(PieceType.HORSE, Side.CHO),
                            Piece.of(PieceType.ELEPHANT, Side.CHO),
                            DEFAULT_PIECE
                    ))
            );
        }
    }

    @DisplayName("상, 마 개수 검증")
    @Nested
    class 상_마_개수_검증 {

        @DisplayName("상의 개수가 1개가 아니면 예외를 던진다")
        @Nested
        class 상의_개수가_1개가_아니면_예외를_던진다 {

            @DisplayName("상의 개수가 0개")
            @Test
            void 상의_개수가_0개() {
                assertThatThrownBy(() -> new WingPieces(
                        Piece.of(PieceType.HORSE, Side.CHO),
                        Piece.of(PieceType.HORSE, Side.CHO)
                )).isInstanceOf(IllegalArgumentException.class);
            }

            @DisplayName("상의 개수가 2개")
            @Test
            void 상의_개수가_2개() {
                assertThatThrownBy(() -> new WingPieces(
                        Piece.of(PieceType.ELEPHANT, Side.CHO),
                        Piece.of(PieceType.ELEPHANT, Side.CHO)
                )).isInstanceOf(IllegalArgumentException.class);
            }
        }

        @DisplayName("마의 개수가 1개가 아니면 예외를 던진다")
        @Nested
        class 마의_개수가_1개가_아니면_예외를_던진다 {

            @DisplayName("마의 개수가 0개")
            @Test
            void 마의_개수가_0개() {
                assertThatThrownBy(() -> new WingPieces(
                        Piece.of(PieceType.ELEPHANT, Side.CHO),
                        Piece.of(PieceType.ELEPHANT, Side.CHO)
                )).isInstanceOf(IllegalArgumentException.class);
            }

            @DisplayName("마의 개수가 2개")
            @Test
            void 마의_개수가_2개() {
                assertThatThrownBy(() -> new WingPieces(
                        Piece.of(PieceType.HORSE, Side.CHO),
                        Piece.of(PieceType.HORSE, Side.CHO)
                )).isInstanceOf(IllegalArgumentException.class);
            }
        }
    }

    @DisplayName("기물의 진영 검증")
    @Nested
    class 기물의_진영_검증 {

        @DisplayName("진영이 동일하지 않으면 예외를 던진다")
        @Test
        void 진영이_동일하지_않으면_예외를_던진다() {
            assertThatThrownBy(() -> new WingPieces(
                    Piece.of(PieceType.HORSE, Side.HAN),
                    Piece.of(PieceType.ELEPHANT, Side.CHO)
            )).isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("진영이 동일하면 정상적으로 생성된다")
        @Test
        void 진영이_동일하면_정상적으로_생성된다() {
            assertThatNoException().isThrownBy(() -> new WingPieces(
                    Piece.of(PieceType.HORSE, Side.HAN),
                    Piece.of(PieceType.ELEPHANT, Side.HAN)
            ));
        }
    }
}