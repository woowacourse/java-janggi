package janggi.gimul;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DistanceTest {


    @DisplayName("한 칸을 이동하면 true를 반환한다.")
    @Test
    void isMoreThanOneStep() {
        assertThat(new Distance(1, 0).isMoreThanOneStep())
                .isTrue();

        assertThat(new Distance(0, 1).isMoreThanOneStep())
                .isTrue();

        assertThat(new Distance(1, 1).isMoreThanOneStep())
                .isFalse();
    }

    @DisplayName("대각선을 포함해서 한 칸을 이동하면 true를 반환한다.")
    @Test
    void isMoreThanOneStepIncludingDiagonal() {
        assertThat(new Distance(1, 0).isMoreThanOneStepIncludingDiagonal())
                .isTrue();

        assertThat(new Distance(0, 1).isMoreThanOneStepIncludingDiagonal())
                .isTrue();

        assertThat(new Distance(1, 1).isMoreThanOneStepIncludingDiagonal())
                .isTrue();
    }
}