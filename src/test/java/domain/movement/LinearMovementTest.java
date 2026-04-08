package domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("LinearMovement 클래스 테스트")
class LinearMovementTest {

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    @Test
    @DisplayName("기물이 보드 중앙에 위치한다 가정, 후보 경로를 정상적으로 생성한다")
    void fromCenterCreatesFourLinearPaths() {
        LinearMovement movement = new LinearMovement();
        Position center = pos(Column.E, Row.FOUR);

        Paths paths = movement.candidatePaths(center);

        assertThat(paths.asList()).hasSize(4);
    }

    @Test
    @DisplayName("각 경로는 보드 경계까지로 구성된다")
    void eachPathExtendsToEdge() {
        LinearMovement movement = new LinearMovement();
        Position center = pos(Column.E, Row.FOUR);

        Paths paths = movement.candidatePaths(center);

        Path upPath = paths.asList().stream()
                .filter(p -> p.endsAt(pos(Column.E, Row.ZERO)))
                .findFirst()
                .orElse(null);
        assertThat(upPath).isNotNull();

        Path downPath = paths.asList().stream()
                .filter(p -> p.endsAt(pos(Column.E, Row.NINE)))
                .findFirst()
                .orElse(null);
        assertThat(downPath).isNotNull();
    }

    @Test
    @DisplayName("위 방향 경로는 모든 중간 위치를 포함한다")
    void upPathContainsAllIntermediatePositions() {
        LinearMovement movement = new LinearMovement();
        Position start = pos(Column.E, Row.FOUR);

        Paths paths = movement.candidatePaths(start);

        Path upPath = paths.asList().stream()
                .filter(p -> p.endsAt(pos(Column.E, Row.ZERO)))
                .findFirst()
                .orElseThrow();

        List<Position> intermediates = upPath.intermediates();
        assertThat(intermediates).containsExactly(
                pos(Column.E, Row.THREE),
                pos(Column.E, Row.TWO),
                pos(Column.E, Row.ONE)
        );
    }

    @Test
    @DisplayName("경로 내 중간 위치들이 순서대로 정렬된다")
    void pathIntermediatesAreOrdered() {
        LinearMovement movement = new LinearMovement();
        Position start = pos(Column.A, Row.ZERO);

        Paths paths = movement.candidatePaths(start);

        Path downPath = paths.asList().stream()
                .filter(p -> p.endsAt(pos(Column.A, Row.NINE)))
                .findFirst()
                .orElseThrow();

        List<Position> intermediates = downPath.intermediates();
        assertThat(intermediates.get(0)).isEqualTo(pos(Column.A, Row.ONE));
        assertThat(intermediates.get(7)).isEqualTo(pos(Column.A, Row.EIGHT));
    }

    @Test
    @DisplayName("궁성 밖 위치에서는 대각선 경로를 생성하지 않는다")
    void outsidePalaceHasNodiagonalPaths() {
        LinearMovement movement = new LinearMovement();

        Paths paths = movement.candidatePaths(pos(Column.E, Row.FOUR));

        assertThat(paths.asList()).hasSize(4); // 상하좌우만
    }

    @Test
    @DisplayName("궁성 대각선 위 위치에서 4개의 대각선 경로를 추가로 생성한다")
    void palaceDiagonalCenterHasEightTotalPaths() {
        LinearMovement movement = new LinearMovement();
        Paths paths = movement.candidatePaths(pos(Column.E, Row.ONE));

        assertThat(paths.asList()).hasSize(8);
    }

    @Test
    @DisplayName("궁성 비대각선 위치(e0)에서는 대각선 경로를 생성하지 않는다")
    void palaceNonDiagonalPositionHasNoDiagonalPaths() {
        LinearMovement movement = new LinearMovement();
        Paths paths = movement.candidatePaths(pos(Column.E, Row.ZERO));

        assertThat(paths.asList()).hasSize(3);
    }

    @Test
    @DisplayName("궁성 중앙에서 대각선 경로는 궁성 밖으로 이어진다")
    void diagonalFromPalaceCenterExtendsOutsidePalace() {
        LinearMovement movement = new LinearMovement();
        Paths paths = movement.candidatePaths(pos(Column.E, Row.ONE));

        Path swPath = paths.asList().stream()
                .filter(p -> p.endsAt(pos(Column.A, Row.FIVE)))
                .findFirst()
                .orElse(null);

        assertThat(swPath).isNotNull();
        assertThat(swPath.contains(pos(Column.D, Row.TWO))).isTrue();
        assertThat(swPath.contains(pos(Column.C, Row.THREE))).isTrue();
    }

    @Test
    @DisplayName("궁성 코너(d0)에서 SE 대각선 경로가 보드 끝까지 이어진다")
    void diagonalFromPalaceCornerExtendsToEdge() {
        LinearMovement movement = new LinearMovement();
        Paths paths = movement.candidatePaths(pos(Column.D, Row.ZERO));

        Path sePath = paths.asList().stream()
                .filter(p -> p.endsAt(pos(Column.I, Row.FIVE)))
                .findFirst()
                .orElse(null);

        assertThat(sePath).isNotNull();
        assertThat(sePath.contains(pos(Column.E, Row.ONE))).isTrue();
        assertThat(sePath.contains(pos(Column.F, Row.TWO))).isTrue();
        assertThat(sePath.contains(pos(Column.G, Row.THREE))).isTrue();
    }

    @Test
    @DisplayName("초 궁성 대각선 위치(e8)에서도 동일하게 대각선 경로를 생성한다")
    void choPalaceCenterAlsoGeneratesDiagonalPaths() {
        LinearMovement movement = new LinearMovement();
        Paths paths = movement.candidatePaths(pos(Column.E, Row.EIGHT));

        assertThat(paths.asList()).hasSize(8);
    }
}
