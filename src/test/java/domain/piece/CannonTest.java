package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CannonTest {

    @Nested
    class 초기_위치를_반환한다 {

        @Test
        void 한_소속_포가_적절한_초기_위치를_반환한다() {
            // given
            Cannon cannon = new Cannon(Side.HAN);
            List<Intersection> expected = List.of(
                    new Intersection(3, 2),
                    new Intersection(3, 8)
            );

            // when
            List<Intersection> actual = cannon.initAt();

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 초_소속_포가_적절한_초기_위치를_반환한다() {
            // given
            Cannon cannon = new Cannon(Side.CHO);
            List<Intersection> expected = List.of(
                    new Intersection(8, 2),
                    new Intersection(8, 8)
            );

            // when
            List<Intersection> actual = cannon.initAt();

            // then
            assertThat(actual).isEqualTo(expected);
        }
    }
}
