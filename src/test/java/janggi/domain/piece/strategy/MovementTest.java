package janggi.domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MovementTest {

    @ParameterizedTest
    @CsvSource({
            "5, 1",
            "-3, -1",
            "0, 0"
    })
    void 목적지와의_행_차이에_따라_행_방향을_결정한다(int rowDifference, int expectedResult) {
        // given
        Movement direction = new Movement(rowDifference, 0);
        // when
        int result = direction.calculateRowDirection();
        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            "7, 1",
            "-4, -1",
            "0, 0"
    })
    void 목적지와의_열_차이에_따라_열_방향을_결정한다(int colDifference, int expectedResult) {
        // given
        Movement direction = new Movement(0, colDifference);
        // when
        int result = direction.calculateColDirection();
        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 1, 2, true",
            "2, 1, 1, 2, true",
            "1, 3, 1, 2, false",
            "1, 2, 3, 2, false",
            "2, 1, 2, 1, true",
            "3, 1, 1, 3, true",
            "3, 3, 1, 2, false"
    })
    void 행과_열_이동_거리가_제공된_거리와_일치하지_않는지_확인한다(
            int rowDifference, int colDifference,
            int firstDistance, int secondDistance,
            boolean expectedResult
    ) {
        // given
        Movement direction = new Movement(rowDifference, colDifference);
        // when
        boolean result = direction.isValidMoveDistance(firstDistance, secondDistance);
        // then
        assertThat(result).isEqualTo(expectedResult);
    }
}
