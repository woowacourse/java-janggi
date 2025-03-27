package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static testutil.TestConstant.A1;
import static testutil.TestConstant.A2;
import static testutil.TestConstant.A3;
import static testutil.TestConstant.B1;
import static testutil.TestConstant.C1;
import static testutil.TestConstant.D1;

import game.Board;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class GuardTest {

    @Test
    void 사는_위_아래_왼쪽_오른쪽_으로_움직일_수_있다() {
        // given
        Guard guard = new Guard(Country.Cho);
        Board board = new Board(Map.of());

        // then
        assertThatCode(() -> guard.canMove(A1, A2, board)).doesNotThrowAnyException();
        assertThatCode(() -> guard.canMove(A2, A1, board)).doesNotThrowAnyException();
        assertThatCode(() -> guard.canMove(B1, A1, board)).doesNotThrowAnyException();
        assertThatCode(() -> guard.canMove(B1, C1, board)).doesNotThrowAnyException();
    }

    @Test
    void 사는_위_아래_왼쪽_오른쪽_제외하고_움직일_수_없다() {
        // given
        Guard guard = new Guard(Country.Cho);
        Board board = new Board(Map.of());
        // then
        assertThatThrownBy(() -> guard.canMove(A1, A3, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> guard.canMove(A2, B1, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> guard.canMove(B1, D1, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");


    }
}
