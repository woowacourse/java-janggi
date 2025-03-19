package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.Team;
import janggi.point.Point;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class GungTest {

    @Nested
    @DisplayName("궁 말 초기화 테스트")
    class InitPieceTest {

        @Test
        @DisplayName("팀당 한 개의 말을 생성할 수 있다.")
        void createOnePiecePerTeam() {
            List<Gung> gungs = Gung.values();

            assertThat(gungs).hasSize(2);
        }
    }

    @Nested
    @DisplayName("이동 가능 확인 테스트")
    class CheckMovableTest {

        @Test
        @DisplayName("좌로 이동할 수 있다면 true를 반환한다.")
        void checkLeftMovable() {
            Gung gung = new Gung(Team.CHO, new Point(6, 6));

            Point targetPoint = new Point(6, 5);

            assertThat(gung.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("우로 이동할 수 있다면 true를 반환한다.")
        void checkRightMovable() {
            Gung gung = new Gung(Team.CHO, new Point(6, 6));

            Point targetPoint = new Point(6, 7);

            assertThat(gung.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("상으로 이동할 수 있다면 true를 반환한다.")
        void checkUpMovable() {
            Gung gung = new Gung(Team.CHO, new Point(6, 6));

            Point targetPoint = new Point(5, 6);

            assertThat(gung.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("하으로 이동할 수 있다면 true를 반환한다.")
        void checkDownMovable() {
            Gung gung = new Gung(Team.CHO, new Point(6, 6));

            Point targetPoint = new Point(7, 6);

            assertThat(gung.isMovable(targetPoint)).isTrue();
        }
    }

    @Nested
    @DisplayName("경로 테스트")
    class RouteTest {

        @Test
        @DisplayName("좌로 이동 경로를 생성할 수 있다.")
        void checkLeftRouteMovable() {
            Gung gung = new Gung(Team.CHO, new Point(6, 6));

            Point targetPoint = new Point(6, 5);

            List<Point> route = gung.findRoute(targetPoint);

            assertAll(() -> {
                assertThat(route).hasSize(1);
                assertThat(route).containsExactly(new Point(6,5));
            });
        }

        @Test
        @DisplayName("우로 이동 경로를 생성할 수 있다.")
        void checkRightRouteMovable() {
            Gung gung = new Gung(Team.CHO, new Point(6, 6));

            Point targetPoint = new Point(6, 7);

            List<Point> route = gung.findRoute(targetPoint);

            assertAll(() -> {
                assertThat(route).hasSize(1);
                assertThat(route).containsExactly(new Point(6,7));
            });
        }

        @Test
        @DisplayName("상로 이동 경로를 생성할 수 있다.")
        void checkUpRouteMovable() {
            Gung gung = new Gung(Team.CHO, new Point(6, 6));

            Point targetPoint = new Point(5, 6);

            List<Point> route = gung.findRoute(targetPoint);

            assertAll(() -> {
                assertThat(route).hasSize(1);
                assertThat(route).containsExactly(new Point(5,6));
            });
        }

        @Test
        @DisplayName("하로 이동 경로를 생성할 수 있다.")
        void checkDownRouteMovable() {
            Gung gung = new Gung(Team.CHO, new Point(6, 6));

            Point targetPoint = new Point(7, 6);

            List<Point> route = gung.findRoute(targetPoint);

            assertAll(() -> {
                assertThat(route).hasSize(1);
                assertThat(route).containsExactly(new Point(7,6));
            });
        }
    }
}
