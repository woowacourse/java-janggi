package janggi.domain.value;

import janggi.domain.value.JanggiPosition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class JanggiPositionTest {

    @DisplayName("범위 내 좌표 생성 성공")
    @ParameterizedTest
    @CsvSource(value = {"0,0", "0,9", "8,0", "8,9"})
    void test1(int x, int y) {
        //when & then
        Assertions.assertThat(new JanggiPosition(x,y)).isInstanceOf(JanggiPosition.class);
    }

    @DisplayName("범위 밖 좌표 생성 실패")
    @ParameterizedTest
    @CsvSource(value = {"-1,0", "-1,9", "9,0", "9,9", "0,-1", "8,-1", "0,10", "8,10"})
    void test2(int x, int y) {
        //when & then
        Assertions.assertThatThrownBy(() -> new JanggiPosition(x,y))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] x좌표는 0~8, y좌표는 0~9 사이로 입력해주세요.");
    }

}