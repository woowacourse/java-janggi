package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BoardDimensionTest {
    private static final Dimension BOARD_DIMENSION = new BoardDimension();

    @ParameterizedTest
    @CsvSource({
            "9,3,true",
            "9,5,true",
            "7,3,true",
            "7,5,true",
    })
    @DisplayName("Han 진영 궁성 안쪽에 있다면 true를 리턴한다.")
    void isInRange(int nx, int ny, boolean expected) {
        assertThat(BOARD_DIMENSION.isInRange(nx, ny)).isEqualTo(expected);
    }

    // HAN 진영
    @ParameterizedTest
    @CsvSource({
            "9,2,false",
            "9,6,false",
            "7,2,false",
            "7,6,false",
            "10,3,false",
            "10,5,false",
            "6,3,false",
            "6,5,false",
    })
    @DisplayName("Han 진영 궁성 바깥에 있다면 false를 리턴한다.")
    void isNotInRange(int nx, int ny, boolean expected) {
        assertThat(BOARD_DIMENSION.isInRange(nx, ny)).isEqualTo(expected);
    }
}
