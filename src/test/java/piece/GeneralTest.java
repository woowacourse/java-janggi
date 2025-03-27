package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static testutil.TestConstant.A1;
import static testutil.TestConstant.A2;
import static testutil.TestConstant.A3;
import static testutil.TestConstant.B1;
import static testutil.TestConstant.D1;
import static testutil.TestConstant.D5;
import static testutil.TestConstant.E4;
import static testutil.TestConstant.E5;
import static testutil.TestConstant.E6;
import static testutil.TestConstant.F5;

import game.Board;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class GeneralTest {

    @Test
    void 장군은_위_아래_왼쪽_오른쪽_으로_움직일_수_있다() {
        // given
        General general = new General(Country.Cho);
        Board board = new Board(Map.of());

        // then
        assertThatCode(() -> general.canMove(E5, E4, board)).doesNotThrowAnyException();
        assertThatCode(() -> general.canMove(E5, E6, board)).doesNotThrowAnyException();
        assertThatCode(() -> general.canMove(E5, D5, board)).doesNotThrowAnyException();
        assertThatCode(() -> general.canMove(E5, F5, board)).doesNotThrowAnyException();
    }

    @Test
    void 장군은_위_아래_왼쪽_오른쪽_제외하고_움직일_수_없다() {
        Board board = new Board(Map.of());

        // given
        General general = new General(Country.Cho);

        // then
        assertThatThrownBy(() -> general.canMove(A1, A3, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> general.canMove(A2, B1, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> general.canMove(B1, D1, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

}
