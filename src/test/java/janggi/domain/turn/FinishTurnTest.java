package janggi.domain.turn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Arrangement;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.initializer.BoardInitializer;
import org.junit.jupiter.api.Test;

public class FinishTurnTest {
    private final Board board = Board.from(BoardInitializer.createBoard(Arrangement.MA_SANG_MA_SANG, Arrangement.MA_SANG_MA_SANG));
    private final FinishTurn finishTurn = new FinishTurn(board, 1, Side.CHO);

    @Test
    void 게임_종료_상태에서_기물을_움직일_시_예외가_발생한다() {
        assertThatThrownBy(() -> finishTurn.move(new Position(7, 1), new Position(6, 1)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("게임 종료 상태에서는 이동할 수 없습니다.");
    }

    @Test
    void 항상_끝났음을_true로_반환한다() {
        assertThat(finishTurn.isFinished()).isTrue();
    }

    @Test
    void 현재_진영은_항상_EMPTY로_반환한다() {
        assertThat(finishTurn.getCurrentSide()).isEqualTo(Side.EMPTY);
    }

    @Test
    void 승자의_경우_승자_진영이_있으면_해당_진영을_반환한다() {
        assertThat(finishTurn.getWinnerSide()).isEqualTo(Side.CHO);
    }

    @Test
    void 승자_진영이_없으면_가장_높은_점수의_진영으로_반환한다() {
        FinishTurn turn = new FinishTurn(board, 1, Side.EMPTY);
        assertThat(turn.getWinnerSide()).isEqualTo(Side.HAN);
    }
}
