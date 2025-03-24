package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.Board;
import janggi.exception.ErrorException;
import janggi.position.Position;
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
        board.placePiece(new Position(3, 5), piece);
        Position fromPosition = new Position(3, 3);
        Cannon cannon = new Cannon(camp, board);
        board.placePiece(fromPosition, cannon);
        Position toPosition = new Position(toX, toY);

        // when & then
        assertThatCode(() -> cannon.validateMove(fromPosition, toPosition))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("포는 수평 혹은 수직으로만 움직여야 합니다.");
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
        board.placePiece(new Position(3, 4), new Soldier(camp, board));
        board.placePiece(new Position(2, 3), new Soldier(camp, board));
        board.placePiece(new Position(4, 3), new Soldier(camp, board));
        board.placePiece(new Position(3, 2), new Soldier(camp, board));
        Position fromPosition = new Position(3, 3);
        Cannon cannon = new Cannon(camp, board);
        board.placePiece(fromPosition, cannon);
        Position toPosition = new Position(toX, toY);

        // when & then
        assertThatCode(() -> cannon.validateMove(fromPosition, toPosition))
                .doesNotThrowAnyException();
    }

    @DisplayName("포는 다른 포를 잡을 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCatchOtherCannon() {
        // given
        Board board = new Board();
        Cannon chuCannon = new Cannon(Camp.CHO, board);
        Cannon hanCannon = new Cannon(Camp.HAN, board);

        // when & then
        assertThatCode(() -> chuCannon.validateCatch(hanCannon))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("포는 포를 잡을 수 없습니다.");
    }

    @DisplayName("포의 경로에 기물이 없는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenJumpOverZeroPiece() {
        // given
        Board board = new Board();
        Cannon cannon = new Cannon(Camp.CHO, board);
        Position fromPosition = new Position(1, 1);
        Position toPosition = new Position(1, 3);
        board.placePiece(fromPosition, cannon);

        // when & then
        assertThatCode(() -> cannon.validateMove(fromPosition, toPosition))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("포는 정확히 하나의 기물만 넘을 수 있습니다. 넘은 기물 수: 0");
    }

    @DisplayName("포의 경로에 기물이 2개인 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenJumpOverTwoPiece() {
        // given
        Board board = new Board();
        Cannon cannon = new Cannon(Camp.CHO, board);
        Position fromPosition = new Position(1, 1);
        Position toPosition = new Position(1, 5);
        board.placePiece(fromPosition, cannon);
        board.placePiece(new Position(1, 2), new Soldier(Camp.CHO, board));
        board.placePiece(new Position(1, 3), new Soldier(Camp.CHO, board));

        // when & then
        assertThatCode(() -> cannon.validateMove(fromPosition, toPosition))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("포는 정확히 하나의 기물만 넘을 수 있습니다. 넘은 기물 수: 2");
    }

    @DisplayName("포가 포를 넘어갈 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCannonJumpOverCannon() {
        // given
        Board board = new Board();
        Cannon cannon = new Cannon(Camp.CHO, board);
        Position fromPosition = new Position(1, 1);
        Position toPosition = new Position(1, 3);
        board.placePiece(fromPosition, cannon);
        board.placePiece(new Position(1, 2), new Cannon(Camp.HAN, board));

        // when & then
        assertThatCode(() -> cannon.validateMove(fromPosition, toPosition))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("포는 포를 넘을 수 없습니다.");
    }
}
