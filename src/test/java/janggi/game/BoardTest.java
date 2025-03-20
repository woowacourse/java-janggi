package janggi.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import janggi.piece.Byeong;
import janggi.piece.Cha;
import janggi.piece.Gung;
import janggi.piece.Movable;
import janggi.piece.Po;
import janggi.point.Point;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Nested
    @DisplayName("장기 판 초기화 테스트")
    class InitBoardTest {

        @Test
        @DisplayName("장기 말들을 가진 장기판을 생성할 수 있다.")
        void createBoard() {
            Board board = Board.init();

            assertThat(board.getRunningPieces()).hasSize(32);
        }
    }

    @Nested
    @DisplayName("기물 검색 테스트")
    class SearchTest {

        @Test
        @DisplayName("입력 좌표에 기물이 위치하는지 확인할 수 있다.")
        void searchByPoint() {
            Point point = new Point(8, 4);
            List<Movable> pieces = List.of(new Gung(Team.CHO, point));
            Board board = new Board(pieces);

            assertThat(board.findByPoint(point)).isEqualTo(pieces.getFirst());
        }
    }

    @Nested
    @DisplayName("기물 경로 장애물 테스트")
    class HurdleTest {

        @Test
        @DisplayName("기물 경로의 좌표들에 장애물이 있는지 확인할 수 있다.")
        void checkHurdlesOnRoute() {
            List<Movable> pieces = List.of(
                    new Cha(Team.CHO, new Point(5, 4)),
                    new Byeong(Team.HAN, new Point(3, 4))
            );
            Board board = new Board(pieces);

            Point startPoint = new Point(5, 4);
            List<Point> route = List.of(new Point(4, 4), new Point(3, 4), new Point(2, 4));

            assertThat(board.checkHurdles(startPoint, route)).isTrue();
        }

        @Test
        @DisplayName("기물 경로의 좌표들에 장애물이 있는지 확인할 수 있다.")
        void checkHurdlesNotOnRoute() {
            List<Movable> pieces = List.of(
                    new Cha(Team.CHO, new Point(5, 4))
            );
            Board board = new Board(pieces);

            Point startPoint = new Point(5, 4);
            List<Point> route = List.of(new Point(4, 4), new Point(3, 4), new Point(2, 4));

            assertThat(board.checkHurdles(startPoint, route)).isFalse();
        }

        @Test
        @DisplayName("기물 경로의 마지막 좌표에 다른 팀의 말이 있다면 장애물이 없다고 확인할 수 있다.")
        void checkNotHurdleRouteIsOtherTeam() {
            List<Movable> pieces = List.of(
                    new Cha(Team.CHO, new Point(5, 4)),
                    new Byeong(Team.HAN, new Point(2, 4))
            );
            Board board = new Board(pieces);

            Point startPoint = new Point(5, 4);
            List<Point> route = List.of(new Point(4, 4), new Point(3, 4), new Point(2, 4));

            assertThat(board.checkHurdles(startPoint, route)).isFalse();
        }

        @Test
        @DisplayName("기물 경로의 마지막 좌표에 같은 팀의 말이 있다면 장애물이 있다고 확인할 수 있다.")
        void checkIsHurdleRouteIsSameTeam() {
            List<Movable> pieces = List.of(
                    new Cha(Team.CHO, new Point(5, 4)),
                    new Byeong(Team.CHO, new Point(2, 4))
            );
            Board board = new Board(pieces);

            Point startPoint = new Point(5, 4);
            List<Point> route = List.of(new Point(4, 4), new Point(3, 4), new Point(2, 4));

            assertThat(board.checkHurdles(startPoint, route)).isTrue();
        }

        @Test
        @DisplayName("포 기물을 움직일 때 기물 경로의 마지막 좌표에 포가 있으면 장애물이 있다고 확인할 수 있다.")
        void checkIsHurdleRouteIsSamePo() {
            List<Movable> pieces = List.of(
                    new Po(Team.CHO, new Point(5, 4)),
                    new Byeong(Team.CHO, new Point(3, 4)),
                    new Po(Team.CHO, new Point(2, 4))
            );
            Board board = new Board(pieces);

            Point startPoint = new Point(5, 4);
            List<Point> route = List.of(new Point(4, 4), new Point(3, 4), new Point(2, 4));

            assertThat(board.checkHurdles(startPoint, route)).isTrue();
        }
    }

    @Nested
    @DisplayName("기물 이동 테스트")
    class MoveTest {

        @Test
        @DisplayName("기물의 위치를 특정 위치로 바꿀 수 있다.")
        void updatePoint() {
            Point beforePoint = new Point(6, 4);
            Byeong byeong = new Byeong(Team.CHO, beforePoint);
            List<Movable> pieces = new ArrayList<>(List.of(byeong));
            Board board = new Board(pieces);

            Point afterPoint = new Point(5, 4);
            board.move(beforePoint, afterPoint);

            assertAll(() -> {
                assertThat(board.findByPoint(afterPoint).getPoint()).isEqualTo(afterPoint);
                assertThatThrownBy(() -> board.findByPoint(beforePoint))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("해당 좌표에 기물이 존재하지 않습니다.");
            });
        }

        @Test
        @DisplayName("이동 하는 위치에 기물이 있으면 공격한다.")
        void attackPiece() {
            Point beforePoint = new Point(6, 4);
            Byeong byeong1 = new Byeong(Team.CHO, beforePoint);

            Point afterPoint = new Point(5, 4);
            Byeong byeong2 = new Byeong(Team.HAN, afterPoint);

            List<Movable> pieces = new ArrayList<>(List.of(byeong1, byeong2));
            Board board = new Board(pieces);

            board.move(beforePoint, afterPoint);

            assertThat(board.getRunningPieces()).hasSize(1);
        }
    }

    @Nested
    @DisplayName("포 이동 테스트")
    class PoMoveTest {

        @Test
        @DisplayName("좌로 이동할 수 있다면 true를 반환한다.")
        void checkLeftMovable() {
            Point beforePoint = new Point(6, 6);
            List<Movable> pieces = List.of(
                    new Byeong(Team.CHO, new Point(6, 4)),
                    new Byeong(Team.CHO, new Point(6, 2)),
                    new Po(Team.CHO, beforePoint)
            );
            Board board = new Board(new ArrayList<>(pieces));
            Point targetPoint = new Point(6, 3);

            assertDoesNotThrow(() -> board.move(beforePoint, targetPoint));
        }

        @Test
        @DisplayName("우로 이동할 수 있다면 true를 반환한다.")
        void checkRightMovable() {
            Point beforePoint = new Point(6, 2);
            List<Movable> pieces = List.of(
                    new Byeong(Team.CHO, new Point(6, 4)),
                    new Byeong(Team.CHO, new Point(6, 6)),
                    new Po(Team.CHO, beforePoint)
            );
            Board board = new Board(new ArrayList<>(pieces));
            Point targetPoint = new Point(6, 5);

            assertDoesNotThrow(() -> board.move(beforePoint, targetPoint));
        }

        @Test
        @DisplayName("상으로 이동할 수 있다면 true를 반환한다.")
        void checkUpMovable() {
            Point beforePoint = new Point(6, 4);
            List<Movable> pieces = List.of(
                    new Byeong(Team.HAN, new Point(3, 4)),
                    new Po(Team.CHO, beforePoint)
            );
            Board board = new Board(new ArrayList<>(pieces));
            Point targetPoint = new Point(2, 4);

            assertDoesNotThrow(() -> board.move(beforePoint, targetPoint));
        }

        @Test
        @DisplayName("하으로 이동할 수 있다면 true를 반환한다.")
        void checkDownMovable() {
            Point beforePoint = new Point(0, 7);
            List<Movable> pieces = List.of(
                    new Byeong(Team.HAN, new Point(2, 7)),
                    new Byeong(Team.HAN, new Point(7, 7)),
                    new Po(Team.CHO, beforePoint)
            );
            Board board = new Board(new ArrayList<>(pieces));
            Point targetPoint = new Point(5, 7);

            assertDoesNotThrow(() -> board.move(beforePoint, targetPoint));
        }
    }
}
