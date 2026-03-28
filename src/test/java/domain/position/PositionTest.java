package domain.position;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    @DisplayName("row, column 값이 같으면 같은 객체 결과 반환")
    void should_be_equal_when_row_and_column_are_same() {
        //given
        Position position1 = new Position(1, 2);
        Position position2 = new Position(1, 2);

        //when
        boolean result = position1.equals(position2);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("row, column 값이 다르면 false 반환")
    void should_not_be_equal_when_row_or_column_is_different() {
        //given
        Position position1 = new Position(1, 2);
        Position position2 = new Position(1, 3);

        //when
        boolean result = position1.equals(position2);

        //then
        assertThat(result).isFalse();
    }
}
