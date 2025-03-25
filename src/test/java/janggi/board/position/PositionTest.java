package janggi.board.position;

import static janggi.fixture.PositionFixture.createPosition;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {
    @CsvSource(value = {"3:2", "1:0", "0:-1"}, delimiterString = ":")
    @ParameterizedTest
    void subtractColumn(int column, int expected) {
        // given
        Position start = createPosition(1, 1);
        Position goal = createPosition(column, 4);

        // when
        int result = goal.subtractColumn(start);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @CsvSource(value = {"4:3", "1:0", "0:-1"}, delimiterString = ":")
    @ParameterizedTest
    void bb(int row, int expected) {
        // given
        Position start = createPosition(1, 1);
        Position goal = createPosition(3, row);

        // when
        int result = goal.subtractRow(start);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
