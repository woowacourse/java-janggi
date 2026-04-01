package domain.coordinate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PositionTest {

    @Test
    @DisplayName("좌표 입력은 행은 0부터 8 열은 0 부터 9 범위여야 한다.")
    void isInvalid_True_Test() {
        // given
        Position position = new Position(4, 4);

        // when - then
        assertThat(position.isValidRange()).isTrue();
    }

    @Test
    @DisplayName("0부터 9 범위를 넘어간 열 좌표 입력은 예외를 발생한다.")
    void isInvalid_Col_Test() {
        // given
        Position position = new Position(10, 4);

        // when - then
        assertThat(position.isValidRange()).isFalse();
    }

    @Test
    @DisplayName("0부터 9 범위를 넘어간 행 좌표 입력은 예외를 발생한다.")
    void isInvalid_Row_Test() {
        // given
        Position position = new Position(4, 9);

        // when - then
        assertThat(position.isValidRange()).isFalse();
    }
}
