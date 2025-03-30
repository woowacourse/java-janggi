package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.game.Board;
import janggi.game.Team;
import janggi.point.Point;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SaTest {

    @Nested
    @DisplayName("이동 가능 확인 테스트")
    class CheckPieceTest {

        @Test
        @DisplayName("북서쪽으로 이동할 수 있다면 true를 반환한다.")
        void checkUpLeftMovable() {
            Sa sa = new Sa(Team.CHO, new Point(8, 4));
            Point targetPoint = new Point(7, 3);
            Board board = new Board(List.of(
                    sa
            ));

            assertThat(sa.canMove(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("좌로 이동할 수 있다면 true를 반환한다.")
        void checkLeftMovable() {
            Sa sa = new Sa(Team.CHO, new Point(8, 4));
            Point targetPoint = new Point(8, 3);
            Board board = new Board(List.of(
                    sa
            ));

            assertThat(sa.canMove(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("우로 이동할 수 있다면 true를 반환한다.")
        void checkRightMovable() {
            Sa sa = new Sa(Team.CHO, new Point(8, 4));
            Point targetPoint = new Point(8, 5);
            Board board = new Board(List.of(
                    sa
            ));

            assertThat(sa.canMove(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("위로 이동할 수 있다면 true를 반환한다.")
        void checkUpMovable() {
            Sa sa = new Sa(Team.CHO, new Point(8, 4));
            Point targetPoint = new Point(7, 4);
            Board board = new Board(List.of(
                    sa
            ));

            assertThat(sa.canMove(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("아래로 이동할 수 있다면 true를 반환한다.")
        void checkDownMovable() {
            Sa sa = new Sa(Team.CHO, new Point(8, 4));
            Point targetPoint = new Point(9, 4);
            Board board = new Board(List.of(
                    sa
            ));

            assertThat(sa.canMove(targetPoint, board.findHurdles())).isTrue();
        }
    }

    @Nested
    @DisplayName("장애물 테스트")
    class HurdleTest {
        @Test
        @DisplayName("좌로 이동 시 장애물이 있으면 이동가능 여부는 false이다.")
        void checkLeftHurdle() {
            Sa sa = new Sa(Team.CHO, new Point(8, 4));
            Point targetPoint = new Point(8, 3);
            Board board = new Board(List.of(
                    sa,
                    new Byeong(Team.CHO, targetPoint)
            ));

            assertThat(sa.canMove(targetPoint, board.findHurdles())).isFalse();
        }

        @Test
        @DisplayName("좌로 이동 시 장애물이 없으면 이동가능 여부는 true이다.")
        void checkLeftNoHurdle() {
            Sa sa = new Sa(Team.CHO, new Point(8, 4));
            Point targetPoint = new Point(8, 3);
            Board board = new Board(List.of(
                    sa
            ));

            assertThat(sa.canMove(targetPoint, board.findHurdles())).isTrue();
        }
    }

    @Nested
    @DisplayName("공격 테스트")
    class AttackTest {

        @Test
        @DisplayName("좌로 이동할 수 있다면 true를 반환한다.")
        void checkLeftMovable() {
            Sa sa = new Sa(Team.CHO, new Point(8, 4));
            Point targetPoint = new Point(8, 3);
            Board board = new Board(List.of(
                    sa,
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(sa.canMove(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("우로 이동할 수 있다면 true를 반환한다.")
        void checkRightMovable() {
            Sa sa = new Sa(Team.CHO, new Point(8, 4));
            Point targetPoint = new Point(8, 5);
            Board board = new Board(List.of(
                    sa,
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(sa.canMove(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("위로 이동할 수 있다면 true를 반환한다.")
        void checkUpMovable() {
            Sa sa = new Sa(Team.CHO, new Point(8, 4));
            Point targetPoint = new Point(7, 4);
            Board board = new Board(List.of(
                    sa,
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(sa.canMove(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("아래로 이동할 수 있다면 true를 반환한다.")
        void checkDownMovable() {
            Sa sa = new Sa(Team.CHO, new Point(8, 4));
            Point targetPoint = new Point(9, 4);
            Board board = new Board(List.of(
                    sa,
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(sa.canMove(targetPoint, board.findHurdles())).isTrue();
        }
    }
}
