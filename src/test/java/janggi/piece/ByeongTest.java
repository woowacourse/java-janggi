package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.game.Board;
import janggi.game.Palace;
import janggi.game.Team;
import janggi.movement.direction.Direction;
import janggi.point.Point;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ByeongTest {

    @Nested
    @DisplayName("이동 가능 확인 테스트")
    class CheckPieceTest {

        @Test
        @DisplayName("좌로 이동할 수 있다면 true를 반환한다.")
        void checkLeftMovable() {
            Byeong byeong = new Byeong(Team.CHO, new Point(6, 6));
            Point targetPoint = new Point(6, 5);
            Board board = new Board(List.of(
                    byeong
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("우로 이동할 수 있다면 true를 반환한다.")
        void checkRightMovable() {
            Byeong byeong = new Byeong(Team.CHO, new Point(6, 6));
            Point targetPoint = new Point(6, 7);
            Board board = new Board(List.of(
                    byeong
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("대각선으로 이동한면 false를 반환한다.")
        void checkDiagonalNotMovable() {
            Byeong byeong = new Byeong(Team.CHO, new Point(6, 6));
            Point targetPoint = new Point(3, 3);
            Board board = new Board(List.of(
                    byeong
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isFalse();
        }

        @Test
        @DisplayName("초나라의 경우 북쪽으로 이동할 수 있다면 true를 반환한다.")
        void checkUpMovableWithCho() {
            Byeong byeong = new Byeong(Team.CHO, new Point(6, 6));
            Point targetPoint = new Point(5, 6);
            Board board = new Board(List.of(
                    byeong
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("초나라의 경우 남쪽으로 이동할 수 있다면 false를 반환한다.")
        void checkDownMovableWithCho() {
            Byeong byeong = new Byeong(Team.CHO, new Point(6, 6));
            Point targetPoint = new Point(7, 6);
            Board board = new Board(List.of(
                    byeong
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isFalse();
        }

        @Test
        @DisplayName("한나라의 경우 북쪽으로 이동할 수 있다면 false를 반환한다.")
        void checkUpMovableWithHan() {
            Byeong byeong = new Byeong(Team.HAN, new Point(6, 6));
            Point targetPoint = new Point(5, 6);
            Board board = new Board(List.of(
                    byeong
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isFalse();
        }

        @Test
        @DisplayName("한나라의 경우 남쪽으로 이동할 수 있다면 true를 반환한다.")
        void checkDownMovableWithHan() {
            Byeong byeong = new Byeong(Team.HAN, new Point(6, 6));
            Point targetPoint = new Point(7, 6);
            Board board = new Board(List.of(
                    byeong
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("초나라의 경우 궁성에서 남서쪽으로 이동할 수 있다면 false를 반환한다.")
        void checkDownMovableWithChoInPalace() {
            Byeong byeong = new Byeong(Team.CHO, new Point(8, 4));
            Point targetPoint = new Point(9, 3);
            Board board = new Board(List.of(
                    byeong
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isFalse();
        }

        @Test
        @DisplayName("초나라의 경우 궁성에서 남동쪽으로 이동할 수 있다면 false를 반환한다.")
        void checkDownMovableWithChoInPalace_2() {
            Byeong byeong = new Byeong(Team.CHO, new Point(8, 4));
            Point targetPoint = new Point(9, 5);
            Board board = new Board(List.of(
                    byeong
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isFalse();
        }

        @Test
        @DisplayName("한나라의 경우 궁성에서 북서쪽으로 이동할 수 있다면 false를 반환한다.")
        void checkDownMovableWithHanInPalace() {
            Byeong byeong = new Byeong(Team.HAN, new Point(1, 4));
            Point targetPoint = new Point(0, 3);
            Board board = new Board(List.of(
                    byeong
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isFalse();
        }

        @Test
        @DisplayName("한나라의 경우 궁성에서 북동쪽으로 이동할 수 있다면 false를 반환한다.")
        void checkDownMovableWithHanInPalace_2() {
            Byeong byeong = new Byeong(Team.HAN, new Point(1, 4));
            Point targetPoint = new Point(0, 5);
            Board board = new Board(List.of(
                    byeong
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isFalse();
        }
    }

    @Nested
    @DisplayName("장애물 테스트")
    class HurdleTest {
        @Test
        @DisplayName("좌로 이동 시 장애물이 있으면 이동가능 여부는 false이다.")
        void checkLeftHurdle() {
            Byeong byeong = new Byeong(Team.CHO, new Point(6, 6));
            Point targetPoint = new Point(6, 5);
            Board board = new Board(List.of(
                    byeong,
                    new Byeong(Team.CHO, targetPoint)
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isFalse();
        }

        @Test
        @DisplayName("좌로 이동 시 장애물이 없으면 이동가능 여부는 true이다.")
        void checkLeftNoHurdle() {
            Byeong byeong = new Byeong(Team.CHO, new Point(6, 6));
            Point targetPoint = new Point(6, 5);
            Board board = new Board(List.of(
                    byeong
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isTrue();
        }
    }

    @Nested
    @DisplayName("공격 테스트")
    class AttackTest {

        @Test
        @DisplayName("좌로 이동할 수 있다면 true를 반환한다.")
        void checkLeftMovable() {
            Byeong byeong = new Byeong(Team.CHO, new Point(6, 6));
            Point targetPoint = new Point(6, 5);
            Board board = new Board(List.of(
                    byeong,
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("우로 이동할 수 있다면 true를 반환한다.")
        void checkRightMovable() {
            Byeong byeong = new Byeong(Team.CHO, new Point(6, 6));
            Point targetPoint = new Point(6, 7);
            Board board = new Board(List.of(
                    byeong,
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("대각선으로 이동한면 false를 반환한다.")
        void checkDiagonalNotMovable() {
            Byeong byeong = new Byeong(Team.CHO, new Point(6, 6));
            Point targetPoint = new Point(3, 3);
            Board board = new Board(List.of(
                    byeong,
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isFalse();
        }

        @Test
        @DisplayName("초나라의 경우 북쪽으로 이동할 수 있다면 true를 반환한다.")
        void checkUpMovableWithCho() {
            Byeong byeong = new Byeong(Team.CHO, new Point(6, 6));
            Point targetPoint = new Point(5, 6);
            Board board = new Board(List.of(
                    byeong,
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isTrue();
        }

        @Test
        @DisplayName("초나라의 경우 남쪽으로 이동할 수 있다면 false를 반환한다.")
        void checkDownMovableWithCho() {
            Byeong byeong = new Byeong(Team.CHO, new Point(6, 6));
            Point targetPoint = new Point(7, 6);
            Board board = new Board(List.of(
                    byeong,
                    new Byeong(Team.HAN, targetPoint)
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isFalse();
        }

        @Test
        @DisplayName("한나라의 경우 북쪽으로 이동할 수 있다면 false를 반환한다.")
        void checkUpMovableWithHan() {
            Byeong byeong = new Byeong(Team.HAN, new Point(6, 6));
            Point targetPoint = new Point(5, 6);
            Board board = new Board(List.of(
                    byeong,
                    new Byeong(Team.CHO, targetPoint)
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isFalse();
        }

        @Test
        @DisplayName("한나라의 경우 남쪽으로 이동할 수 있다면 true를 반환한다.")
        void checkDownMovableWithHan() {
            Byeong byeong = new Byeong(Team.HAN, new Point(6, 6));
            Point targetPoint = new Point(7, 6);
            Board board = new Board(List.of(
                    byeong,
                    new Byeong(Team.CHO, targetPoint)
            ));

            assertThat(byeong.canMove(targetPoint, board.findHurdles())).isTrue();
        }
    }
}
