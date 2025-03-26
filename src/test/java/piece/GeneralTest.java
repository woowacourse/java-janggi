package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static testutil.StaticTest.A1;
import static testutil.StaticTest.A2;
import static testutil.StaticTest.A3;
import static testutil.StaticTest.B1;
import static testutil.StaticTest.C1;
import static testutil.StaticTest.D1;

import java.util.List;
import org.junit.jupiter.api.Test;
import position.Position;

public class GeneralTest {

    @Test
    void 장군은_위_아래_왼쪽_오른쪽_으로_움직일_수_있다() {
        // given
        General general = new General();

        // when
        List<Position> upPath = general.getPathForMoving(A1, A2);
        List<Position> downPath = general.getPathForMoving(A2, A1);
        List<Position> leftPath = general.getPathForMoving(B1, A1);
        List<Position> rightPath = general.getPathForMoving(B1, C1);

        // then
        assertThat(upPath).containsExactly(A2);
        assertThat(downPath).containsExactly(A1);
        assertThat(leftPath).containsExactly(A1);
        assertThat(rightPath).containsExactly(C1);
    }

    @Test
    void 장군은_위_아래_왼쪽_오른쪽_제외하고_움직일_수_없다() {
        // given
        General general = new General();

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
