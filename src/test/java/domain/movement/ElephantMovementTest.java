package domain.movement;

import domain.board.Col;
import domain.board.Position;
import domain.board.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ElephantMovement 클래스 테스트")
class ElephantMovementTest {

    private Position pos(Col col, Row row) {
        return new Position(col, row);
    }

    private boolean canReach(Paths paths, Position target) {
        return paths.asList().stream().anyMatch(p -> p.endsAt(target));
    }

    @Test
    @DisplayName("기물이 보드 중앙에 위치한다 가정, 후보 경로를 정상적으로 생성한다")
    void fromCenterReachablePositionsAreCorrect() {
        ElephantMovement movement = new ElephantMovement();
        Position center = pos(Col.E, Row.FOUR);

        Paths paths = movement.candidatePaths(center);

        assertThat(canReach(paths, pos(Col.C, Row.ONE))).isTrue();
        assertThat(canReach(paths, pos(Col.G, Row.ONE))).isTrue();
        assertThat(canReach(paths, pos(Col.C, Row.SEVEN))).isTrue();
        assertThat(canReach(paths, pos(Col.G, Row.SEVEN))).isTrue();
        assertThat(canReach(paths, pos(Col.B, Row.TWO))).isTrue();
        assertThat(canReach(paths, pos(Col.B, Row.SIX))).isTrue();
        assertThat(canReach(paths, pos(Col.H, Row.TWO))).isTrue();
        assertThat(canReach(paths, pos(Col.H, Row.SIX))).isTrue();
    }

    @Test
    @DisplayName("보드 범위를 벗어나는 경로를 제외하고, 후보 경로를 정상적으로 생성한다")
    void fromCornerHasTwoPaths() {
        ElephantMovement movement = new ElephantMovement();
        Position corner = pos(Col.A, Row.ZERO);

        Paths paths = movement.candidatePaths(corner);

        assertThat(paths.asList()).hasSize(2);
        assertThat(canReach(paths, pos(Col.C, Row.THREE))).isTrue();
        assertThat(canReach(paths, pos(Col.D, Row.TWO))).isTrue();
    }

    @Test
    @DisplayName("상의 각 이동 경로는 3개의 좌표를 갖는다")
    void eachPathHasThreePositions() {
        ElephantMovement movement = new ElephantMovement();
        Paths paths = movement.candidatePaths(pos(Col.E, Row.FOUR));

        paths.asList().forEach(path ->
                assertThat(path.intermediates()).hasSize(2)
        );
    }
}
