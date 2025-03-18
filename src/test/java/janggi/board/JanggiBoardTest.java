package janggi.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiBoardTest {

    @Test
    @DisplayName("9 x 10의 빈 장기판 생성 테스트")
    void test1() {
        assertThat(JanggiBoard.initialize().getBoard().size()).isEqualTo(90);
    }
}
