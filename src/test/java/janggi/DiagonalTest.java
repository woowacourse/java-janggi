package janggi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiagonalTest {

    @DisplayName("rowDistance가 1이면 남쪽이다.")
    @Test
    void of_south() {
        assertThat(Diagonal.of(
                1,
                1
        ).isSouthAndEast()
        ).isTrue();

        assertThat(Diagonal.of(
                        1,
                        -1
                ).isSouthAndWest()
        ).isTrue();
    }

    @DisplayName("columnDistance가 1이면 동쪽이다.")
    @Test
    void of_east() {
        assertThat(Diagonal.of(
                        1,
                        1
                ).isSouthAndEast()
        ).isTrue();

        assertThat(Diagonal.of(
                        -1,
                        1
                ).isNorthAndEast()
        ).isTrue();
    }
}