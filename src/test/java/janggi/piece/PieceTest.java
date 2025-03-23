package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.view.PieceSymbol;
import janggi.board.point.Point;
import janggi.board.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PieceTest {

    @DisplayName("기물은 같은 진영의 기물을 잡으려고 하는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCatchSameCamp() {
        // given
        Board board = new Board();
        TestPiece testPiece = new TestPiece(Camp.CHU, board);

        // when & then
        assertThatCode(() -> testPiece.validateCatch(new TestPiece(Camp.CHU, board)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("같은 진영의 기물을 잡을 수 없습니다.");
    }

    @DisplayName("같은 진영의 기물만 선택할 수 있다.")
    @Test
    void validateSelectTest() {
        // given
        Board board = new Board();
        TestPiece testPiece = new TestPiece(Camp.CHU, board);

        // when & then
        assertThatCode(() -> testPiece.validateSelect(Camp.CHU))
                .doesNotThrowAnyException();
    }

    @DisplayName("다른 진영이 기물을 선택하려고 하는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenSelectOtherCamp() {
        // given
        Board board = new Board();
        TestPiece testPiece = new TestPiece(Camp.CHU, board);

        // when & then
        assertThatCode(() -> testPiece.validateSelect(Camp.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("다른 진영의 기물을 선택할 수 없습니다.");
    }

    @DisplayName("기물이 어떤 방향을 바라보고 있는지(어디에 놓였는지) 확인할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "CHU, true",
            "HAN, false"
    })
    void isPlacedAtBottomTest(Camp camp, boolean expected) {
        // given
        Board board = new Board();
        TestPiece testPiece = new TestPiece(camp, board);

        // when
        boolean isPlacedAtBottom = testPiece.isPlacedAtBottom();

        // then
        assertThat(isPlacedAtBottom)
                .isSameAs(expected);
    }

    static class TestPiece extends Piece {

        public TestPiece(Camp camp, Board board) {
            super(camp, board);
        }

        @Override
        public void validateMove(Point fromPoint, Point toPoint) {
        }

        @Override
        public PieceSymbol getPieceSymbol() {
            return PieceSymbol.GENERAL;
        }
    }
}
