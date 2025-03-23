package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.game.Team;
import janggi.point.Point;
import janggi.point.Route;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class GungTest {

    @Nested
    @DisplayName("이동 가능 확인 테스트")
    class CheckMovableTest {

        @Test
        @DisplayName("좌로 이동할 수 있다면 true를 반환한다.")
        void checkLeftMovable() {
            Gung gung = new Gung(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(6, 5);

            assertThat(gung.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("우로 이동할 수 있다면 true를 반환한다.")
        void checkRightMovable() {
            Gung gung = new Gung(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(6, 7);

            assertThat(gung.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("상으로 이동할 수 있다면 true를 반환한다.")
        void checkUpMovable() {
            Gung gung = new Gung(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(5, 6);

            assertThat(gung.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("하으로 이동할 수 있다면 true를 반환한다.")
        void checkDownMovable() {
            Gung gung = new Gung(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(7, 6);

            assertThat(gung.isInMovingRange(startPoint, targetPoint)).isTrue();
        }
    }

    @Nested
    @DisplayName("경로 테스트")
    class RouteTest {

        @Test
        @DisplayName("좌로 이동 경로를 생성할 수 있다.")
        void checkLeftRouteMovable() {
            Gung gung = new Gung(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(6, 5);

            Route route = gung.findRoute(startPoint, targetPoint);

            assertAll(() -> {
                assertThat(route.getPath()).hasSize(0);
                assertThat(route.getTargetPoint()).isEqualTo(new Point(6, 5));
            });
        }

        @Test
        @DisplayName("우로 이동 경로를 생성할 수 있다.")
        void checkRightRouteMovable() {
            Gung gung = new Gung(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(6, 7);

            Route route = gung.findRoute(startPoint, targetPoint);

            assertAll(() -> {
                assertThat(route.getPath()).hasSize(0);
                assertThat(route.getTargetPoint()).isEqualTo(new Point(6, 7));
            });
        }

        @Test
        @DisplayName("상로 이동 경로를 생성할 수 있다.")
        void checkUpRouteMovable() {
            Gung gung = new Gung(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(5, 6);

            Route route = gung.findRoute(startPoint, targetPoint);

            assertAll(() -> {
                assertThat(route.getPath()).hasSize(0);
                assertThat(route.getTargetPoint()).isEqualTo(new Point(5, 6));
            });
        }

        @Test
        @DisplayName("하로 이동 경로를 생성할 수 있다.")
        void checkDownRouteMovable() {
            Gung gung = new Gung(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(7, 6);

            Route route = gung.findRoute(startPoint, targetPoint);

            assertAll(() -> {
                assertThat(route.getPath()).hasSize(0);
                assertThat(route.getTargetPoint()).isEqualTo(new Point(7, 6));
            });
        }
    }
}
