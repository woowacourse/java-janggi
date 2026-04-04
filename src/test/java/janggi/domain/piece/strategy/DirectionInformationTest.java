package janggi.domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DirectionInformationTest {

    @ParameterizedTest
    @CsvSource({
            "5, 1",
            "-3, -1",
            "0, 0"
    })
    void 목적지와의_행_차이에_따라_행_방향을_결정한다(int rowDifference, int expectedResult) {
        // given
        DirectionInformation direction = new DirectionInformation(rowDifference, 0);
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
        DirectionInformation direction = new DirectionInformation(0, colDifference);
        // when
        int result = direction.calculateColDirection();
        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            "2, 3, 2, 3, false",
            "2, 3, 3, 2, false",
            "-2, 3, 2, 3, false",
            "2, -3, 3, 2, false",
            "1, 2, 1, 2, false",
            "1, 2, 2, 1, false",
            "1, 2, 1, 3, true",
            "1, 2, 3, 2, true"
    })
    void 두_차이값이_행열_절대_차이값과_일치하지_않는지_확인한다(
            int rowDifference, int colDifference,
            int difference1, int difference2,
            boolean expectedResult
    ) {
        // given
        DirectionInformation direction = new DirectionInformation(rowDifference, colDifference);
        // when
        boolean result = direction.isInvalidMoveDistance(difference1, difference2);
        // then
        assertThat(result).isEqualTo(expectedResult);
    }
}
