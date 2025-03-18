package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class JolTest {

    @DisplayName("CHO 팀 졸의 움직임 테스트")
    @Nested
    class ChoTeam {

        @Test
        @DisplayName("CHO 팀의 졸이 왼쪽으로 움직이면 졸의 x 좌표 1 감소한다.")
        void test2() {
            // given
            Jol jol = new Jol(new Coordinate(2, 1), Team.CHO);

            // when
            Jol movedJol = jol.moveLeft();

            // then
            assertThat(movedJol).isEqualTo(new Jol(new Coordinate(1, 1), Team.CHO));
        }

        @Test
        @DisplayName("CHO 팀의 졸이 오른쪽으로 움직이면 졸의 x 좌표가 1 증가한다.")
        void test4() {
            // given
            Jol jol = new Jol(new Coordinate(2, 1), Team.CHO);

            // when
            Jol movedJol = jol.moveRight();

            // then
            assertThat(movedJol).isEqualTo(new Jol(new Coordinate(3, 1), Team.CHO));
        }

        @Test
        @DisplayName("CHO 팀의 졸이 앞쪽으로 움직이면 졸의 y 좌표가 1 감소한다.")
        void test6() {
            // given
            Jol jol = new Jol(new Coordinate(2, 2), Team.CHO);

            // when
            Jol movedJol = jol.moveForward();

            // then
            assertThat(movedJol).isEqualTo(new Jol(new Coordinate(2, 1), Team.CHO));
        }
    }

    @DisplayName("HAN 팀 졸의 움직임 테스트 ")
    @Nested
    class HanTeam {

        @Test
        @DisplayName("HAN 팀의 졸이 왼쪽으로 움직이면 졸의 x 좌표 1 증가한다.")
        void test1() {
            // given
            Jol jol = new Jol(new Coordinate(2, 1), Team.HAN);

            // when
            Jol movedJol = jol.moveLeft();

            // then
            assertThat(movedJol).isEqualTo(new Jol(new Coordinate(3, 1), Team.HAN));
        }

        @Test
        @DisplayName("HAN 팀의 졸이 오른쪽으로 움직이면 졸의 x 좌표가 1 감소한다.")
        void test3() {
            // given
            Jol jol = new Jol(new Coordinate(2, 1), Team.HAN);

            // when
            Jol movedJol = jol.moveRight();

            // then
            assertThat(movedJol).isEqualTo(new Jol(new Coordinate(1, 1), Team.HAN));
        }

        @Test
        @DisplayName("HAN 팀의 졸이 앞쪽으로 움직이면 졸의 y 좌표가 1 증가한다.")
        void test5() {
            // given
            Jol jol = new Jol(new Coordinate(2, 2), Team.HAN);

            // when
            Jol movedJol = jol.moveForward();

            // then
            assertThat(movedJol).isEqualTo(new Jol(new Coordinate(2, 3), Team.HAN));
        }
    }
}
