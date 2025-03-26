package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.BoardFactory;
import janggi.board.Board;
import janggi.exception.ErrorException;
import janggi.position.Movement;
import janggi.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoldierTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = BoardFactory.emptyBoard(Camp.CHO);
    }

    @DisplayName("군인이 뒤로 움직일 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,2",
            "CHO,0"
    })
    void shouldThrowException_WhenBackwardMove(Camp camp, int targetY) {
        // given
        Piece piece = new Soldier(camp, board);

        Position origin = new Position(0, 1);
        Position target = new Position(0, targetY);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThatCode(() -> piece.validateMove(movement))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("군인은 뒤로 갈 수 없습니다.");
    }

    @DisplayName("군인은 앞 또는 양 옆으로 한 칸만 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,0,1",
            "HAN,2,1",
            "HAN,1,0",
            "CHO,0,1",
            "CHO,2,1",
            "CHO,1,2",
    })
    void validMoveTest(Camp camp, int targetX, int targetY) {
        // given
        Piece piece = new Soldier(camp, board);

        Position origin = new Position(1, 1);
        Position target = new Position(targetX, targetY);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThatCode(() -> piece.validateMove(movement))
                .doesNotThrowAnyException();
    }

    @DisplayName("군인은 앞 또는 양 옆으로 한 칸만 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,3,1",
            "CHO,0,3"
    })
    void shouldThrowException_WhenInvalidMove(Camp camp, int targetX, int targetY) {
        // given
        Piece piece = new Soldier(camp, board);

        Position origin = new Position(1, 1);
        Position target = new Position(targetX, targetY);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThatCode(() -> piece.validateMove(movement))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("군인은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
    }

    @DisplayName("같은 진영의 기물을 잡을 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCatchSameCamp() {
        // given
        Piece piece = new Soldier(Camp.HAN, board);
        Piece otherPiece = new Soldier(Camp.HAN, board);

        // when & then
        assertThatCode(() -> piece.validateCatch(otherPiece))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("같은 진영의 기물을 잡을 수 없습니다.");
    }
}
