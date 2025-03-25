package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.game.Board;
import janggi.game.Team;
import janggi.point.Point;
import java.util.List;
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

            assertThat(po.canMove(targetPoint, board.findHurdles())).isTrue();
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

            assertThat(po.canMove(targetPoint, board.findHurdles())).isTrue();
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

            assertThat(po.canMove(targetPoint, board.findHurdles())).isTrue();
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

            assertThat(po.canMove(targetPoint, board.findHurdles())).isTrue();
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

            assertThat(po.canMove(targetPoint, board.findHurdles())).isFalse();
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

            assertThat(po.canMove(targetPoint, board.findHurdles())).isTrue();
        }

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

            assertThat(po.canMove(targetPoint, board.findHurdles())).isFalse();
        }

        @Test
        @DisplayName("상으로 이동 시 장애물이 아예 없으면 이동가능 여부는 false이다.")
        void checkUpWithNoBridge() {
            Po po = new Po(Team.CHO, new Point(7, 1));
            Point targetPoint = new Point(5, 1);
            Board board = new Board(List.of(
                    po
            ));

            assertThat(po.canMove(targetPoint, board.findHurdles())).isFalse();
        }
    }

    @Nested
    @DisplayName("공격 테스트")
    class AttackTest {

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

            assertThat(po.canMove(targetPoint, board.findHurdles())).isTrue();
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

            assertThat(po.canMove(targetPoint, board.findHurdles())).isTrue();
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

            assertThat(po.canMove(targetPoint, board.findHurdles())).isTrue();
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

            assertThat(po.canMove(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("좌로 이동 시 포를 공격하려고 하면 이동가능 여부는 false이다.")
        void checkUpWithPoPrey() {
            Po po = new Po(Team.CHO, new Point(6, 6));
            Point targetPoint = new Point(6, 4);
            Board board = new Board(List.of(
                    po,
                    new Byeong(Team.CHO, new Point(6, 5)),
                    new Po(Team.HAN, targetPoint)
            ));

            assertThat(po.canMove(targetPoint, board.findHurdles())).isFalse();
        }
    }
}
