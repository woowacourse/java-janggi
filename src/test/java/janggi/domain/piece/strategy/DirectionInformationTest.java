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
}
