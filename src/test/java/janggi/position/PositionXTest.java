package janggi.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PositionXTest {
    @DisplayName("예외: X 값이 장기 판 범위 내에 있는지 확인")
    @ParameterizedTest
    @ValueSource(ints = {0,10})
    void exceptionPositionXTest(int value){
        assertThatThrownBy(() -> new PositionX(value)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상: X 값이 장기 판 범위 내에 있는지 확인")
    @ParameterizedTest
    @ValueSource(ints = {1,9})
    void successPositionXTest(int value){
        assertThatCode(() -> new PositionX(value)).doesNotThrowAnyException();
    }
}
