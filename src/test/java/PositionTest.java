import janggi.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PositionTest {

    @ParameterizedTest
    @CsvSource(value = {"0,1", "1,0", "11,1", "1,10"})
    @DisplayName("유효하지 않은 범위로 위치를 생성할 수 없다.")
    void validateRange(int row, int column) {
        //given

        //when

        //then
        assertThatThrownBy(() -> Position.of(row, column))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 위치입니다.");
    }
}
