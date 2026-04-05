package janggi.domain.turn;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Arrangement;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.initializer.BoardInitializer;
import org.junit.jupiter.api.Test;

public class PlayerTurnTest {
    private Board board = Board.from(BoardInitializer.createBoard(Arrangement.MA_SANG_MA_SANG, Arrangement.MA_SANG_MA_SANG));
    @Test
    void 초기_생성할_때_초_진영으로_생성된다() {
        PlayerTurn turn = PlayerTurn.init(board);
        assertThat(turn.getCurrentSide()).isEqualTo(Side.CHO);
        assertThat(turn.getCurrentTurn()).isEqualTo(0);
    }

    @Test
    void 기존_보드_데이터에_대한_생성에_대해서_해당_진영과_턴을_반환한다() {
        PlayerTurn turn = PlayerTurn.from(board, 2, Side.HAN);

        assertThat(turn.getCurrentSide()).isEqualTo(Side.HAN);
        assertThat(turn.getCurrentTurn()).isEqualTo(2);
    }
}
