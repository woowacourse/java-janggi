package janggi.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PositionYTest {
    @DisplayName("예외: Y 값이 장기 판 범위 내에 있는지 확인")
    @ParameterizedTest
    @ValueSource(ints={-1,10})
    void exceptionPositionYTest(int value){
        assertThatThrownBy(() -> new PositionY(value)).isInstanceOf(IllegalArgumentException.class);
    }
    @DisplayName("정상: Y 값이 장기 판 범위 내에 있는지 확인")
    @ParameterizedTest
    @ValueSource(ints={0,9})
    void successPositionYTest(int value){
        assertThatCode(() -> new PositionY(value)).doesNotThrowAnyException();
    }
}
