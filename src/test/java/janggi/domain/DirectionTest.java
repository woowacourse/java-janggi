package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DirectionTest {
    @DisplayName("방향에 따라 인접한 대각선 방향을 반환한다.")
    @ParameterizedTest
    @CsvSource({
            "N, NE, NW",
            "S, SE, SW",
            "E, NE, SE",
            "W, NW, SW"
    })
    void 직선_방향_인접_대각선_반환_테스트(Direction direction, Direction diagonal1, Direction diagonal2) {
        assertThat(direction.getAdjacentDiagonals()).containsExactlyInAnyOrder(diagonal1, diagonal2);
    }

    @DisplayName("대각선 방향의 경우, 빈 리스트를 반환한다.")
    @Test
    void 대각선_방향_인접_대각선_빈_리스트_반환_테스트() {
        assertThat(Direction.NE.getAdjacentDiagonals()).isEmpty();
    }
}