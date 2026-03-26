package janggi.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.position.DiagonalDelta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiagonalDeltaTest {

    @DisplayName("rowDistance가 1이면 남쪽이다.")
    @Test
    void of_south() {
        assertThat(new DiagonalDelta(1,1).isNorth()).isFalse();
        assertThat(new DiagonalDelta(1,-1).isNorth()).isFalse();

    }

    @DisplayName("columnDistance가 1이면 동쪽이다.")
    @Test
    void of_east() {
        assertThat(new DiagonalDelta(1,1).isEast()).isTrue();
        assertThat(new DiagonalDelta(-1,1).isNorth()).isTrue();
    }

    @DisplayName("가로 세로 거리가 같지 않으면 예외가 발생한다.")
    @Test
    void not_diagonal() {
        assertThatThrownBy(() -> new DiagonalDelta(1, 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("대각선이 아닙니다.");
    }
}