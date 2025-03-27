package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static testutil.StaticTest.B3;
import static testutil.StaticTest.B7;
import static testutil.StaticTest.C2;
import static testutil.StaticTest.C8;
import static testutil.StaticTest.D5;
import static testutil.StaticTest.E5;
import static testutil.StaticTest.F6;
import static testutil.StaticTest.G2;
import static testutil.StaticTest.G6;
import static testutil.StaticTest.G8;
import static testutil.StaticTest.H3;
import static testutil.StaticTest.H7;

import org.junit.jupiter.api.Test;

public class ElephantTest {

    @Test
    void 상은_시작지와_목적지에_따른_이동경로를_반환한다() {
        // given
        Elephant elephant = new Elephant(Country.Cho);

        // then
        assertThatCode(() -> elephant.getPathForMoving(E5, C8)).doesNotThrowAnyException();
        assertThatCode(() -> elephant.getPathForMoving(E5, B7)).doesNotThrowAnyException();
        assertThatCode(() -> elephant.getPathForMoving(E5, C2)).doesNotThrowAnyException();
        assertThatCode(() -> elephant.getPathForMoving(E5, B3)).doesNotThrowAnyException();
        assertThatCode(() -> elephant.getPathForMoving(E5, G8)).doesNotThrowAnyException();
        assertThatCode(() -> elephant.getPathForMoving(E5, G2)).doesNotThrowAnyException();
        assertThatCode(() -> elephant.getPathForMoving(E5, H7)).doesNotThrowAnyException();
        assertThatCode(() -> elephant.getPathForMoving(E5, H3)).doesNotThrowAnyException();

    }

    @Test
    void 상은_정해진_루트가_아니면_이동할_수_없다() {
        // given
        Elephant elephant = new Elephant(Country.Cho);

        // then
        assertThatThrownBy(() -> elephant.getPathForMoving(E5, D5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> elephant.getPathForMoving(E5, F6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> elephant.getPathForMoving(E5, G6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }
}
