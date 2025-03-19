package janggi;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.piece.Byeong;
import janggi.piece.Cha;
import janggi.piece.Gung;
import janggi.point.Point;
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

            assertThat(board.getPieces()).hasSize(32);
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
}
