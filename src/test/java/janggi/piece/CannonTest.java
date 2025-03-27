package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.Board;
import janggi.board.point.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CannonTest {

    @DisplayName("포는 수평 혹은 수직으로 움직이지 않은 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,2,2",
            "HAN,4,2",
            "HAN,2,4",
            "HAN,4,4",
    })
    void shouldThrowException_WhenInvalidMove(Camp camp, int toX, int toY) {
        // given
        Board board = new Board();
        Piece piece = new SoldierByeong(board);
        board.placePiece(new Point(3, 5), piece);
        Point fromPoint = new Point(3, 3);
        Cannon cannon = new Cannon(camp, board);
        board.placePiece(fromPoint, cannon);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> cannon.validateMove(fromPoint, toPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 수평 혹은 수직으로만 움직여야 합니다.");
    }

    @DisplayName("포는 수평 혹은 수직으로 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,3,0",
            "HAN,3,5",
            "HAN,0,3",
            "HAN,5,3",
    })
    void validateMoveTest(Camp camp, int toX, int toY) {
        // given
        Board board = new Board();
        board.placePiece(new Point(3, 4), new SoldierByeong(board));
        board.placePiece(new Point(2, 3), new SoldierByeong(board));
        board.placePiece(new Point(4, 3), new SoldierByeong(board));
        board.placePiece(new Point(3, 2), new SoldierByeong(board));
        Point fromPoint = new Point(3, 3);
        Cannon cannon = new Cannon(camp, board);
        board.placePiece(fromPoint, cannon);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> cannon.validateMove(fromPoint, toPoint))
                .doesNotThrowAnyException();
    }

    @DisplayName("포는 같은 진영의 기물을 잡을 수 없다.")
    @ParameterizedTest
    @CsvSource({
            "CHU, false",
            "HAN, true"
    })
    void canCaptureTest(Camp camp, boolean expected) {
        // given
        Board board = new Board();
        Cannon cannon = new Cannon(camp, board);

        // when
        boolean canCapture = cannon.canCapture(new SoldierJol(board));

        // then
        assertThat(canCapture)
                .isSameAs(expected);
    }

    @DisplayName("포는 다른 포를 잡을 수 없다.")
    @Test
    void canCaptureTest_WhenSameCannonPiece() {
        // given
        Board board = new Board();
        Cannon chuCannon = new Cannon(Camp.CHU, board);
        Cannon hanCannon = new Cannon(Camp.HAN, board);

        // when
        boolean canCapture = chuCannon.canCapture(hanCannon);

        // then
        assertThat(canCapture)
                .isFalse();
    }

    @DisplayName("포는 같은 진영의 기물을 잡을 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCatchSameCamp() {
        // given
        Board board = new Board();
        Cannon chuCannon = new Cannon(Camp.CHU, board);

        // when & then
        assertThatCode(() -> chuCannon.validateCatch(new SoldierJol(board)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 기물을 잡을 수 없습니다.");
    }

    @DisplayName("포가 다른 포를 잡을 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCaptureCannon() {
        // given
        Board board = new Board();
        Cannon chuCannon = new Cannon(Camp.CHU, board);

        // when & then
        assertThatCode(() -> chuCannon.validateCatch(new SoldierJol(board)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 기물을 잡을 수 없습니다.");
    }

    @DisplayName("포는 포가 아닌 다른 진영의 기물을 잡을 수 있다.")
    @Test
    void validateCatchTest() {
        // given
        Board board = new Board();
        Cannon chuCannon = new Cannon(Camp.CHU, board);
        SoldierByeong soldierByeong = new SoldierByeong(board);

        // when & then
        assertThatCode(() -> chuCannon.validateCatch(soldierByeong))
                .doesNotThrowAnyException();
    }

    @DisplayName("포의 경로에 기물이 없는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenJumpOverZeroPiece() {
        // given
        Board board = new Board();
        Cannon cannon = new Cannon(Camp.CHU, board);
        Point fromPoint = new Point(1, 1);
        Point toPoint = new Point(1, 3);
        board.placePiece(fromPoint, cannon);

        // when & then
        assertThatCode(() -> cannon.validateMove(fromPoint, toPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 정확히 하나의 기물만 넘을 수 있습니다. 넘은 기물 수: 0");
    }

    @DisplayName("포의 경로에 기물이 2개인 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenJumpOverTwoPiece() {
        // given
        Board board = new Board();
        Cannon cannon = new Cannon(Camp.CHU, board);
        Point fromPoint = new Point(1, 1);
        Point toPoint = new Point(1, 5);
        board.placePiece(fromPoint, cannon);
        board.placePiece(new Point(1, 2), new SoldierJol(board));
        board.placePiece(new Point(1, 3), new SoldierJol(board));

        // when & then
        assertThatCode(() -> cannon.validateMove(fromPoint, toPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 정확히 하나의 기물만 넘을 수 있습니다. 넘은 기물 수: 2");
    }

    @DisplayName("포가 포를 넘어갈 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCannonJumpOverCannon() {
        // given
        Board board = new Board();
        Cannon cannon = new Cannon(Camp.CHU, board);
        Point fromPoint = new Point(1, 1);
        Point toPoint = new Point(1, 3);
        board.placePiece(fromPoint, cannon);
        board.placePiece(new Point(1, 2), new Cannon(Camp.HAN, board));

        // when & then
        assertThatCode(() -> cannon.validateMove(fromPoint, toPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 넘을 수 없습니다.");
    }

    @DisplayName("자신의 기물 형태를 반환한다.")
    @Test
    void getPieceSymbolTest() {
        // given
        Board board = new Board();
        Cannon cannon = new Cannon(Camp.CHU, board);

        // when
        PieceSymbol pieceSymbol = cannon.getPieceSymbol();

        // then
        assertThat(pieceSymbol)
                .isSameAs(PieceSymbol.CANNON);
    }

    @DisplayName("자신의 점수를 반환한다.")
    @Test
    void getPointTest() {
        // given
        Board board = new Board();
        Cannon cannon = new Cannon(Camp.CHU, board);

        // when
        int point = cannon.getPoint();

        // then
        assertThat(point)
                .isEqualTo(7);
    }

    @Nested
    class WithPalaceTest {

        @DisplayName("포는 궁 내부에서 허용된 대각선으로 직진 가능하다.")
        @ParameterizedTest
        @CsvSource({
                "3, 0, 5, 2",
                "5, 2, 3, 0",
                "5, 0, 3, 2",
                "3, 2, 5, 0",
        })
        void isDiagonalPalaceMoveAllowedTest(int fromX, int fromY, int toX, int toY) {
            // given
            Board board = new Board();
            Cannon cannon = new Cannon(Camp.CHU, board);
            Point fromPoint = new Point(fromX, fromY);
            Point toPoint = new Point(toX, toY);
            board.placePiece(fromPoint, cannon);
            board.placePiece(new Point(4, 1), new SoldierJol(board));

            // when & then
            assertThatCode(() -> cannon.validateMove(fromPoint, toPoint))
                    .doesNotThrowAnyException();
        }

        @DisplayName("포가 대각선으로 직진하려고 할 때, 허용되지 않은(대각선 경로가 없는) 경로인 경우 예외가 발생한다.")
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
            Cannon cannon = new Cannon(Camp.CHU, board);
            Point fromPoint = new Point(fromX, fromY);
            Point toPoint = new Point(toX, toY);
            board.placePiece(fromPoint, cannon);

            // when & then
            assertThatCode(() -> cannon.validateMove(fromPoint, toPoint))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("포가 대각선으로 이동하려면, 허용된 지점에서만 가능합니다.");
        }

        @DisplayName("포는 궁 내부에서도 넘을 기물 조건만 충족된다면, 상하좌우로 무제한으로 움직일 수 있다.")
        @ParameterizedTest
        @CsvSource({
                "3, 0, 3, 6",
                "5, 1, 0, 1",
        })
        void validateMoveTest(int fromX, int fromY, int toX, int toY) {
            // given
            Board board = new Board();
            Cannon cannon = new Cannon(Camp.CHU, board);
            Point fromPoint = new Point(fromX, fromY);
            Point toPoint = new Point(toX, toY);
            board.placePiece(fromPoint, cannon);
            board.placePiece(new Point(3, 3), new SoldierJol(board));
            board.placePiece(new Point(2, 1), new SoldierJol(board));

            // when & then
            assertThatCode(() -> cannon.validateMove(fromPoint, toPoint))
                    .doesNotThrowAnyException();
        }
    }
}
