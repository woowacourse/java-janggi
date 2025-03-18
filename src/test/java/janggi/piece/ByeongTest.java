package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.TeamColor;
import janggi.point.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ByeongTest {

    @Nested
    @DisplayName("이동 가능 확인 테스트")
    class CheckMovableTest {

        @Test
        @DisplayName("좌로 이동할 수 있다면 true를 반환한다.")
        void checkLeftMovable() {
            Byeong byeong = new Byeong(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(6, 5);

            assertThat(byeong.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("우로 이동할 수 있다면 true를 반환한다.")
        void checkRightMovable() {
            Byeong byeong = new Byeong(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(6, 7);

            assertThat(byeong.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("상으로 이동할 수 있다면 true를 반환한다.")
        void checkUpMovable() {
            Byeong byeong = new Byeong(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(5, 6);

            assertThat(byeong.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("하으로 이동할 수 있다면 false를 반환한다.")
        void checkDownMovable() {
            Byeong byeong = new Byeong(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(7, 6);

            assertThat(byeong.isMovable(targetPoint)).isFalse();
        }
    }

}