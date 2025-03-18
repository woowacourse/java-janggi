package move;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseMovementTest {
    @Test
    @DisplayName("위쪽 1칸, 왼쪽 대각선 1칸으로 이동할 수 있다.")
    void test1() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(1, 0);
        HorseMovement horseMovement = new HorseMovement();

        //when
        Position result = horseMovement.move(from, to);

        //then
        assertThat(result).isEqualTo(to);
    }
}
