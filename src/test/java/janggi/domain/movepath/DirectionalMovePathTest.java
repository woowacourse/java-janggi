package janggi.domain.movepath;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Delta;
import janggi.domain.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DirectionalMovePathTest {

    @Test
    @DisplayName("방향 기반 MovePath 구현체의 match 메서드는 방향으로 매칭 여부를 판단한다.")
    void matchesDirectionForStraightPath() {
        // given
        DirectionalMovePath upwardPath = new DirectionalMovePath(List.of(Delta.createUp()));
        DirectionalMovePath rightPath = new DirectionalMovePath(List.of(Delta.createRight()));

        // when & then
        assertAll(
            () -> assertThat(upwardPath.matches(0, 3)).isTrue(),
            () -> assertThat(upwardPath.matches(0, 1)).isTrue(),
            () -> assertThat(rightPath.matches(4, 0)).isTrue(),
            () -> assertThat(rightPath.matches(1, 0)).isTrue()
        );
    }

    @Test
    @DisplayName("한 칸 경로라도 방향이 다르거나 제자리 이동이면 match 판단을 하지 않는다.")
    void doesNotMatchDirectionWhenDirectionIsDifferentOrSamePosition() {
        // given
        DirectionalMovePath upwardPath = new DirectionalMovePath(List.of(Delta.createUp()));
        DirectionalMovePath diagonalPath = new DirectionalMovePath(List.of(Delta.createRightUp()));

        // when & then
        assertAll(
            () -> assertThat(upwardPath.matches(0, -2)).isFalse(),
            () -> assertThat(upwardPath.matches(2, 0)).isFalse(),
            () -> assertThat(upwardPath.matches(0, 0)).isFalse(),
            () -> assertThat(diagonalPath.matches(2, 2)).isTrue(),
            () -> assertThat(diagonalPath.matches(-2, -2)).isFalse(),
            () -> assertThat(diagonalPath.matches(2, 1)).isFalse()
        );
    }

    @Test
    @DisplayName("두 칸 이상으로 구성된 경로는 방향 기반 이동 경로가 아니다.")
    void doesNotMatchDirectionWhenPathHasMultipleSteps() {
        // given
        DirectionalMovePath movePath = new DirectionalMovePath(List.of(Delta.createUp(), Delta.createRightUp()));

        // when
        boolean result = movePath.matches(1, 2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("방향 기반 경로의 중간 경유지는 도착 위치를 제외한 경로만 반환한다.")
    void intermediatePositionsExcludesDestination() {
        // given
        DirectionalMovePath movePath = new DirectionalMovePath(List.of(Delta.createUp()));

        // when
        List<Position> intermediatePositions = movePath.intermediatePositions(
            new Position(1, 1),
            new Position(1, 4)
        );

        // then
        assertThat(intermediatePositions).containsExactly(
            new Position(1, 2),
            new Position(1, 3)
        );
    }

    @Test
    @DisplayName("한 칸 이동 경로의 중간 경유지는 없다.")
    void intermediatePositionsIsEmptyWhenMoveHasNoMiddleStep() {
        // given
        DirectionalMovePath movePath = new DirectionalMovePath(List.of(Delta.createUp()));

        // when
        List<Position> intermediatePositions = movePath.intermediatePositions(
            new Position(3, 3),
            new Position(3, 4)
        );

        // then
        assertThat(intermediatePositions).isEmpty();
    }
}
