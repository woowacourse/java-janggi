package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.JanggiPosition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CannonStrategyTest implements LinearMoveAssertion {

    @Test
    @DisplayName("포는 현재 위치에서 가로와 세로 직선상의 모든 좌표를 후보로 반환한다")
    void findMovablePaths_ReturnAllLinearCandidates() {
        assertLinearStrategy(new CannonStrategy());
    }

    @DisplayName("포가 궁전 중앙이면 8방향의 경로를 반환한다")
    @Test
    void findMoveablePaths_InTheCenterPalace_ReturnEightRoutes() {
        MoveStrategy strategy = new CannonStrategy();
        List<Path> paths = strategy.findMovablePaths(JanggiPosition.of(1, 4));

        List<JanggiPosition> destinations = paths.stream()
                .map(Path::destination)
                .toList();
        assertThat(destinations.contains(JanggiPosition.of(0, 3))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(0, 4))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(0, 5))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(1, 3))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(1, 5))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(2, 3))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(2, 4))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(2, 5))).isTrue();
    }

    @DisplayName("포가 궁전 안이면 직교 방향으로는 궁전 밖까지 이동할 수 있다.")
    @Test
    void findMoveablePaths_InThePalace_InOrthogonalDirectionsCanMoveOutsidePalace() {
        MoveStrategy strategy = new CannonStrategy();
        List<Path> paths = strategy.findMovablePaths(JanggiPosition.of(1, 4));

        List<JanggiPosition> destinations = paths.stream()
                .map(Path::destination)
                .toList();
        assertThat(destinations.contains(JanggiPosition.of(1, 2))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(1, 6))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(3, 4))).isTrue();
    }

    @DisplayName("포가 궁전 안이면 대각 방향으로는 궁전 밖을 이동할 수 없다.")
    @Test
    void findMoveablePaths_InThePalace_InDiagonalDirectionsCantMoveOutsidePalace() {
        MoveStrategy strategy = new CannonStrategy();
        List<Path> paths = strategy.findMovablePaths(JanggiPosition.of(1, 4));

        List<JanggiPosition> destinations = paths.stream()
                .map(Path::destination)
                .toList();
        assertThat(destinations.contains(JanggiPosition.of(3, 2))).isFalse();
        assertThat(destinations.contains(JanggiPosition.of(3, 6))).isFalse();
    }

    @DisplayName("포가 궁성 대각에 위치하면 궁전의 반대 대각까지 이동할 수 있다.")
    @Test
    void findMoveablePaths_IsDiagonallyInThePalace_CanMoveOppositeDiagonal() {
        MoveStrategy strategy = new CannonStrategy();
        List<Path> paths = strategy.findMovablePaths(JanggiPosition.of(0, 3));

        List<JanggiPosition> destinations = paths.stream()
                .map(Path::destination)
                .toList();
        assertThat(destinations.contains(JanggiPosition.of(1, 4))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(2, 5))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(1, 2))).isFalse();
    }

    @DisplayName("포가 궁성 직교에 위치하면 대각선 방향을 이동할 수 없다.")
    @Test
    void findMoveablePaths_IsOrthogonalPartInThePalace_CantMoveDiagonalDirections() {
        MoveStrategy strategy = new CannonStrategy();
        List<Path> paths = strategy.findMovablePaths(JanggiPosition.of(1, 3));

        List<JanggiPosition> destinations = paths.stream()
                .map(Path::destination)
                .toList();
        assertThat(destinations.contains(JanggiPosition.of(0, 4))).isFalse();
        assertThat(destinations.contains(JanggiPosition.of(0, 2))).isFalse();
        assertThat(destinations.contains(JanggiPosition.of(2, 4))).isFalse();
        assertThat(destinations.contains(JanggiPosition.of(2, 2))).isFalse();
    }
}
