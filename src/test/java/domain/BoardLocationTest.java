package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BoardLocationTest {

    @DisplayName("보드의 범위는 가로9, 세로 10 범위 밖에있는지 검증한다")
    @ParameterizedTest
    @CsvSource({
            "0,0",
            "1,0",
            "0,1",
            "10,9",
            "10,10"
    })
    void test(int x, int y) {
        // given when & then
        assertThatThrownBy(() ->{
            new BoardLocation(x, y);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
