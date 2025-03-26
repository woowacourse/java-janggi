package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.Board;
import janggi.exception.ErrorException;
import janggi.position.Movement;
import janggi.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HorseTest {

    @DisplayName("마는 직선으로 한 칸, 대각선으로 한 칸 움직이지 않은 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,5,7",
            "HAN,7,5",
            "HAN,3,5",
            "HAN,7,7",
            "HAN,6,8",
    })
    void shouldThrowException_WhenInvalidMove(Camp camp, int targetX, int targetY) {
        // given
        Board board = new Board();
        Piece piece = new Horse(camp, board);

        Position origin = new Position(5, 5);
        Position target = new Position(targetX, targetY);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThatCode(() -> piece.validateMove(movement))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("마는 직선으로 한 칸, 대각선으로 한 칸 움직여야 합니다.");
    }

    @DisplayName("마는 직선으로 한 칸, 대각선으로 한 칸 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,6,7",
            "HAN,7,6",
            "HAN,7,4",
            "HAN,6,3",
            "HAN,4,7",
            "HAN,3,6",
            "HAN,3,4",
            "HAN,4,3,",
    })
    void validateMoveTest(Camp camp, int targetX, int targetY) {
        // given
        Board board = new Board();
        Piece piece = new Horse(camp, board);

        Position origin = new Position(5, 5);
        Position target = new Position(targetX, targetY);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThatCode(() -> piece.validateMove(movement))
                .doesNotThrowAnyException();
    }

    @DisplayName("마는 직선으로 한 칸, 대각선으로 한 칸 움직일 때 기물에 막힌 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,6,7",
            "HAN,7,4",
            "HAN,6,3",
            "HAN,3,4",
    })
    void shouldThrowException_WhenBlocked(Camp camp, int targetX, int targetY) {
        // given
        Board board = new Board();
        Piece piece = new Horse(camp, board);

        Position origin = new Position(5, 5);
        Position target = new Position(targetX, targetY);
        Movement movement = new Movement(origin, target);

        board.placePiece(new Position(5, 6), new Soldier(Camp.CHO, board));
        board.placePiece(new Position(6, 5), new Soldier(Camp.CHO, board));
        board.placePiece(new Position(4, 5), new Soldier(Camp.CHO, board));
        board.placePiece(new Position(5, 4), new Soldier(Camp.CHO, board));

        // when & then
        assertThatCode(() -> piece.validateMove(movement))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("마는 기물을 넘어서 이동할 수 없습니다.");
    }
}
