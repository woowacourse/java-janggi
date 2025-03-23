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

public class ChaTest {

    @Nested
    @DisplayName("이동 가능 확인 테스트")
    class CheckMovableTest {

        @Test
        @DisplayName("좌로 이동할 수 있다면 true를 반환한다.")
        void checkLeftMovable() {
            Cha cha = new Cha(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(6, 4);

            assertThat(cha.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("우로 이동할 수 있다면 true를 반환한다.")
        void checkRightMovable() {
            Cha cha = new Cha(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(6, 8);

            assertThat(cha.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("상으로 이동할 수 있다면 true를 반환한다.")
        void checkUpMovable() {
            Cha cha = new Cha(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(4, 6);

            assertThat(cha.isInMovingRange(startPoint, targetPoint)).isTrue();
        }

        @Test
        @DisplayName("하으로 이동할 수 있다면 true를 반환한다.")
        void checkDownMovable() {
            Cha cha = new Cha(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(8, 6);

            assertThat(cha.isInMovingRange(startPoint, targetPoint)).isTrue();
        }
    }

    @Nested
    @DisplayName("경로 테스트")
    class RouteTest {

        @Test
        @DisplayName("좌로 이동 경로를 생성할 수 있다.")
        void checkLeftRouteMovable() {
            Cha cha = new Cha(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(6, 4);

            Route route = cha.findRoute(startPoint, targetPoint);
            List<Point> path = route.getPath();

            assertAll(() -> {
                assertThat(path).hasSize(1);
                assertThat(path).containsExactly(new Point(6, 5));
                assertThat(route.getTargetPoint()).isEqualTo(new Point(6, 4));
            });
        }

        @Test
        @DisplayName("우로 이동 경로를 생성할 수 있다.")
        void checkRightRouteMovable() {
            Cha cha = new Cha(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(6, 8);

            Route route = cha.findRoute(startPoint, targetPoint);
            List<Point> path = route.getPath();

            assertAll(() -> {
                assertThat(path).hasSize(1);
                assertThat(path).containsExactly(new Point(6, 7));
                assertThat(route.getTargetPoint()).isEqualTo(new Point(6, 8));
            });
        }

        @Test
        @DisplayName("상로 이동 경로를 생성할 수 있다.")
        void checkUpRouteMovable() {
            Cha cha = new Cha(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(4, 6);

            Route route = cha.findRoute(startPoint, targetPoint);
            List<Point> path = route.getPath();

            assertAll(() -> {
                assertThat(path).hasSize(1);
                assertThat(path).containsExactly(new Point(5, 6));
                assertThat(route.getTargetPoint()).isEqualTo(new Point(4, 6));
            });
        }

        @Test
        @DisplayName("하로 이동 경로를 생성할 수 있다.")
        void checkDownRouteMovable() {
            Cha cha = new Cha(Team.CHO);

            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(8, 6);

            Route route = cha.findRoute(startPoint, targetPoint);
            List<Point> path = route.getPath();

            assertAll(() -> {
                assertThat(path).hasSize(1);
                assertThat(path).containsExactly(new Point(7, 6));
                assertThat(route.getTargetPoint()).isEqualTo(new Point(8, 6));
            });
        }
    }
}
