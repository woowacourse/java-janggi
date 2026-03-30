package domain.board.wing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Intersection;
import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class WingsTest {

    @DisplayName("초 진영의 진 검증")
    @Nested
    class 초_진영의_진_검증 {

        private static final Side side = Side.CHO;

        @Nested
        class 생성할_때_기물의_진영을_검증한다 {

            private final List<Piece> illegalSideWing = List.of(
                    new Piece(PieceType.HORSE, Side.CHO),
                    new Piece(PieceType.ELEPHANT, Side.HAN)
            );
            private final List<Piece> legalSideWing = List.of(
                    new Piece(PieceType.HORSE, Side.CHO),
                    new Piece(PieceType.ELEPHANT, Side.CHO)
            );

            @Test
            void 좌진에_초가_아닌_기물이_있다면_예외를_던진다() {
                assertThatThrownBy(() -> new Wings(side, illegalSideWing, legalSideWing))
                        .isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            void 우진에_초가_아닌_기물이_있다면_예외를_던진다() {
                assertThatThrownBy(() -> new Wings(side, legalSideWing, illegalSideWing))
                        .isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            void 좌진과_우진에_초_기물만_있다면_정상적으로_생성된다() {
                assertThatNoException().isThrownBy(() -> new Wings(side, legalSideWing, legalSideWing));
            }
        }

        @Test
        void 초의_좌진과_우진_기물의_초기_위치를_반환한다() {
            // given
            Piece first = new Piece(PieceType.HORSE, Side.CHO);
            Piece second = new Piece(PieceType.ELEPHANT, Side.CHO);
            Piece third = new Piece(PieceType.HORSE, Side.CHO);
            Piece fourth = new Piece(PieceType.ELEPHANT, Side.CHO);

            Wings choWings = new Wings(
                    side,
                    List.of(first, second),
                    List.of(third, fourth)
            );

            // when
            Map<Intersection, Piece> setUpPieces = choWings.setUpPieces();

            // then
            assertThat(setUpPieces.get(new Intersection(10, 2))).isEqualTo(first);
            assertThat(setUpPieces.get(new Intersection(10, 3))).isEqualTo(second);
            assertThat(setUpPieces.get(new Intersection(10, 7))).isEqualTo(third);
            assertThat(setUpPieces.get(new Intersection(10, 8))).isEqualTo(fourth);
        }
    }

    @DisplayName("한 진영의 진 검증")
    @Nested
    class 한_진영의_진_검증 {

        private static final Side side = Side.HAN;

        @Nested
        class 생성할_때_기물의_진영을_검증한다 {

            private final List<Piece> illegalSideWing = List.of(
                    new Piece(PieceType.HORSE, Side.HAN),
                    new Piece(PieceType.ELEPHANT, Side.CHO)
            );
            private final List<Piece> legalSideWing = List.of(
                    new Piece(PieceType.HORSE, Side.HAN),
                    new Piece(PieceType.ELEPHANT, Side.HAN)
            );

            @Test
            void 좌진에_한이_아닌_기물이_있다면_예외를_던진다() {
                assertThatThrownBy(() -> new Wings(side, illegalSideWing, legalSideWing))
                        .isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            void 우진에_한이_아닌_기물이_있다면_예외를_던진다() {
                assertThatThrownBy(() -> new Wings(side, legalSideWing, illegalSideWing))
                        .isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            void 좌진과_우진에_한_기물만_있다면_정상적으로_생성된다() {
                assertThatNoException().isThrownBy(() -> new Wings(side, legalSideWing, legalSideWing));
            }
        }

        @Test
        void 한의_좌진과_우진_기물의_초기_위치를_반환한다() {
            // given
            Piece first = new Piece(PieceType.HORSE, Side.HAN);
            Piece second = new Piece(PieceType.ELEPHANT, Side.HAN);
            Piece third = new Piece(PieceType.HORSE, Side.HAN);
            Piece fourth = new Piece(PieceType.ELEPHANT, Side.HAN);

            Wings hanWings = new Wings(
                    side,
                    List.of(first, second),
                    List.of(third, fourth)
            );

            // when
            Map<Intersection, Piece> setUpPieces = hanWings.setUpPieces();

            // then
            assertThat(setUpPieces.get(new Intersection(1, 8))).isEqualTo(first);
            assertThat(setUpPieces.get(new Intersection(1, 7))).isEqualTo(second);
            assertThat(setUpPieces.get(new Intersection(1, 3))).isEqualTo(third);
            assertThat(setUpPieces.get(new Intersection(1, 2))).isEqualTo(fourth);
        }
    }
}
