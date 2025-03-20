package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.Camp;
import janggi.Point;
import janggi.board.Board;
import org.junit.jupiter.api.DisplayName;
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
        Piece piece = new Soldier(camp, board);
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
        board.placePiece(new Point(3, 4), new Soldier(camp, board));
        board.placePiece(new Point(2, 3), new Soldier(camp, board));
        board.placePiece(new Point(4, 3), new Soldier(camp, board));
        board.placePiece(new Point(3, 2), new Soldier(camp, board));
        Point fromPoint = new Point(3, 3);
        Cannon cannon = new Cannon(camp, board);
        board.placePiece(fromPoint, cannon);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> cannon.validateMove(fromPoint, toPoint))
                .doesNotThrowAnyException();
    }

    @DisplayName("포는 다른 포를 잡을 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCatchOtherCannon() {
        // given
        Board board = new Board();
        Cannon chuCannon = new Cannon(Camp.CHU, board);
        Cannon hanCannon = new Cannon(Camp.HAN, board);

        // when & then
        assertThatCode(() -> chuCannon.validateCatch(hanCannon))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 잡을 수 없습니다.");
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
        board.placePiece(new Point(1, 2), new Soldier(Camp.CHU, board));
        board.placePiece(new Point(1, 3), new Soldier(Camp.CHU, board));

        // when & then
        assertThatCode(() -> cannon.validateMove(fromPoint, toPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 정확히 하나의 기물만 넘을 수 있습니다. 넘은 기물 수: 2");
    }
}
