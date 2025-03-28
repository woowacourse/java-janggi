package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.point.Point;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTest {

    @DisplayName("기물을 잡을 수 없는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenValidateCatch() {
        // given
        Piece neverCapturePiece = new NeverCaptureTestPiece(Camp.CHU);

        // when & then
        assertThatCode(() -> neverCapturePiece.validateCatch(new NeverCaptureTestPiece(Camp.CHU)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 기물을 잡을 수 없습니다.");
    }

    @DisplayName("같은 진영의 기물만 선택할 수 있다.")
    @Test
    void validateSelectTest() {
        // given
        Piece testPiece = new AlwaysCaptureTestPiece(Camp.CHU);

        // when & then
        assertThatCode(() -> testPiece.validateSelect(Camp.CHU))
                .doesNotThrowAnyException();
    }

    @DisplayName("다른 진영이 기물을 선택하려고 하는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenSelectOtherCamp() {
        // given
        Piece testPiece = new AlwaysCaptureTestPiece(Camp.CHU);

        // when & then
        assertThatCode(() -> testPiece.validateSelect(Camp.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("다른 진영의 기물을 선택할 수 없습니다.");
    }

    static class AlwaysCaptureTestPiece extends Piece {

        public AlwaysCaptureTestPiece(Camp camp) {
            super(camp);
        }

        @Override
        public Set<Point> findRoute(Point fromPoint, Point toPoint) {
            return Set.of();
        }

        @Override
        public void validateMove(Point fromPoint, Point toPoint, Set<Piece> piecesOnRoute) {

        }

        @Override
        protected boolean canCapture(Piece otherPiece) {
            return true;
        }

        @Override
        public PieceSymbol getPieceSymbol() {
            return PieceSymbol.GENERAL;
        }

        @Override
        public int getPoint() {
            return 0;
        }
    }

    static class NeverCaptureTestPiece extends Piece {

        public NeverCaptureTestPiece(Camp camp) {
            super(camp);
        }

        @Override
        public Set<Point> findRoute(Point fromPoint, Point toPoint) {
            return Set.of();
        }

        @Override
        public void validateMove(Point fromPoint, Point toPoint, Set<Piece> piecesOnRoute) {
        }

        @Override
        protected boolean canCapture(Piece otherPiece) {
            return false;
        }

        @Override
        public PieceSymbol getPieceSymbol() {
            return PieceSymbol.GENERAL;
        }

        @Override
        public int getPoint() {
            return 0;
        }
    }
}
