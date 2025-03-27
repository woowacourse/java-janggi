package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static testutil.StaticTest.A1;
import static testutil.StaticTest.A2;
import static testutil.StaticTest.A3;
import static testutil.StaticTest.B1;
import static testutil.StaticTest.C1;
import static testutil.StaticTest.D1;

import org.junit.jupiter.api.Test;

public class GeneralTest {

    @Test
    void 장군은_위_아래_왼쪽_오른쪽_으로_움직일_수_있다() {
        // given
        General general = new General(Country.Cho);

        // then
        assertThatCode(() -> general.getPathForMoving(A1, A2))
                .doesNotThrowAnyException();

        assertThatCode(() -> general.getPathForMoving(A2, A1))
                .doesNotThrowAnyException();

        assertThatCode(() -> general.getPathForMoving(B1, A1))
                .doesNotThrowAnyException();

        assertThatCode(() -> general.getPathForMoving(B1, C1))
                .doesNotThrowAnyException();
    }

    @Test
    void 장군은_위_아래_왼쪽_오른쪽_제외하고_움직일_수_없다() {
        // given
        General general = new General(Country.Cho);

        // then
        assertThatThrownBy(() -> general.getPathForMoving(A1, A3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> general.getPathForMoving(A2, B1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> general.getPathForMoving(B1, D1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }
}
