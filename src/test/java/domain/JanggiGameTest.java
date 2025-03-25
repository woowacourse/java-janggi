package domain;

import domain.board.Point;
import domain.pieces.Piece;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JanggiGameTest {
    @Nested
    @DisplayName("보드를 생성할 때")
    class TestGenerateBoard {
        @Test
        @DisplayName("90개의 포인트를 갖는다")
        void test_contains90Points() {
            // given
            JanggiGame janggiGame = new JanggiGame();

            // when
            Map<Point, Piece> board = janggiGame.getBoard();

            // then
            assertThat(board).hasSize(90);
        }

        @Test
        @DisplayName("row 는 최대 8, column 은 최대 9를 갖는다")
        void test_containsOnlyRowUnder8() {
            // given
            JanggiGame janggiGame = new JanggiGame();

            // when
            Map<Point, Piece> board = janggiGame.getBoard();

            // then
            assertThat(board.keySet()).allSatisfy(point -> {
                assertThat(point.row()).isBetween(0, 9);
                assertThat(point.column()).isBetween(0, 8);
            });
        }
    }

    @Nested
    class TestMove {
        @Test
        @DisplayName("시작 지점에서부터 말을 이동시킨다")
        void test_move() {
            // given
            JanggiGame janggiGame = new JanggiGame();
            Point startPoint = new Point(0, 0);
            Point arrivalPoint = new Point(1, 0);

            Piece originalPieceAtStartPoint = janggiGame.getBoard().get(startPoint);

            // when
            janggiGame.move(startPoint, arrivalPoint, true);

            // then
            Piece pieceAtArrivalPoint = janggiGame.getBoard().get(arrivalPoint);
            assertThat(originalPieceAtStartPoint).isEqualTo(pieceAtArrivalPoint);
        }
    }
}
