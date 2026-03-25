package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ChariotTest {

    @Nested
    class 초기_위치를_반환한다 {

        @Test
        void 한_소속_차가_적절한_초기_위치를_반환한다() {
            // given
            Chariot chariot = new Chariot(Side.HAN);
            List<Intersection> expected = List.of(
                    new Intersection(1, 1),
                    new Intersection(1, 9)
            );

            // when
            List<Intersection> actual = chariot.initAt();

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 초_소속_차가_적절한_초기_위치를_반환한다() {
            // given
            Chariot chariot = new Chariot(Side.CHO);
            List<Intersection> expected = List.of(
                    new Intersection(10, 1),
                    new Intersection(10, 9)
            );

            // when
            List<Intersection> actual = chariot.initAt();

            // then
            assertThat(actual).isEqualTo(expected);
        }
    }
}
