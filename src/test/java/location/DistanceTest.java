package location;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DistanceTest {

    @Test
    @DisplayName("좌표 사이의 거리를 반환할 수 있다.")
    void test1() {
        //given
        int x1 = 1;
        int y1 = 1;
        int x2 = 5;
        int y2 = 7;

        //when
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);
        Distance distance = Distance.createBy(from, to);

        //then
        assertThat(distance.x()).isEqualTo(x2 - x1);
        assertThat(distance.y()).isEqualTo(y2 - y1);
    }
}
