package janggi.position;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionConnectionTest {
    @DisplayName("한 칸을 이동하면 true를 반환한다.")
    @Test
    void isMoreThanOneStep() {
        assertThat(new PositionConnection(1, 0).isMoreThanOneStep())
                .isTrue();

        assertThat(new PositionConnection(0, 1).isMoreThanOneStep())
                .isTrue();

        assertThat(new PositionConnection(1, 1).isMoreThanOneStep())
                .isFalse();
    }

    @DisplayName("대각선을 포함해서 한 칸을 이동하면 false를 반환한다.")
    @Test
    void isMoreThanOneStepIncludingDiagonal() {
        assertThat(new PositionConnection(1, 0).isMoreThanOneStepIncludingDiagonal())
                .isFalse();

        assertThat(new PositionConnection(0, 1).isMoreThanOneStepIncludingDiagonal())
                .isFalse();

        assertThat(new PositionConnection(1, 1).isMoreThanOneStepIncludingDiagonal())
                .isFalse();
    }
}