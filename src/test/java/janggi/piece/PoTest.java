package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.Movable;
import janggi.TeamColor;
import janggi.point.Point;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PoTest {

    @Nested
    @DisplayName("이동 가능 확인 테스트")
    class CheckMovableTest {




    }

    @Nested
    @DisplayName("경로 테스트")
    class RouteTest {

        @Test
        @DisplayName("좌로 이동 경로를 생성할 수 있다.")
        void checkLeftRouteMovable() {
            Po po = new Po(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(6, 3);

            List<Point> route = po.findRoute(targetPoint);

            assertAll(() -> {
                assertThat(route).hasSize(3);
                assertThat(route).containsExactly(new Point(6,5), new Point(6,4), new Point(6,3));
            });
        }

        @Test
        @DisplayName("우로 이동 경로를 생성할 수 있다.")
        void checkRightRouteMovable() {
            Po po = new Po(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(6, 8);

            List<Point> route = po.findRoute(targetPoint);

            assertAll(() -> {
                assertThat(route).hasSize(2);
                assertThat(route).containsExactly(new Point(6,7), new Point(6, 8));
            });
        }

        @Test
        @DisplayName("상로 이동 경로를 생성할 수 있다.")
        void checkUpRouteMovable() {
            Po po = new Po(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(2, 6);

            List<Point> route = po.findRoute(targetPoint);

            assertAll(() -> {
                assertThat(route).hasSize(4);
                assertThat(route).containsExactly(new Point(5,6), new Point(4, 6),
                        new Point(3, 6), new Point(2, 6));
            });
        }

        @Test
        @DisplayName("하로 이동 경로를 생성할 수 있다.")
        void checkDownRouteMovable() {
            Po po = new Po(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(8, 6);

            List<Point> route = po.findRoute(targetPoint);

            assertAll(() -> {
                assertThat(route).hasSize(2);
                assertThat(route).containsExactly(new Point(7,6), new Point(8, 6));
            });
        }
    }
}
