package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.Board;
import janggi.board.point.Point;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChariotTest {

    @DisplayName("차는 상하좌우로 움직이지 않은 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,2,2",
            "HAN,4,2",
            "HAN,2,4",
            "HAN,4,4",
    })
    void shouldThrowException_WhenInvalidMove(Camp camp, int toX, int toY) {
        // given
        Chariot chariot = new Chariot(camp);
        Point fromPoint = new Point(3, 3);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> chariot.validateMove(fromPoint, toPoint, Set.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차는 수평 혹은 수직으로만 움직여야 합니다.");
    }

    @DisplayName("차는 상하좌우 무제한으로 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,3,0",
            "HAN,3,5",
            "HAN,0,3",
            "HAN,5,3",
    })
    void validateMoveTest(Camp camp, int toX, int toY) {
        // given
        Chariot chariot = new Chariot(camp);
        Point fromPoint = new Point(3, 3);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> chariot.validateMove(fromPoint, toPoint, Set.of()))
                .doesNotThrowAnyException();
    }

    @DisplayName("차는 상하좌우로 움직일 때 기물에 막힌 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "3, 4, 3, 5",
            "3, 2, 3, 1",
            "2, 3, 1, 3",
            "4, 3, 5, 3",
    })
    void shouldThrowException_WhenBlocked(int obstacleX, int obstacleY, int toX, int toY) {
        // given
        Board board = new Board();
        board.placePiece(new Point(obstacleX, obstacleY), new SoldierJol());
        Chariot chariot = new Chariot(Camp.CHU);
        Point fromPoint = new Point(3, 3);
        board.placePiece(fromPoint, chariot);
        Point toPoint = new Point(toX, toY);
        Set<Piece> piecesOnRoute = board.getPiecesByPoint(chariot.findRoute(fromPoint, toPoint));

        // when & then
        assertThatCode(() -> chariot.validateMove(fromPoint, toPoint, piecesOnRoute))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차는 기물을 넘어 이동할 수 없습니다.");
    }

    @DisplayName("차는 같은 진영의 기물을 잡을 수 없다.")
    @ParameterizedTest
    @CsvSource({
            "CHU, false",
            "HAN, true"
    })
    void canCaptureTest(Camp camp, boolean expected) {
        // given
        Chariot chariot = new Chariot(camp);

        // when
        boolean canCapture = chariot.canCapture(new SoldierJol());

        // then
        assertThat(canCapture)
                .isSameAs(expected);
    }

    @DisplayName("차는 같은 진영의 기물을 잡을 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCatchSameCamp() {
        // given
        Chariot chariot = new Chariot(Camp.CHU);

        // when & then
        assertThatCode(() -> chariot.validateCatch(new SoldierJol()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 기물을 잡을 수 없습니다.");
    }

    @DisplayName("특정 진영이 선택할 수 없는 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "CHU, HAN",
            "HAN, CHU",
    })
    void shouldThrowException_WhenSelectOtherCampPiece(Camp camp, Camp otherCamp) {
        // given
        Chariot chariot = new Chariot(otherCamp);

        // when & then
        assertThatCode(() -> chariot.validateSelect(camp))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("다른 진영의 기물을 선택할 수 없습니다.");
    }

    @DisplayName("자신의 기물 형태를 반환한다.")
    @Test
    void getPieceSymbolTest() {
        // given
        Chariot chariot = new Chariot(Camp.CHU);

        // when
        PieceSymbol pieceSymbol = chariot.getPieceSymbol();

        // then
        assertThat(pieceSymbol)
                .isSameAs(PieceSymbol.CHARIOT);
    }

    @DisplayName("자신의 점수를 반환한다.")
    @Test
    void getPointTest() {
        // given
        Chariot chariot = new Chariot(Camp.CHU);

        // when
        int point = chariot.getPoint();

        // then
        assertThat(point)
                .isEqualTo(13);
    }

    @Nested
    class WithPalaceTest {

        @DisplayName("차는 궁 내부에서 허용된 대각선으로 직진 가능하다.")
        @ParameterizedTest
        @CsvSource({
                "3, 0, 5, 2",
                "5, 2, 3, 0",
                "5, 0, 3, 2",
                "3, 2, 5, 0",
                "4, 1, 3, 0",
                "4, 1, 5, 2",
                "4, 1, 3, 2",
                "4, 1, 5, 0",
        })
        void isDiagonalPalaceMoveAllowedTest(int fromX, int fromY, int toX, int toY) {
            // given
            Board board = new Board();
            Chariot chariot = new Chariot(Camp.CHU);
            Point fromPoint = new Point(fromX, fromY);
            Point toPoint = new Point(toX, toY);
            board.placePiece(fromPoint, chariot);
            Set<Piece> piecesOnRoute = board.getPiecesByPoint(chariot.findRoute(fromPoint, toPoint));

            // when & then
            assertThatCode(() -> chariot.validateMove(fromPoint, toPoint, piecesOnRoute))
                    .doesNotThrowAnyException();
        }

        @DisplayName("차가 대각선으로 직진하려고 할 때, 허용되지 않은(대각선 경로가 없는) 경로인 경우 예외가 발생한다.")
        @ParameterizedTest
        @CsvSource({
                "3, 1, 4, 0",
                "4, 0, 5, 1",
                "5, 1, 4, 2",
                "4, 2, 3, 1",
        })
        void shouldThrowException_WhenDiagonalMoveOutsidePalace(int fromX, int fromY, int toX, int toY) {
            // given
            Board board = new Board();
            Chariot chariot = new Chariot(Camp.CHU);
            Point fromPoint = new Point(fromX, fromY);
            Point toPoint = new Point(toX, toY);
            board.placePiece(fromPoint, chariot);
            Set<Piece> piecesOnRoute = board.getPiecesByPoint(chariot.findRoute(fromPoint, toPoint));

            // when & then
            assertThatCode(() -> chariot.validateMove(fromPoint, toPoint, piecesOnRoute))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("차가 대각선으로 이동하려면, 허용된 지점에서만 가능합니다.");
        }

        @DisplayName("차는 궁 내부에서 대각선으로 이동할 때 기물에 막힌 경우 예외가 발생한다.")
        @Test
        void shouldThrowException_WhenBlockedInsidePalace() {
            // given
            Board board = new Board();
            Chariot chariot = new Chariot(Camp.CHU);
            Point fromPoint = new Point(3, 0);
            Point toPoint = new Point(5, 2);
            board.placePiece(fromPoint, chariot);
            board.placePiece(new Point(4, 1), new SoldierJol());
            Set<Piece> piecesOnRoute = board.getPiecesByPoint(chariot.findRoute(fromPoint, toPoint));

            // when & then
            assertThatCode(() -> chariot.validateMove(fromPoint, toPoint, piecesOnRoute))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("차는 기물을 넘어 이동할 수 없습니다.");
        }

        @DisplayName("차는 궁 내부에서도 상하좌우로 무제한으로 움직일 수 있다.")
        @ParameterizedTest
        @CsvSource({
                "3, 0, 3, 5",
                "5, 2, 5, 7",
                "3, 2, 3, 7"
        })
        void validateMoveTest(int fromX, int fromY, int toX, int toY) {
            // given
            Board board = new Board();
            Chariot chariot = new Chariot(Camp.CHU);
            Point fromPoint = new Point(fromX, fromY);
            Point toPoint = new Point(toX, toY);
            board.placePiece(fromPoint, chariot);
            Set<Piece> piecesOnRoute = board.getPiecesByPoint(chariot.findRoute(fromPoint, toPoint));

            // when & then
            assertThatCode(() -> chariot.validateMove(fromPoint, toPoint, piecesOnRoute))
                    .doesNotThrowAnyException();
        }
    }
}
