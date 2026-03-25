package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SoldierTest {

    @Nested
    class 초기_위치를_반환한다 {

        @Test
        void 한_소속_병이_적절한_초기_위치를_반환한다() {
            // given
            Soldier soldier = new Soldier(Side.HAN);
            List<Intersection> expected = List.of(
                    new Intersection(4, 1),
                    new Intersection(4, 3),
                    new Intersection(4, 5),
                    new Intersection(4, 7),
                    new Intersection(4, 9)
            );

            // when
            List<Intersection> actual = soldier.initAt();

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 초_소속_졸이_적절한_초기_위치를_반환한다() {
            // given
            Soldier soldier = new Soldier(Side.CHO);
            List<Intersection> expected = List.of(
                    new Intersection(7, 1),
                    new Intersection(7, 3),
                    new Intersection(7, 5),
                    new Intersection(7, 7),
                    new Intersection(7, 9)
            );

            // when
            List<Intersection> actual = soldier.initAt();

            // then
            assertThat(actual).isEqualTo(expected);
        }
    }
}
