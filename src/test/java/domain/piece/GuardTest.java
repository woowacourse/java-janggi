package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GuardTest {

    @Nested
    class 초기_위치를_반환한다 {

        @Test
        void 한_소속_사가_적절한_초기_위치를_반환한다() {
            // given
            Guard guard = new Guard(Side.HAN);
            List<Intersection> expected = List.of(
                    new Intersection(1, 4),
                    new Intersection(1, 6)
            );

            // when
            List<Intersection> actual = guard.initAt();

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 초_소속_사가_적절한_초기_위치를_반환한다() {
            // given
            Guard guard = new Guard(Side.CHO);
            List<Intersection> expected = List.of(
                    new Intersection(10, 4),
                    new Intersection(10, 6)
            );

            // when
            List<Intersection> actual = guard.initAt();

            // then
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Test
    void 본인의_점수를_반환한다() {
        // given
        Guard guard = new Guard(Side.HAN);
        double expected = 3;

        // when
        double actual = guard.getScore();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
