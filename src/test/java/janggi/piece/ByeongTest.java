package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.Team;
import janggi.point.Point;
import java.util.List;
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
            Byeong byeong = new Byeong(Team.CHO, new Point(6, 6));

            Point targetPoint = new Point(6, 5);

            assertThat(byeong.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("우로 이동할 수 있다면 true를 반환한다.")
        void checkRightMovable() {
            Byeong byeong = new Byeong(Team.CHO, new Point(6, 6));

            Point targetPoint = new Point(6, 7);

            assertThat(byeong.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("초나라의 경우 북쪽으로 이동할 수 있다면 true를 반환한다.")
        void checkUpMovableWithCho() {
            Byeong byeong = new Byeong(Team.CHO, new Point(6, 6));

            Point targetPoint = new Point(5, 6);

            assertThat(byeong.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("초나라의 경우 남쪽으로 이동할 수 있다면 false를 반환한다.")
        void checkDownMovableWithCho() {
            Byeong byeong = new Byeong(Team.CHO, new Point(6, 6));

            Point targetPoint = new Point(7, 6);

            assertThat(byeong.isMovable(targetPoint)).isFalse();
        }

        @Test
        @DisplayName("한나라의 경우 북쪽으로 이동할 수 있다면 false를 반환한다.")
        void checkUpMovableWithHan() {
            Byeong byeong = new Byeong(Team.HAN, new Point(6, 6));

            Point targetPoint = new Point(5, 6);

            assertThat(byeong.isMovable(targetPoint)).isFalse();
        }

        @Test
        @DisplayName("한나라의 경우 남쪽으로 이동할 수 있다면 true를 반환한다.")
        void checkDownMovableWithHan() {
            Byeong byeong = new Byeong(Team.HAN, new Point(6, 6));

            Point targetPoint = new Point(7, 6);

            assertThat(byeong.isMovable(targetPoint)).isTrue();
        }
    }

    @Nested
    @DisplayName("경로 테스트")
    class RouteTest {

        @Test
        @DisplayName("좌로 이동 경로를 생성할 수 있다.")
        void checkLeftRouteMovable() {
            Byeong byeong = new Byeong(Team.CHO, new Point(6, 6));

            Point targetPoint = new Point(6, 5);

            List<Point> route = byeong.findRoute(targetPoint);

            assertAll(() -> {
                assertThat(route).hasSize(1);
                assertThat(route).containsExactly(new Point(6,5));
            });
        }

        @Test
        @DisplayName("우로 이동 경로를 생성할 수 있다.")
        void checkRightRouteMovable() {
            Byeong byeong = new Byeong(Team.CHO, new Point(6, 6));

            Point targetPoint = new Point(6, 7);

            List<Point> route = byeong.findRoute(targetPoint);

            assertAll(() -> {
                assertThat(route).hasSize(1);
                assertThat(route).containsExactly(new Point(6,7));
            });
        }

        @Test
        @DisplayName("상로 이동 경로를 생성할 수 있다.")
        void checkUpRouteMovable() {
            Byeong byeong = new Byeong(Team.CHO, new Point(6, 6));

            Point targetPoint = new Point(5, 6);

            List<Point> route = byeong.findRoute(targetPoint);

            assertAll(() -> {
                assertThat(route).hasSize(1);
                assertThat(route).containsExactly(new Point(5,6));
            });
        }
    }

}