package domain.board;

import static domain.util.AssertUtils.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.game.Side;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ChoWingsTest {

    @Nested
    class 생성할_때_기물의_진영을_검증한다 {

        private final List<Piece> illegalSideWing = List.of(new Horse(Side.CHO), new Elephant(Side.HAN));
        private final List<Piece> legalSideWing = List.of(new Horse(Side.CHO), new Elephant(Side.CHO));

        @Test
        void 좌진에_초가_아닌_기물이_있다면_예외를_던진다() {
            assertThatThrownBy(() -> new ChoWings(illegalSideWing, legalSideWing))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 우진에_초가_아닌_기물이_있다면_예외를_던진다() {
            assertThatThrownBy(() -> new ChoWings(legalSideWing, illegalSideWing))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 좌진과_우진에_초_기물만_있다면_정상적으로_생성된다() {
            assertThatNoException(() -> new ChoWings(legalSideWing, legalSideWing));
        }
    }

    @Test
    void 초의_좌진과_우진_기물의_초기_위치를_반환한다() {
        // given
        Piece first = new Horse(Side.CHO);
        Piece second = new Elephant(Side.CHO);
        Piece third = new Horse(Side.CHO);
        Piece fourth = new Elephant(Side.CHO);

        ChoWings choWings = new ChoWings(
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
