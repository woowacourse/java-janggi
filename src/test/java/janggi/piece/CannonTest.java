package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.Board;
import janggi.exception.ErrorException;
import janggi.position.Movement;
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
    void shouldThrowException_WhenInvalidMove(Camp camp, int targetX, int targetY) {
        // given
        Board board = new Board();
        Piece piece = new Cannon(camp, board);

        Position origin = new Position(3, 3);
        Position target = new Position(targetX, targetY);
        Movement movement = new Movement(origin, target);

        board.placePiece(origin, piece);
        board.placePiece(new Position(3, 5), new Soldier(camp, board));

        // when & then
        assertThatCode(() -> piece.validateMove(movement))
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
    void validateMoveTest(Camp camp, int targetX, int targetY) {
        // given
        Board board = new Board();
        Piece piece = new Cannon(camp, board);

        Position origin = new Position(3, 3);
        Position target = new Position(targetX, targetY);
        Movement movement = new Movement(origin, target);

        board.placePiece(origin, piece);
        board.placePiece(new Position(3, 4), new Soldier(camp, board));
        board.placePiece(new Position(2, 3), new Soldier(camp, board));
        board.placePiece(new Position(4, 3), new Soldier(camp, board));
        board.placePiece(new Position(3, 2), new Soldier(camp, board));

        // when & then
        assertThatCode(() -> piece.validateMove(movement))
                .doesNotThrowAnyException();
    }

    @DisplayName("포는 다른 포를 잡을 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCatchOtherCannon() {
        // given
        Board board = new Board();
        Piece choPiece = new Cannon(Camp.CHO, board);
        Piece hanPiece = new Cannon(Camp.HAN, board);

        // when & then
        assertThatCode(() -> choPiece.validateCatch(hanPiece))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("포는 포를 잡을 수 없습니다.");
    }

    @DisplayName("포의 경로에 기물이 없는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenJumpOverZeroPiece() {
        // given
        Board board = new Board();
        Piece piece = new Cannon(Camp.CHO, board);

        Position origin = new Position(1, 1);
        Position target = new Position(1, 3);
        Movement movement = new Movement(origin, target);

        board.placePiece(origin, piece);

        // when & then
        assertThatCode(() -> piece.validateMove(movement))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("포는 정확히 하나의 기물만 넘을 수 있습니다. 넘은 기물 수: 0");
    }

    @DisplayName("포의 경로에 기물이 2개인 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenJumpOverTwoPiece() {
        // given
        Board board = new Board();
        Piece piece = new Cannon(Camp.CHO, board);

        Position origin = new Position(1, 1);
        Position target = new Position(1, 5);
        Movement movement = new Movement(origin, target);

        board.placePiece(origin, piece);
        board.placePiece(new Position(1, 2), new Soldier(Camp.CHO, board));
        board.placePiece(new Position(1, 3), new Soldier(Camp.CHO, board));

        // when & then
        assertThatCode(() -> piece.validateMove(movement))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("포는 정확히 하나의 기물만 넘을 수 있습니다. 넘은 기물 수: 2");
    }

    @DisplayName("포가 포를 넘어갈 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCannonJumpOverCannon() {
        // given
        Board board = new Board();
        Piece piece = new Cannon(Camp.CHO, board);

        Position origin = new Position(1, 1);
        Position target = new Position(1, 3);
        Movement movement = new Movement(origin, target);

        board.placePiece(origin, piece);
        board.placePiece(new Position(1, 2), new Cannon(Camp.HAN, board));

        // when & then
        assertThatCode(() -> piece.validateMove(movement))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("포는 포를 넘을 수 없습니다.");
    }
}
