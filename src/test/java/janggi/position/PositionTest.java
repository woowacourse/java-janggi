package janggi.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @DisplayName("위치는 행과 열의 위치 정보를 가진다.")
    @Test
    void locationCreate() {
        //given
        final Position position = new Position(4, 5);

        //when - then
        assertThat(position.row()).isEqualTo(4);
        assertThat(position.col()).isEqualTo(5);
    }

    @DisplayName("장기판의 범위를 초과하면 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"10:9", "-1:-1"}, delimiter = ':')
    void validateOutOfBound(final int row, final int col) {
        //when // then
        assertThatThrownBy(() -> new Position(row, col))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}
