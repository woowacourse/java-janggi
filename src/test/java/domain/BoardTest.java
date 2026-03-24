package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class BoardTest {

    @Test
    @DisplayName("보드를 생성하면 기물들 초기화된다.")
    void 보드_생성() {
        // given
        // when
        // then
        assertDoesNotThrow(Board::of);
    }
}
