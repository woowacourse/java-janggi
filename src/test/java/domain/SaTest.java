package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SaTest {

    @DisplayName("CHO 팀 사의 움직임 테스트")
    @Nested
    class ChoTeam {

        @Test
        @DisplayName("CHO 팀의 사가 왼쪽으로 움직이면 졸의 x 좌표 1 감소한다.")
        void test2() {
            // given
            Sa sa = new Sa(new Coordinate(2, 1), Team.CHO);

            // when
            Sa movedSa = sa.moveLeft();

            // then
            assertThat(movedSa).isEqualTo(new Sa(new Coordinate(1, 1), Team.CHO));
        }

        @Test
        @DisplayName("CHO 팀의 사가 오른쪽으로 움직이면 졸의 x 좌표가 1 증가한다.")
        void test4() {
            // given
            Sa sa = new Sa(new Coordinate(2, 1), Team.CHO);

            // when
            Sa movedSa = sa.moveRight();

            // then
            assertThat(movedSa).isEqualTo(new Sa(new Coordinate(3, 1), Team.CHO));
        }

        @Test
        @DisplayName("CHO 팀의 사가 앞쪽으로 움직이면 졸의 y 좌표가 1 감소한다.")
        void test6() {
            // given
            Sa sa = new Sa(new Coordinate(2, 2), Team.CHO);

            // when
            Sa movedSa = sa.moveForward();

            // then
            assertThat(movedSa).isEqualTo(new Sa(new Coordinate(2, 1), Team.CHO));
        }

        @Test
        @DisplayName("CHO 팀의 사가 뒤쪽으로 움직이면 졸의 y 좌표가 1 증가한다.")
        void test8() {
            // given
            Sa sa = new Sa(new Coordinate(2, 2), Team.CHO);

            // when
            Sa movedSa = sa.moveBackward();

            // then
            assertThat(movedSa).isEqualTo(new Sa(new Coordinate(2, 3), Team.CHO));
        }
    }

    @DisplayName("HAN 팀 사의 움직임 테스트 ")
    @Nested
    class HanTeam {

        @Test
        @DisplayName("HAN 팀의 사가 왼쪽으로 움직이면 사의 x 좌표 1 증가한다.")
        void test1() {
            // given
            Sa sa = new Sa(new Coordinate(2, 1), Team.HAN);

            // when
            Sa movedSa = sa.moveLeft();

            // then
            assertThat(movedSa).isEqualTo(new Sa(new Coordinate(3, 1), Team.HAN));
        }

        @Test
        @DisplayName("HAN 팀의 사가 오른쪽으로 움직이면 사의 x 좌표가 1 감소한다.")
        void test3() {
            // given
            Sa sa = new Sa(new Coordinate(2, 1), Team.HAN);

            // when
            Sa movedSa = sa.moveRight();

            // then
            assertThat(movedSa).isEqualTo(new Sa(new Coordinate(1, 1), Team.HAN));
        }

        @Test
        @DisplayName("HAN 팀의 사가 앞쪽으로 움직이면 사의 y 좌표가 1 증가한다.")
        void test5() {
            // given
            Sa sa = new Sa(new Coordinate(2, 2), Team.HAN);

            // when
            Sa movedSa = sa.moveForward();

            // then
            assertThat(movedSa).isEqualTo(new Sa(new Coordinate(2, 3), Team.HAN));
        }

        @Test
        @DisplayName("HAN 팀의 사가 뒤쪽으로 움직이면 사의 y 좌표가 1 감소한다.")
        void test7() {
            // given
            Sa sa = new Sa(new Coordinate(2, 2), Team.HAN);

            // when
            Sa movedSa = sa.moveBackward();

            // then
            assertThat(movedSa).isEqualTo(new Sa(new Coordinate(2, 1), Team.HAN));
        }
    }
}
