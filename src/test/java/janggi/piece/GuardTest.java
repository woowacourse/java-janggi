package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.BoardFactory;
import janggi.domain.board.Board;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Piece;
import janggi.exception.ErrorException;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GuardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = BoardFactory.emptyBoard(Camp.CHO);
    }

    @DisplayName("사는 궁성 안에서 이동하지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "CHO,3,0,2,0",
            "HAN,3,9,2,9",
    })
    void shouldThrowException_WhenInvalidPalace(Camp camp, int originX, int originY, int targetX, int targetY) {
        // given
        Piece piece = new Guard(camp);

        Position origin = Position.of(originX, originY);
        Position target = Position.of(targetX, targetY);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThatCode(() -> piece.validateMove(movement, board))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("궁성 안에서 이동해야 합니다.");
    }

    @DisplayName("사는 상하좌우 또는 대각선으로 한 칸 움직이지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "CHO,3,0,5,2",
            "CHO,3,0,3,2",
            "CHO,3,0,5,0",
            "HAN,3,7,5,9",
            "HAN,3,7,3,9",
            "HAN,3,7,5,7"

    })
    void shouldThrowException_WhenInvalidMove(Camp camp, int originX, int originY, int targetX, int targetY) {
        // given
        Piece piece = new Guard(camp);

        Position origin = Position.of(originX, originY);
        Position target = Position.of(targetX, targetY);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThatCode(() -> piece.validateMove(movement, board))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("사는 상하좌우 또는 대각선으로 한 칸 움직여야 합니다.");
    }
}
