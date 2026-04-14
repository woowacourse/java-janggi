package janggi.domain.state;

import janggi.domain.JanggiPosition;
import janggi.domain.board.Board;
import janggi.domain.board.strategy.HorseElephantHorseElephant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HanTurnTest {

    @DisplayName("한의 턴에 기물을 움직인 후 왕이 잡히지 않았다면 초의 턴을 반환한다")
    @Test
    void move_IsGeneralHadntBeenCaught_ReturnChoTurn() {
        Board board = Board.initializeToBoard(new HorseElephantHorseElephant(), new HorseElephantHorseElephant());
        JanggiPosition from = JanggiPosition.of(6, 4);
        JanggiPosition to = JanggiPosition.of(5, 4);
        HanTurn hanTurn = new HanTurn();

        assertThat(hanTurn.move(from, to, board)).isInstanceOf(ChoTurn.class);
    }

    @DisplayName("한의 턴에 기물을 움직인 후 왕이 잡혔다면 외통수를 반환한다")
    @Test
    void move_IsGeneralCaught_ReturnCheckmate() {
        Board board = Board.initializeToBoard(new HorseElephantHorseElephant(), new HorseElephantHorseElephant());

        GameState state = new HanTurn();
        for (int i = 6; i > 1; i--) {
            state = new HanTurn();
            JanggiPosition from = JanggiPosition.of(i, 4);
            JanggiPosition to = JanggiPosition.of(i - 1, 4);
            state = state.move(from, to, board);
        }

        assertThat(state).isInstanceOf(Checkmate.class);
    }

    @DisplayName("한의 턴에 초의 기물을 움직이려고 하면 예외가 발생한다")
    @Test
    void move_TryMoveDifferentPiece_ReturnException() {
        Board board = Board.initializeToBoard(new HorseElephantHorseElephant(), new HorseElephantHorseElephant());
        JanggiPosition from = JanggiPosition.of(3, 4);
        JanggiPosition to = JanggiPosition.of(4, 4);
        HanTurn hanTurn = new HanTurn();

        assertThatThrownBy(() -> hanTurn.move(from, to, board)).isInstanceOf(IllegalArgumentException.class);
    }

}
