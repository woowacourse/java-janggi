package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.game.Team;
import janggi.point.Point;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MaTest {

    @Nested
    @DisplayName("이동 가능 확인 테스트")
    class CheckMovableTest {

        @Test
        @DisplayName("상-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkUpRightMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));

            Point targetPoint = new Point(3, 5);

            assertThat(ma.isInMovingRange(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("우-좌측대각선로 이동할 수 있다면 true를 반환한다.")
        void checkRightLeftMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));

            Point targetPoint = new Point(4, 6);

            assertThat(ma.isInMovingRange(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("우-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkRightRightMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));

            Point targetPoint = new Point(6, 6);

            assertThat(ma.isInMovingRange(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("하-좌측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkDownLeftMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));

            Point targetPoint = new Point(7, 5);

            assertThat(ma.isInMovingRange(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("하-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkDownRightMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));

            Point targetPoint = new Point(7, 3);

            assertThat(ma.isInMovingRange(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("좌-좌측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkLeftLeftMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));

            Point targetPoint = new Point(6, 2);

            assertThat(ma.isInMovingRange(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("좌-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkLeftRightMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));

            Point targetPoint = new Point(4, 2);

            assertThat(ma.isInMovingRange(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("상-좌측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkUpLeftMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));

            Point targetPoint = new Point(3, 3);

            assertThat(ma.isInMovingRange(targetPoint)).isTrue();
        }
    }

    @Nested
    @DisplayName("경로 테스트")
    class RouteTest {

        @Test
        @DisplayName("상-우측대각선로 이동 경로를 생성할 수 있다.")
        void checkUpRightRouteMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));

            Point targetPoint = new Point(3, 5);

            List<Point> route = ma.findRoute(targetPoint);

            assertAll(() -> {
                assertThat(route).hasSize(2);
                assertThat(route).containsExactly(new Point(4, 4), new Point(3, 5));
            });
        }

        @Test
        @DisplayName("우-좌측대각선로 이동 경로를 생성할 수 있다.")
        void checkRightLeftRouteMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));

            Point targetPoint = new Point(4, 6);

            List<Point> route = ma.findRoute(targetPoint);

            assertAll(() -> {
                assertThat(route).hasSize(2);
                assertThat(route).containsExactly(new Point(5, 5), new Point(4, 6));
            });
        }

        @Test
        @DisplayName("우-우측대각선로 이동 경로를 생성할 수 있다.")
        void checkRightRightRouteMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));

            Point targetPoint = new Point(6, 6);

            List<Point> route = ma.findRoute(targetPoint);

            assertAll(() -> {
                assertThat(route).hasSize(2);
                assertThat(route).containsExactly(new Point(5, 5), new Point(6, 6));
            });
        }

        @Test
        @DisplayName("하-좌측대각선로 이동 경로를 생성할 수 있다.")
        void checkDownLeftRouteMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));

            Point targetPoint = new Point(7, 5);

            List<Point> route = ma.findRoute(targetPoint);

            assertAll(() -> {
                assertThat(route).hasSize(2);
                assertThat(route).containsExactly(new Point(6, 4), new Point(7, 5));
            });
        }

        @Test
        @DisplayName("하-우측대각선로 이동 경로를 생성할 수 있다.")
        void checkDownRightRouteMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));

            Point targetPoint = new Point(7, 3);

            List<Point> route = ma.findRoute(targetPoint);

            assertAll(() -> {
                assertThat(route).hasSize(2);
                assertThat(route).containsExactly(new Point(6, 4), new Point(7, 3));
            });
        }

        @Test
        @DisplayName("좌-좌측대각선로 이동 경로를 생성할 수 있다.")
        void checkLeftLeftRouteMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));

            Point targetPoint = new Point(6, 2);

            List<Point> route = ma.findRoute(targetPoint);

            assertAll(() -> {
                assertThat(route).hasSize(2);
                assertThat(route).containsExactly(new Point(5, 3), new Point(6, 2));
            });
        }

        @Test
        @DisplayName("좌-우측대각선로 이동 경로를 생성할 수 있다.")
        void checkLeftRightRouteMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));

            Point targetPoint = new Point(4, 2);

            List<Point> route = ma.findRoute(targetPoint);

            assertAll(() -> {
                assertThat(route).hasSize(2);
                assertThat(route).containsExactly(new Point(5, 3), new Point(4, 2));
            });
        }

        @Test
        @DisplayName("상-좌측대각선로 이동 경로를 생성할 수 있다.")
        void checkUpLeftRouteMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));

            Point targetPoint = new Point(3, 3);

            List<Point> route = ma.findRoute(targetPoint);

            assertAll(() -> {
                assertThat(route).hasSize(2);
                assertThat(route).containsExactly(new Point(4, 4), new Point(3, 3));
            });
        }
    }
}
