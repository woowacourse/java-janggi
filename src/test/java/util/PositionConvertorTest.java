package util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PositionConvertorTest {

    @Test
    @DisplayName("입력 받은 값을 좌표로 변환한다")
    void changeInputToPositionTest() {
        // given
        String input = "i3";

        // when
        Position position = PositionConvertor.changeInputToPosition(input);

        // then
        assertThat(position).isEqualTo(Position.of(3, 8));
    }

    @ParameterizedTest
    @ValueSource(strings = {"33", "AA", "A4", "GG5"})
    @DisplayName("입력 받은 값이 올바르지 않으면 예외가 발생한다")
    void changeInputToPositionException(String input) {
        // given

        // when & then
        assertThatThrownBy(() -> PositionConvertor.changeInputToPosition(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 좌표 형식이 아닙니다.");
    }

}
