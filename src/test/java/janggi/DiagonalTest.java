package janggi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiagonalTest {

    @DisplayName("rowDistance가 1이면 남쪽이다.")
    @Test
    void of_south() {
        assertThat(Diagonal.of(
                1,
                1
        ).isNorth()
        ).isFalse();

        assertThat(Diagonal.of(
                        1,
                        -1
                ).isNorth()
        ).isFalse();
    }

    @DisplayName("columnDistance가 1이면 동쪽이다.")
    @Test
    void of_east() {
        assertThat(Diagonal.of(
                        1,
                        1
                ).isEast()
        ).isTrue();

        assertThat(Diagonal.of(
                        -1,
                        1
                ).isEast()
        ).isTrue();
    }
}