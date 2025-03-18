package janggi;

import static org.assertj.core.api.Assertions.assertThat;

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
