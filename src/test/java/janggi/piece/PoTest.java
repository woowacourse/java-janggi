package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.game.Board;
import janggi.game.Team;
import janggi.point.Point;
import janggi.point.Route;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PoTest {

    @Nested
    @DisplayName("이동 가능 확인 테스트")
    class CheckMovableTest {

        @Test
        @DisplayName("좌로 이동할 수 있다면 true를 반환한다.")
        void checkLeftMovable() {
            Po po = new Po(Team.CHO, new Point(6, 6));
            Point targetPoint = new Point(6, 4);
            Board board = new Board(List.of(
                    po,
                    new Byeong(Team.CHO, new Point(6, 5)),
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(po.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("우로 이동할 수 있다면 true를 반환한다.")
        void checkRightMovable() {
            Po po = new Po(Team.CHO, new Point(6, 6));
            Point targetPoint = new Point(6, 8);
            Board board = new Board(List.of(
                    po,
                    new Byeong(Team.CHO, new Point(6, 7)),
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(po.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("상으로 이동할 수 있다면 true를 반환한다.")
        void checkUpMovable() {
            Po po = new Po(Team.CHO, new Point(6, 6));
            Point targetPoint = new Point(4, 6);
            Board board = new Board(List.of(
                    po,
                    new Byeong(Team.CHO, new Point(5, 6)),
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(po.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("하으로 이동할 수 있다면 true를 반환한다.")
        void checkDownMovable() {
            Po po = new Po(Team.CHO, new Point(6, 6));
            Point targetPoint = new Point(8, 6);
            Board board = new Board(List.of(
                    po,
                    new Byeong(Team.CHO, new Point(7, 6)),
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(po.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }

        //TODO 공격테스트에 추가
        @Test
        @DisplayName("상으로 이동 시 포를 공격하려고 하면 이동가능 여부는 false이다.")
        void checkUpWithPoPrey() {

        }
    }

    @Nested
    @DisplayName("장애물 테스트")
    class HurdleTest {
        @Test
        @DisplayName("상으로 이동 시 장애물이 있으면 이동가능 여부는 false이다.")
        void checkUpHurdle() {
            Po po = new Po(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(1, 4);
            Point bridgePoint = new Point(3, 4);
            Board board = new Board(List.of(
                    po,
                    new Byeong(Team.CHO, bridgePoint),
                    new Byeong(Team.CHO, new Point(2, 4))
            ));

            assertThat(po.isInMovingRange(targetPoint, board.findHurdles())).isFalse();
        }

        @Test
        @DisplayName("상으로 이동 시 장애물이 없으면 이동가능 여부는 true이다.")
        void checkUpNoHurdle() {
            Po po = new Po(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(1, 4);
            Point bridgePoint = new Point(3, 4);
            Board board = new Board(List.of(
                    po,
                    new Byeong(Team.CHO, bridgePoint)
            ));

            assertThat(po.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }

        //TODO 채우기
        @Test
        @DisplayName("상으로 이동 시 포를 건너뛰려고 하면 이동가능 여부는 false이다.")
        void checkUpWithPoBridge() {
            Po po = new Po(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(1, 4);
            Point bridgePoint = new Point(3, 4);
            Board board = new Board(List.of(
                    po,
                    new Po(Team.CHO, bridgePoint)
            ));

            assertThat(po.isInMovingRange(targetPoint, board.findHurdles())).isFalse();
        }

        @Test
        @DisplayName("상으로 이동 시 장애물이 아예 없으면 이동가능 여부는 false이다.")
        void checkUpWithNoBridge() {
            Po po = new Po(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(1, 4);
            Board board = new Board(List.of(
                    po,
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(po.isInMovingRange(targetPoint, board.findHurdles())).isFalse();
        }
    }

//    @Nested
//    @Disabled
//    @DisplayName("경로 테스트")
//    class RouteTest {
//
//        @Test
//        @DisplayName("좌로 이동 경로를 생성할 수 있다.")
//        void checkLeftRouteMovable() {
//            Po po = new Po(Team.CHO, new Point(6, 6));
//
//            Point targetPoint = new Point(6, 3);
//
//            Route route = po.findRoute(targetPoint);
//
//            assertAll(() -> {
//                assertThat(route).isEqualTo(new Route(List.of(
//                        new Point(6, 5), new Point(6, 4)
//                )));
//            });
//        }
//
//        @Test
//        @DisplayName("우로 이동 경로를 생성할 수 있다.")
//        void checkRightRouteMovable() {
//            Po po = new Po(Team.CHO, new Point(6, 6));
//
//            Point targetPoint = new Point(6, 8);
//
//            Route route = po.findRoute(targetPoint);
//
//            assertAll(() -> {
//                assertThat(route).isEqualTo(new Route(List.of(
//                        new Point(6, 7)
//                )));
//            });
//        }
//
//        @Test
//        @DisplayName("상로 이동 경로를 생성할 수 있다.")
//        void checkUpRouteMovable() {
//            Po po = new Po(Team.CHO, new Point(6, 6));
//
//            Point targetPoint = new Point(2, 6);
//
//            Route route = po.findRoute(targetPoint);
//
//            assertAll(() -> {
//                assertThat(route).isEqualTo(new Route(List.of(
//                        new Point(5, 6), new Point(4, 6), new Point(3, 6)
//                )));
//            });
//        }
//
//        @Test
//        @DisplayName("하로 이동 경로를 생성할 수 있다.")
//        void checkDownRouteMovable() {
//            Po po = new Po(Team.CHO, new Point(6, 6));
//
//            Point targetPoint = new Point(8, 6);
//
//            Route route = po.findRoute(targetPoint);
//
//            assertAll(() -> {
//                assertThat(route).isEqualTo(new Route(List.of(
//                        new Point(7, 6)
//                )));
//            });
//        }
//    }
}
