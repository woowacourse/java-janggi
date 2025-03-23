package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.game.Team;
import janggi.point.Point;
import janggi.point.Route;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SaTest {

    @Nested
    @DisplayName("이동 가능 확인 테스트")
    class CheckMovableTest {

        @Test
        @DisplayName("좌로 이동할 수 있다면 true를 반환한다.")
        void checkLeftMovable() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(6, 5);

            assertThat(sa.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("우로 이동할 수 있다면 true를 반환한다.")
        void checkRightMovable() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(6, 7);

            assertThat(sa.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("상으로 이동할 수 있다면 true를 반환한다.")
        void checkUpMovable() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(5, 6);

            assertThat(sa.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("하으로 이동할 수 있다면 true를 반환한다.")
        void checkDownMovable() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(7, 6);

            assertThat(sa.isInMovingRange(startPoint, targetPoint)).isTrue();
        }
    }

    @Nested
    @DisplayName("경로 테스트")
    class RouteTest {

        @Test
        @DisplayName("좌로 이동 경로를 생성할 수 있다.")
        void checkLeftRouteMovable() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(6, 5);

            Route route = sa.findRoute(startPoint, targetPoint);

            assertAll(() -> {
                assertThat(route.getPath()).hasSize(0);
                assertThat(route.getTargetPoint()).isEqualTo(new Point(6, 5));
            });
        }

        @Test
        @DisplayName("우로 이동 경로를 생성할 수 있다.")
        void checkRightRouteMovable() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(6, 7);

            Route route = sa.findRoute(startPoint, targetPoint);

            assertAll(() -> {
                assertThat(route.getPath()).hasSize(0);
                assertThat(route.getTargetPoint()).isEqualTo(new Point(6, 7));
            });
        }

        @Test
        @DisplayName("상로 이동 경로를 생성할 수 있다.")
        void checkUpRouteMovable() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(5, 6);

            Route route = sa.findRoute(startPoint, targetPoint);

            assertAll(() -> {
                assertThat(route.getPath()).hasSize(0);
                assertThat(route.getTargetPoint()).isEqualTo(new Point(5, 6));
            });
        }

        @Test
        @DisplayName("하로 이동 경로를 생성할 수 있다.")
        void checkDownRouteMovable() {
            Sa sa = new Sa(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(7, 6);

            Route route = sa.findRoute(startPoint, targetPoint);

            assertAll(() -> {
                assertThat(route.getPath()).hasSize(0);
                assertThat(route.getTargetPoint()).isEqualTo(new Point(7, 6));
            });
        }
    }
}
