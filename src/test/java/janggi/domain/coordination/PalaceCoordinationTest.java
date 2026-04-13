package janggi.domain.coordination;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.point.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PalaceCoordinationTest {

    @Nested
    class IsRangeOf {
        @ParameterizedTest
        @CsvSource({
                //CHO진영
                "0,3,true",
                "0,5,true",
                "2,3,true",
                "2,5,true",
        })
        @DisplayName("Cho 진영 궁성에 있다면 true를 리턴한다.")
        void isInRangeOfCho(int nx, int ny, boolean expected) {
            Point point = new Point(nx, ny);
            assertThat(PalaceCoordination.isInRange(point)).isEqualTo(expected);
        }

        @ParameterizedTest
        @CsvSource({
                "0,2,false",
                "0,6,false",
                "2,2,false",
                "2,6,false",
                "-1,3,false",
                "-1,5,false",
                "3,3,false",
                "3,5,false",
        })
        @DisplayName("Cho 진영 궁성 바깥에 있다면 false를 리턴한다.")
        void isNotInRangeOfCho(int nx, int ny, boolean expected) {
            Point point = new Point(nx, ny);
            assertThat(PalaceCoordination.isInRange(point)).isEqualTo(expected);
        }

        // HAN 진영
        @ParameterizedTest
        @CsvSource({
                "9,3,true",
                "9,5,true",
                "7,3,true",
                "7,5,true",
        })
        @DisplayName("Han 진영 궁성 안쪽에 있다면 true를 리턴한다.")
        void isInRangeOfHan(int nx, int ny, boolean expected) {
            Point point = new Point(nx, ny);
            assertThat(PalaceCoordination.isInRange(point)).isEqualTo(expected);
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
        void isNotInRangeOfHan(int nx, int ny, boolean expected) {
            Point point = new Point(nx, ny);
            assertThat(PalaceCoordination.isInRange(point)).isEqualTo(expected);
        }

    }
}
