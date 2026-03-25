package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardInitializerTest {

    @Test
    @DisplayName("시작시 장기 전체 기물 개수는 32개이다.")
    void boardInitializeTest() {
        // given
        BoardInitializer boardInitializer = new BoardInitializer();

        // when - then
        assertThat(boardInitializer.initialize().size()).isEqualTo(32);
    }
}
