package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.game.Team;
import janggi.point.Point;
import janggi.point.Route;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SangTest {

    @Nested
    @DisplayName("이동 가능 확인 테스트")
    class CheckMovableTest {

        @Test
        @DisplayName("상-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkUpRightMovable() {
            Sang sang = new Sang(Team.CHO);

            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(2, 6);

            assertThat(sang.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("우-좌측대각선로 이동할 수 있다면 true를 반환한다.")
        void checkRightLeftMovable() {
            Sang sang = new Sang(Team.CHO);

            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(3, 7);

            assertThat(sang.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("우-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkRightRightMovable() {
            Sang sang = new Sang(Team.CHO);

            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(7, 7);

            assertThat(sang.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("하-좌측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkDownLeftMovable() {
            Sang sang = new Sang(Team.CHO);

            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(8, 6);

            assertThat(sang.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("하-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkDownRightMovable() {
            Sang sang = new Sang(Team.CHO);

            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(8, 2);

            assertThat(sang.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("좌-좌측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkLeftLeftMovable() {
            Sang sang = new Sang(Team.CHO);

            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(7, 1);

            assertThat(sang.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("좌-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkLeftRightMovable() {
            Sang sang = new Sang(Team.CHO);

            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(3, 1);

            assertThat(sang.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("상-좌측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkUpLeftMovable() {
            Sang sang = new Sang(Team.CHO);

            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(2, 2);

            assertThat(sang.isInMovingRange(startPoint, targetPoint)).isTrue();
        }
    }

    @Nested
    @DisplayName("경로 테스트")
    class RouteTest {

        @Test
        @DisplayName("상-우측대각선로 이동 경로를 생성할 수 있다.")
        void checkUpRightRouteMovable() {
            Sang sang = new Sang(Team.CHO);

            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(2, 6);

            Route route = sang.findRoute(startPoint, targetPoint);
            List<Point> path = route.getPath();

            assertAll(() -> {
                assertThat(path).hasSize(2);
                assertThat(path).containsExactly(new Point(4, 4), new Point(3, 5));
                assertThat(route.getTargetPoint()).isEqualTo(new Point(2, 6));
            });
        }

        @Test
        @DisplayName("우-좌측대각선로 이동 경로를 생성할 수 있다.")
        void checkRightLeftRouteMovable() {
            Sang sang = new Sang(Team.CHO);

            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(3, 7);

            Route route = sang.findRoute(startPoint, targetPoint);
            List<Point> path = route.getPath();

            assertAll(() -> {
                assertThat(path).hasSize(2);
                assertThat(path).containsExactly(new Point(5, 5), new Point(4, 6));
                assertThat(route.getTargetPoint()).isEqualTo(new Point(3, 7));
            });
        }

        @Test
        @DisplayName("우-우측대각선로 이동 경로를 생성할 수 있다.")
        void checkRightRightRouteMovable() {
            Sang sang = new Sang(Team.CHO);

            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(7, 7);

            Route route = sang.findRoute(startPoint, targetPoint);
            List<Point> path = route.getPath();

            assertAll(() -> {
                assertThat(path).hasSize(2);
                assertThat(path).containsExactly(new Point(5, 5), new Point(6, 6));
                assertThat(route.getTargetPoint()).isEqualTo(new Point(7, 7));
            });
        }

        @Test
        @DisplayName("하-좌측대각선로 이동 경로를 생성할 수 있다.")
        void checkDownLeftRouteMovable() {
            Sang sang = new Sang(Team.CHO);

            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(8, 6);

            Route route = sang.findRoute(startPoint, targetPoint);
            List<Point> path = route.getPath();

            assertAll(() -> {
                assertThat(path).hasSize(2);
                assertThat(path).containsExactly(new Point(6, 4), new Point(7, 5));
                assertThat(route.getTargetPoint()).isEqualTo(new Point(8, 6));
            });
        }

        @Test
        @DisplayName("하-우측대각선로 이동 경로를 생성할 수 있다.")
        void checkDownRightRouteMovable() {
            Sang sang = new Sang(Team.CHO);

            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(8, 2);

            Route route = sang.findRoute(startPoint, targetPoint);
            List<Point> path = route.getPath();

            assertAll(() -> {
                assertThat(path).hasSize(2);
                assertThat(path).containsExactly(new Point(6, 4), new Point(7, 3));
                assertThat(route.getTargetPoint()).isEqualTo(new Point(8, 2));
            });
        }

        @Test
        @DisplayName("좌-좌측대각선로 이동 경로를 생성할 수 있다.")
        void checkLeftLeftRouteMovable() {
            Sang sang = new Sang(Team.CHO);

            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(7, 1);

            Route route = sang.findRoute(startPoint, targetPoint);
            List<Point> path = route.getPath();

            assertAll(() -> {
                assertThat(path).hasSize(2);
                assertThat(path).containsExactly(new Point(5, 3), new Point(6, 2));
                assertThat(route.getTargetPoint()).isEqualTo(new Point(7, 1));
            });
        }

        @Test
        @DisplayName("좌-우측대각선로 이동 경로를 생성할 수 있다.")
        void checkLeftRightRouteMovable() {
            Sang sang = new Sang(Team.CHO);

            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(3, 1);

            Route route = sang.findRoute(startPoint, targetPoint);
            List<Point> path = route.getPath();

            assertAll(() -> {
                assertThat(path).hasSize(2);
                assertThat(path).containsExactly(new Point(5, 3), new Point(4, 2));
                assertThat(route.getTargetPoint()).isEqualTo(new Point(3, 1));
            });
        }

        @Test
        @DisplayName("상-좌측대각선로 이동 경로를 생성할 수 있다.")
        void checkUpLeftRouteMovable() {
            Sang sang = new Sang(Team.CHO);

            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(2, 2);

            Route route = sang.findRoute(startPoint, targetPoint);
            List<Point> path = route.getPath();

            assertAll(() -> {
                assertThat(path).hasSize(2);
                assertThat(path).containsExactly(new Point(4, 4), new Point(3, 3));
                assertThat(route.getTargetPoint()).isEqualTo(new Point(2, 2));
            });
        }
    }
}
