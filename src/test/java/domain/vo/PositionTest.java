package domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    @DisplayName("정상적인 좌표값 입력 시 Position을 생성한다.")
    void shouldCreatePositionSuccessfully() {
        // given
        // when
        int x = 1;
        int y = 1;

        Position position = Position.of(x, y);

        // then
        assertEquals(x, position.getRow());
        assertEquals(y, position.getCol());
    }

    @Test
    @DisplayName("행이 0~9 범위를 벗어나면 예외를 발생한다.")
    void shouldThrowExceptionForInvalidRow() {
        // given
        // when
        // then
        assertThatThrownBy(() ->Position.of(-1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("행이 0~9 범위를 벗어났습니다.");
    }

    @Test
    @DisplayName("열이 0~8 범위를 벗어나면 예외를 발생한다.")
    void shouldThrowExceptionForInvalidCol() {
        // given
        // when
        // then
        assertThatThrownBy(() ->Position.of(0, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("열이 0~8 범위를 벗어났습니다.");
    }
}
