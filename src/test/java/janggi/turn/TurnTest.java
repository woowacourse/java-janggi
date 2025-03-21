package janggi.turn;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.view.SetupOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnTest {

    @DisplayName("처음 턴이 초의 턴인지 확인한다.")
    @Test
    void testInitialTurn() {
        // given
        final Board board = BoardGenerator.generate(SetupOption.LEFT_SETUP);
        // when

        // then
        assertThat(board.getTurn()).isEqualTo("초");
    }

    @DisplayName("기물 이동이 한 번 끝나면 턴이 바뀐다.")
    @Test
    void testChangeTurn() {
        // given
        final Board board = BoardGenerator.generate(SetupOption.LEFT_SETUP);
        // when
        board.move(
                new Position(Row.NINE, Column.ZERO),
                new Position(Row.EIGHT, Column.ZERO)
        );
        // then
        assertThat(board.getTurn()).isEqualTo("한");
    }
}
