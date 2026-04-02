package janggi.domain.movepath;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Delta;
import janggi.domain.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FixedMovePathTest {

    @Test
    @DisplayName("경로의 델타 합과 이동량이 같으면 일치한다.")
    void matchesWhenTotalDeltaMatches() {
        // given
        FixedMovePath movePath = new FixedMovePath(List.of(Delta.createUp(), Delta.createRightUp()));

        // when
        boolean result = movePath.matches(1, 2);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("경로의 델타 합과 이동량이 다르면 일치하지 않는다.")
    void doesNotMatchWhenTotalDeltaDiffers() {
        // given
        FixedMovePath movePath = new FixedMovePath(List.of(Delta.createUp(), Delta.createRightUp()));

        // when & then
        assertAll(
            () -> assertThat(movePath.matches(2, 1)).isFalse(),
            () -> assertThat(movePath.matches(1, 1)).isFalse()
        );
    }

    @Test
    @DisplayName("고정 경로의 중간 경유지는 도착 위치를 제외한 경로를 반환한다.")
    void intermediatePositionsExcludesDestination() {
        // given
        FixedMovePath movePath = new FixedMovePath(List.of(
            Delta.createUp(),
            Delta.createRightUp(),
            Delta.createRightUp()
        ));

        // when
        List<Position> intermediatePositions = movePath.intermediatePositions(
            new Position(4, 4),
            new Position(6, 7)
        );

        // then
        assertThat(intermediatePositions).containsExactly(
            new Position(4, 5),
            new Position(5, 6)
        );
    }
}
