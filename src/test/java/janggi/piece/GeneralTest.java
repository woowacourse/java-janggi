package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.Board;
import janggi.exception.ErrorException;
import janggi.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GeneralTest {

    @DisplayName("왕은 궁성 안에서 이동하지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "CHO,3,0,2,0",
            "HAN,3,9,2,9",
    })
    void shouldThrowException_WhenInvalidPalace(Camp camp, int fromX, int fromY, int toX, int toY) {
        // given
        Board board = new Board();
        General general = new General(camp, board);
        Position fromPosition = new Position(fromX, fromY);
        Position toPosition = new Position(toX, toY);

        // when & then
        assertThatCode(() -> general.validateMove(fromPosition, toPosition))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("궁성 안에서 이동해야 합니다.");
    }

    @DisplayName("왕은 상하좌우 또는 대각선으로 한 칸 움직이지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "CHO,3,0,5,2",
            "CHO,3,0,3,2",
            "CHO,3,0,5,0",
            "HAN,3,7,5,9",
            "HAN,3,7,3,9",
            "HAN,3,7,5,7"

    })
    void shouldThrowException_WhenInvalidMove(Camp camp, int fromX, int fromY, int toX, int toY) {
        // given
        Board board = new Board();
        General general = new General(camp, board);
        Position fromPosition = new Position(fromX, fromY);
        Position toPosition = new Position(toX, toY);

        // when & then
        assertThatCode(() -> general.validateMove(fromPosition, toPosition))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("왕은 상하좌우 또는 대각선으로 한 칸 움직여야 합니다.");
    }
}
