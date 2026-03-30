package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovePathTest {

    @Test
    @DisplayName("경로의 델타 합과 이동량이 같으면 일치한다.")
    void matchesWhenTotalDeltaMatches() {
        // given
        MovePath movePath = new MovePath(List.of(Delta.createUp(), Delta.createRightUp()));

        // when
        boolean result = movePath.matches(1, 2);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("경로의 델타 합과 이동량이 다르면 일치하지 않는다.")
    void doesNotMatchWhenTotalDeltaDiffers() {
        // given
        MovePath movePath = new MovePath(List.of(Delta.createUp(), Delta.createRightUp()));

        // when & then
        assertAll(
            () -> assertThat(movePath.matches(2, 1)).isFalse(),
            () -> assertThat(movePath.matches(1, 1)).isFalse()
        );
    }

    @Test
    @DisplayName("한 칸 직선 경로는 같은 방향의 여러 칸 직선 이동과 방향이 일치한다.")
    void matchesDirectionForStraightPath() {
        // given
        MovePath upwardPath = new MovePath(List.of(Delta.createUp()));
        MovePath rightPath = new MovePath(List.of(Delta.createRight()));

        // when & then
        assertAll(
            () -> assertThat(upwardPath.matchesDirection(0, 3)).isTrue(),
            () -> assertThat(upwardPath.matchesDirection(0, 1)).isTrue(),
            () -> assertThat(rightPath.matchesDirection(4, 0)).isTrue(),
            () -> assertThat(rightPath.matchesDirection(1, 0)).isTrue()
        );
    }

    @Test
    @DisplayName("한 칸 경로라도 방향이 다르거나 제자리 이동이면 일치하지 않는다.")
    void doesNotMatchDirectionWhenDirectionIsDifferentOrSamePosition() {
        // given
        MovePath upwardPath = new MovePath(List.of(Delta.createUp()));
        MovePath diagonalPath = new MovePath(List.of(Delta.createRightUp()));

        // when & then
        assertAll(
            () -> assertThat(upwardPath.matchesDirection(0, -2)).isFalse(),
            () -> assertThat(upwardPath.matchesDirection(2, 0)).isFalse(),
            () -> assertThat(upwardPath.matchesDirection(0, 0)).isFalse(),
            () -> assertThat(diagonalPath.matchesDirection(2, 2)).isTrue(),
            () -> assertThat(diagonalPath.matchesDirection(-2, -2)).isFalse(),
            () -> assertThat(diagonalPath.matchesDirection(2, 1)).isFalse()
        );
    }

    @Test
    @DisplayName("두 칸 이상으로 구성된 경로는 matchesDirection으로 비교하지 않는다.")
    void doesNotMatchDirectionWhenPathHasMultipleSteps() {
        // given
        MovePath movePath = new MovePath(List.of(Delta.createUp(), Delta.createRightUp()));

        // when
        boolean result = movePath.matchesDirection(1, 2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("직선 경로는 시작 위치부터 도착 위치까지 모든 칸을 순서대로 만든다.")
    void createStraightRoute() {
        // given
        MovePath movePath = new MovePath(List.of(Delta.createUp()));

        // when
        List<Position> route = movePath.createRoute(new Position(1, 1), new Position(1, 4));

        // then
        assertThat(route).containsExactly(
            new Position(1, 2),
            new Position(1, 3),
            new Position(1, 4)
        );
    }

    @Test
    @DisplayName("여러 단계 경로는 각 델타를 적용한 위치들을 순서대로 만든다.")
    void createRouteForMultiStepPath() {
        // given
        MovePath movePath = new MovePath(List.of(
            Delta.createUp(),
            Delta.createRightUp(),
            Delta.createRightUp()
        ));

        // when
        List<Position> route = movePath.createRoute(new Position(4, 4), new Position(6, 7));

        // then
        assertThat(route).containsExactly(
            new Position(4, 5),
            new Position(5, 6),
            new Position(6, 7)
        );
    }

    @Test
    @DisplayName("중간 경유지는 도착 위치를 제외한 경로만 반환한다.")
    void intermediatePositionsExcludesDestination() {
        // given
        MovePath straightPath = new MovePath(List.of(Delta.createUp()));
        MovePath multiStepPath = new MovePath(List.of(
            Delta.createUp(),
            Delta.createRightUp(),
            Delta.createRightUp()
        ));

        // when & then
        assertAll(
            () -> assertThat(straightPath.intermediatePositions(new Position(1, 1), new Position(1, 4)))
                .containsExactly(new Position(1, 2), new Position(1, 3)),
            () -> assertThat(multiStepPath.intermediatePositions(new Position(4, 4), new Position(6, 7)))
                .containsExactly(new Position(4, 5), new Position(5, 6))
        );
    }

    @Test
    @DisplayName("한 칸 이동 경로의 중간 경유지는 없다.")
    void intermediatePositionsIsEmptyWhenMoveHasNoMiddleStep() {
        // given
        MovePath movePath = new MovePath(List.of(Delta.createUp()));

        // when
        List<Position> intermediatePositions = movePath.intermediatePositions(
            new Position(3, 3),
            new Position(3, 4)
        );

        // then
        assertThat(intermediatePositions).isEmpty();
    }
}
