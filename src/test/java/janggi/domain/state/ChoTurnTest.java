package janggi.domain.state;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.strategy.HorseElephantHorseElephant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChoTurnTest {

    @DisplayName("초의 턴에 기물을 움직인 후 왕이 잡히지 않았다면 한의 턴을 반환한다")
    @Test
    void move_IsGeneralHadntBeenCaught_ReturnHanTurn() {
        Board board = Board.initializeToBoard(new HorseElephantHorseElephant(), new HorseElephantHorseElephant());
        Position from = Position.of(3, 4);
        Position to = Position.of(4, 4);
        ChoTurn choTurn = new ChoTurn();

        assertThat(choTurn.move(from, to, board)).isInstanceOf(HanTurn.class);
    }

    @DisplayName("초의 턴에 기물을 움직인 후 왕이 잡혔다면 외통수를 반환한다")
    @Test
    void move_IsGeneralCaught_ReturnCheckmate() {
        Board board = Board.initializeToBoard(new HorseElephantHorseElephant(), new HorseElephantHorseElephant());

        GameState state = new ChoTurn();
        for (int i = 4; i < 9; i++) {
            Position from = Position.of(i-1, 4);
            Position to = Position.of(i, 4);
            state = state.move(from, to, board);
        }

        assertThat(state).isInstanceOf(Checkmate.class);
    }
}
