package janggi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.piece.Byeong;
import janggi.piece.Cha;
import janggi.piece.Gung;
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
            List<Movable> pieces = List.of(new Gung(TeamColor.BLUE, point));
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
                    new Cha(TeamColor.BLUE, new Point(5, 4)),
                    new Byeong(TeamColor.RED, new Point(3, 4))
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
                    new Cha(TeamColor.BLUE, new Point(5, 4))
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
                    new Cha(TeamColor.BLUE, new Point(5, 4)),
                    new Byeong(TeamColor.RED, new Point(2, 4))
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
                    new Cha(TeamColor.BLUE, new Point(5, 4)),
                    new Byeong(TeamColor.BLUE, new Point(2, 4))
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
                    new Po(TeamColor.BLUE, new Point(5, 4)),
                    new Byeong(TeamColor.BLUE, new Point(3, 4)),
                    new Po(TeamColor.BLUE, new Point(2, 4))
            );
            Board board = new Board(pieces);

            Point startPoint = new Point(5, 4);
            List<Point> route = List.of(new Point(4, 4), new Point(3, 4), new Point(2, 4));

            assertThat(board.checkHurdles(startPoint, route)).isTrue();
        }
    }

    @Nested
    @DisplayName("장기 판에 상하좌우 경로 테스트")
    class BoardRouteTest {

        @Test
        @DisplayName("장기 판에서 특정 좌표의 수직 경로에 있는 기물을 필터링할 수 있다.")
        void filterVerticalRoute() {
            Board board = Board.init();

            List<Movable> pieces = board.findPieceOnVerticalRoute(new Point(5, 4));

            assertThat(pieces).hasSize(4);
        }

        @Test
        @DisplayName("장기 판에서 특정 좌표의 수평 경로에 있는 기물을 필터링할 수 있다.")
        void filterHorizontalRoute() {
            Board board = Board.init();

            List<Movable> pieces = board.findPieceOnHorizontalRoute(new Point(5, 4));

            assertThat(pieces).hasSize(0);
        }
    }

    @Nested
    @DisplayName("기물 이동 테스트")
    class MoveTest {

        @Test
        @DisplayName("기물의 위치를 특정 위치로 바꿀 수 있다.")
        void updatePoint() {
            Point beforePoint = new Point(6, 4);
            Byeong byeong = new Byeong(TeamColor.BLUE, beforePoint);
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
            Byeong byeong1 = new Byeong(TeamColor.BLUE, beforePoint);

            Point afterPoint = new Point(5, 4);
            Byeong byeong2 = new Byeong(TeamColor.RED, afterPoint);

            List<Movable> pieces = new ArrayList<>(List.of(byeong1, byeong2));
            Board board = new Board(pieces);

            board.move(beforePoint, afterPoint);

            assertThat(board.getRunningPieces()).hasSize(1);
        }
    }
}
