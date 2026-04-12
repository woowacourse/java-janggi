package domain.coordinate;

import domain.state.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DirectionTest {

    @Test
    @DisplayName("한나라 진영의 전진 방향은 아래쪽(DOWN)이다")
    void getForwardHanSideTest() {
        assertThat(Direction.getForward(Side.HAN)).isEqualTo(Direction.DOWN);
    }

    @Test
    @DisplayName("초나라 진영의 전진 방향은 위쪽(UP)이다")
    void getForwardChuSideTest() {
        assertThat(Direction.getForward(Side.CHU)).isEqualTo(Direction.UP);
    }
}