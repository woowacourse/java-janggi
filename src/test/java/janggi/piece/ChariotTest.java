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

class ChariotTest {

    @DisplayName("차는 상하좌우로 움직이지 않은 경우 예외가 발생한다.")
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
        Piece piece = new Chariot(camp, board);

        Position origin = new Position(3, 3);
        Position target = new Position(targetX, targetY);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThatCode(() -> piece.validateMove(movement))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("차는 수평 혹은 수직으로만 움직여야 합니다.");
    }

    @DisplayName("차는 상하좌우 무제한으로 움직일 수 있다.")
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
        Piece piece = new Chariot(camp, board);

        Position origin = new Position(3, 3);
        Position target = new Position(targetX, targetY);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThatCode(() -> piece.validateMove(movement))
                .doesNotThrowAnyException();
    }

    @DisplayName("차는 상하좌우로 움직일 때 기물에 막힌 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenBlocked() {
        // given
        Board board = new Board();
        Piece piece = new Chariot(Camp.CHO, board);

        Position origin = new Position(3, 3);
        Position target = new Position(3, 7);
        Movement movement = new Movement(origin, target);

        board.placePiece(origin, piece);
        board.placePiece(new Position(3, 5), new Soldier(Camp.CHO, board));

        // when & then
        assertThatCode(() -> piece.validateMove(movement))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("차는 기물을 넘어 이동할 수 없습니다.");
    }
}
