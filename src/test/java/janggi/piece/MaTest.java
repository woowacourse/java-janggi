package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.game.Board;
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
            Board board = new Board(List.of(
                    ma
            ));

            assertThat(ma.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("우-좌측대각선로 이동할 수 있다면 true를 반환한다.")
        void checkRightLeftMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(4, 6);
            Board board = new Board(List.of(
                    ma
            ));

            assertThat(ma.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("우-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkRightRightMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(6, 6);
            Board board = new Board(List.of(
                    ma
            ));

            assertThat(ma.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("하-좌측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkDownLeftMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(7, 5);
            Board board = new Board(List.of(
                    ma
            ));

            assertThat(ma.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("하-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkDownRightMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(7, 3);
            Board board = new Board(List.of(
                    ma
            ));

            assertThat(ma.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("좌-좌측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkLeftLeftMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(6, 2);
            Board board = new Board(List.of(
                    ma
            ));

            assertThat(ma.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("좌-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkLeftRightMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(4, 2);
            Board board = new Board(List.of(
                    ma
            ));

            assertThat(ma.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("상-좌측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkUpLeftMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(3, 3);
            Board board = new Board(List.of(
                    ma
            ));

            assertThat(ma.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }
    }

    @Nested
    @DisplayName("장애물 테스트")
    class HurdleTest {
        @Test
        @DisplayName("좌-우측대각선으로 이동 시 장애물이 있으면 이동가능 여부는 false이다.")
        void checkLeftHurdle() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(4, 2);
            Board board = new Board(List.of(
                    ma,
                    new Byeong(Team.HAN, new Point(5, 3))
            ));

            assertThat(ma.isInMovingRange(targetPoint, board.findHurdles())).isFalse();
        }

        @Test
        @DisplayName("좌-우측대각선로 이동 시 장애물이 없으면 이동가능 여부는 true이다.")
        void checkLeftNoHurdle() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(4, 2);
            Board board = new Board(List.of(
                    ma
            ));

            assertThat(ma.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }
    }

    @Nested
    @DisplayName("공격 테스트")
    class AttackTest {

        @Test
        @DisplayName("상-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkUpRightMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(3, 5);
            Board board = new Board(List.of(
                    ma,
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(ma.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("우-좌측대각선로 이동할 수 있다면 true를 반환한다.")
        void checkRightLeftMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(4, 6);
            Board board = new Board(List.of(
                    ma,
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(ma.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("우-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkRightRightMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(6, 6);
            Board board = new Board(List.of(
                    ma,
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(ma.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("하-좌측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkDownLeftMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(7, 5);
            Board board = new Board(List.of(
                    ma,
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(ma.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("하-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkDownRightMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(7, 3);
            Board board = new Board(List.of(
                    ma,
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(ma.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("좌-좌측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkLeftLeftMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(6, 2);
            Board board = new Board(List.of(
                    ma,
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(ma.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("좌-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkLeftRightMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(4, 2);
            Board board = new Board(List.of(
                    ma,
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(ma.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("상-좌측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkUpLeftMovable() {
            Ma ma = new Ma(Team.CHO, new Point(5, 4));
            Point targetPoint = new Point(3, 3);
            Board board = new Board(List.of(
                    ma,
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(ma.isInMovingRange(targetPoint, board.findHurdles())).isTrue();
        }
    }

//    @Nested
//    @DisplayName("경로 테스트")
//    class RouteTest {
//
//        @Test
//        @DisplayName("상-우측대각선로 이동 경로를 생성할 수 있다.")
//        void checkUpRightRouteMovable() {
//            Ma ma = new Ma(Team.CHO, new Point(5, 4));
//
//            Point targetPoint = new Point(3, 5);
//
//            Route route = ma.findRoute(targetPoint);
//
//            assertAll(() -> {
//                assertThat(route).isEqualTo(new Route(List.of(
//                        new Point(4, 4), new Point(3, 5)
//                )));
//            });
//        }
//
//        @Test
//        @DisplayName("우-좌측대각선로 이동 경로를 생성할 수 있다.")
//        void checkRightLeftRouteMovable() {
//            Ma ma = new Ma(Team.CHO, new Point(5, 4));
//
//            Point targetPoint = new Point(4, 6);
//
//            Route route = ma.findRoute(targetPoint);
//
//            assertAll(() -> {
//                assertThat(route).isEqualTo(new Route(List.of(
//                        new Point(5, 5), new Point(4, 6)
//                )));
//            });
//        }
//
//        @Test
//        @DisplayName("우-우측대각선로 이동 경로를 생성할 수 있다.")
//        void checkRightRightRouteMovable() {
//            Ma ma = new Ma(Team.CHO, new Point(5, 4));
//
//            Point targetPoint = new Point(6, 6);
//
//            Route route = ma.findRoute(targetPoint);
//
//            assertAll(() -> {
//                assertThat(route).isEqualTo(new Route(List.of(
//                        new Point(5, 5), new Point(6, 6)
//                )));
//            });
//        }
//
//        @Test
//        @DisplayName("하-좌측대각선로 이동 경로를 생성할 수 있다.")
//        void checkDownLeftRouteMovable() {
//            Ma ma = new Ma(Team.CHO, new Point(5, 4));
//
//            Point targetPoint = new Point(7, 5);
//
//            Route route = ma.findRoute(targetPoint);
//
//            assertAll(() -> {
//                assertThat(route).isEqualTo(new Route(List.of(
//                        new Point(6, 4), new Point(7, 5)
//                )));
//            });
//        }
//
//        @Test
//        @DisplayName("하-우측대각선로 이동 경로를 생성할 수 있다.")
//        void checkDownRightRouteMovable() {
//            Ma ma = new Ma(Team.CHO, new Point(5, 4));
//
//            Point targetPoint = new Point(7, 3);
//
//            Route route = ma.findRoute(targetPoint);
//
//            assertAll(() -> {
//                assertThat(route).isEqualTo(new Route(List.of(
//                        new Point(6, 4), new Point(7, 3)
//                )));
//            });
//        }
//
//        @Test
//        @DisplayName("좌-좌측대각선로 이동 경로를 생성할 수 있다.")
//        void checkLeftLeftRouteMovable() {
//            Ma ma = new Ma(Team.CHO, new Point(5, 4));
//
//            Point targetPoint = new Point(6, 2);
//
//            Route route = ma.findRoute(targetPoint);
//
//            assertAll(() -> {
//                assertThat(route).isEqualTo(new Route(List.of(
//                        new Point(5, 3), new Point(6, 2)
//                )));
//            });
//        }
//
//        @Test
//        @DisplayName("좌-우측대각선로 이동 경로를 생성할 수 있다.")
//        void checkLeftRightRouteMovable() {
//            Ma ma = new Ma(Team.CHO, new Point(5, 4));
//
//            Point targetPoint = new Point(4, 2);
//
//            Route route = ma.findRoute(targetPoint);
//
//            assertAll(() -> {
//                assertThat(route).isEqualTo(new Route(List.of(
//                        new Point(5, 3), new Point(4, 2)
//                )));
//            });
//        }
//
//        @Test
//        @DisplayName("상-좌측대각선로 이동 경로를 생성할 수 있다.")
//        void checkUpLeftRouteMovable() {
//            Ma ma = new Ma(Team.CHO, new Point(5, 4));
//
//            Point targetPoint = new Point(3, 3);
//
//            Route route = ma.findRoute(targetPoint);
//
//            assertAll(() -> {
//                assertThat(route).isEqualTo(new Route(List.of(
//                        new Point(4, 4), new Point(3, 3)
//                )));
//            });
//        }
//    }
}
