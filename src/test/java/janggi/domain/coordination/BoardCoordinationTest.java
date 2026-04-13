package janggi.domain.coordination;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.point.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BoardCoordinationTest {

    @ParameterizedTest
    @CsvSource({
            "9,3,true",
            "9,5,true",
            "7,3,true",
            "7,5,true",
    })
    @DisplayName("Han 진영 궁성 안쪽에 있다면 true를 리턴한다.")
    void isInRange(int nx, int ny, boolean expected) {
        Point point = new Point(nx, ny);
        assertThat(BoardCoordination.isInRange(point)).isEqualTo(expected);
    }

    // HAN 진영
    @ParameterizedTest
    @CsvSource({
            "0,-1,false",
            "0,9,false",
            "-1,0,false",
            "10,0,false",
    })
    @DisplayName("Han 진영 궁성 바깥에 있다면 false를 리턴한다.")
    void isNotInRange(int nx, int ny, boolean expected) {
        Point point = new Point(nx, ny);
        assertThat(BoardCoordination.isInRange(point)).isEqualTo(expected);
    }
}
