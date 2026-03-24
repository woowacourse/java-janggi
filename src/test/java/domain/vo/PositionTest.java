package domain.vo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    @DisplayName("정상적인 좌표값 입력 시 Position을 생성한다.")
    void 위치_정상_입력() {
        // given
        // when
        int x = 1;
        int y = 1;

        Position position = Position.of(x, y);

        // then
        Assertions.assertEquals(x, position.getRow());
        Assertions.assertEquals(y, position.getCol());
    }
}
