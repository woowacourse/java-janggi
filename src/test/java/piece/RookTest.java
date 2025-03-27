package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static testutil.StaticTest.A5;
import static testutil.StaticTest.B3;
import static testutil.StaticTest.D4;
import static testutil.StaticTest.E1;
import static testutil.StaticTest.E5;
import static testutil.StaticTest.E9;
import static testutil.StaticTest.F6;
import static testutil.StaticTest.I5;

import org.junit.jupiter.api.Test;

public class RookTest {
    @Test
    void 차는_위_아래_왼쪽_오른쪽_으로_움직일_수_있다() {
        // given
        Rook rook = new Rook();

        // then
        assertThatCode(() -> rook.getPathForMoving(E5, A5))
                .doesNotThrowAnyException();

        assertThatCode(() -> rook.getPathForMoving(E5, I5))
                .doesNotThrowAnyException();

        assertThatCode(() -> rook.getPathForMoving(E5, E1))
                .doesNotThrowAnyException();

        assertThatCode(() -> rook.getPathForMoving(E5, E9))
                .doesNotThrowAnyException();
    }

    @Test
    void 차는_위_아래_왼쪽_오른쪽_제외하고_움직일_수_없다() {
        // given
        General rook = new General();

        // then
        assertThatThrownBy(() -> rook.getPathForMoving(E5, D4))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> rook.getPathForMoving(E5, B3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> rook.getPathForMoving(E5, F6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }
}
