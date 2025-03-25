package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.Board;
import janggi.board.point.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTest {

    @DisplayName("기물을 잡을 수 없는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenValidateCatch() {
        // given
        Board board = new Board();
        Piece neverCapturePiece = new NeverCaptureTestPiece(Camp.CHU, board);

        // when & then
        assertThatCode(() -> neverCapturePiece.validateCatch(new NeverCaptureTestPiece(Camp.CHU, board)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 기물을 잡을 수 없습니다.");
    }

    @DisplayName("같은 진영의 기물만 선택할 수 있다.")
    @Test
    void validateSelectTest() {
        // given
        Board board = new Board();
        Piece testPiece = new AlwaysCaptureTestPiece(Camp.CHU, board);

        // when & then
        assertThatCode(() -> testPiece.validateSelect(Camp.CHU))
                .doesNotThrowAnyException();
    }

    @DisplayName("다른 진영이 기물을 선택하려고 하는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenSelectOtherCamp() {
        // given
        Board board = new Board();
        Piece testPiece = new AlwaysCaptureTestPiece(Camp.CHU, board);

        // when & then
        assertThatCode(() -> testPiece.validateSelect(Camp.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("다른 진영의 기물을 선택할 수 없습니다.");
    }

    static class AlwaysCaptureTestPiece extends Piece {

        public AlwaysCaptureTestPiece(Camp camp, Board board) {
            super(camp, board);
        }

        @Override
        public void validateMove(Point fromPoint, Point toPoint) {
        }

        @Override
        protected boolean canCapture(Piece otherPiece) {
            return true;
        }

        @Override
        public PieceSymbol getPieceSymbol() {
            return PieceSymbol.GENERAL;
        }
    }

    static class NeverCaptureTestPiece extends Piece {

        public NeverCaptureTestPiece(Camp camp, Board board) {
            super(camp, board);
        }

        @Override
        public void validateMove(Point fromPoint, Point toPoint) {
        }

        @Override
        protected boolean canCapture(Piece otherPiece) {
            return false;
        }

        @Override
        public PieceSymbol getPieceSymbol() {
            return PieceSymbol.GENERAL;
        }
    }
}
