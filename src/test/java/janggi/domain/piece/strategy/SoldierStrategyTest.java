package janggi.domain.piece.strategy;

import janggi.domain.Camp;
import janggi.domain.Path;
import janggi.domain.JanggiPosition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SoldierStrategyTest {

    @DisplayName("병이 현재 위치에서 앞, 양 옆 1칸씩의 좌표를 도착지점 후보로 반환한다")
    @ParameterizedTest
    @EnumSource(Camp.class)
    void findMoveablePaths_GetCurrentPosition_ReturnAllPossibleRoutes(Camp camp) {
        MoveStrategy strategy = new SoldierStrategy(camp.forward());
        JanggiPosition current = JanggiPosition.of(4, 4);
        List<Path> paths = strategy.findMovablePaths(current);
        JanggiPosition expectedForwardPosition = camp.forward().findNextPosition(current).orElseThrow();

        assertThat(paths.get(0).destination()).isEqualTo(expectedForwardPosition);
        assertThat(paths.get(1).destination()).isEqualTo(JanggiPosition.of(4, 3));
        assertThat(paths.get(2).destination()).isEqualTo(JanggiPosition.of(4, 5));
    }

    @DisplayName("병이 궁성 중앙에 있다면 앞, 양 옆, 왼쪽/오른쪽 대각 방향으로 이동할 수 있다")
    @Test
    void findMoveablePaths_InTheCenterPalace_ReturnFiveRoutes() {
        MoveStrategy strategy = new SoldierStrategy(Camp.CHO.forward());
        List<Path> paths = strategy.findMovablePaths(JanggiPosition.of(8, 4));
        List<JanggiPosition> destinations = paths.stream()
                .map(Path::destination)
                .toList();
        assertThat(destinations.contains(JanggiPosition.of(9, 4))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(9, 3))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(9, 5))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(8, 3))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(8, 5))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(7, 4))).isFalse();
        assertThat(destinations.contains(JanggiPosition.of(7, 3))).isFalse();
        assertThat(destinations.contains(JanggiPosition.of(7, 5))).isFalse();
    }

    @DisplayName("병이 궁성 대각에 위치하면 기본 이동 + 궁중쪽 대각으로 이동할 수 있다 (뒤로가는 대각 제외)")
    @Test
    void findMoveablePaths_IsDiagonallyInThePalace_CanMovePalaceCenter() {
        MoveStrategy strategy = new SoldierStrategy(Camp.CHO.forward());
        List<Path> paths = strategy.findMovablePaths(JanggiPosition.of(7, 3));
        List<JanggiPosition> destinations = paths.stream()
                .map(Path::destination)
                .toList();
        assertThat(destinations.contains(JanggiPosition.of(8, 4))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(8, 3))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(8, 2))).isFalse();
        assertThat(destinations.contains(JanggiPosition.of(7, 4))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(7, 2))).isTrue();
    }

    @DisplayName("병이 상대 진영의 baseline에 도달하면 양 옆으로만 이동할 수 있다")
    @Test
    void findmoveablePaths_IsOpponentsBaseline_CanMoveBothSides() {
        MoveStrategy strategy = new SoldierStrategy(Camp.CHO.forward());
        List<Path> paths = strategy.findMovablePaths(JanggiPosition.of(9, 3));
        List<JanggiPosition> destinations = paths.stream()
                .map(Path::destination)
                .toList();
        assertThat(destinations.contains(JanggiPosition.of(9, 2))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(9, 4))).isTrue();
    }
}
