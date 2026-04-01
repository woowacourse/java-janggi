package domain.movement;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HorseMovement 클래스 테스트")
class HorseMovementTest {

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    private boolean canReach(Paths paths, Position target) {
        return paths.asList().stream().anyMatch(p -> p.endsAt(target));
    }

    @Test
    @DisplayName("기물이 보드 중앙에 위치한다 가정, 후보 경로를 정상적으로 생성한다")
    void fromCenterReachablePositionsAreCorrect() {
        HorseMovement movement = new HorseMovement();
        Position center = pos(Column.E, Row.FOUR);

        Paths paths = movement.candidatePaths(center);

        assertThat(canReach(paths, pos(Column.D, Row.TWO))).isTrue();
        assertThat(canReach(paths, pos(Column.F, Row.TWO))).isTrue();
        assertThat(canReach(paths, pos(Column.D, Row.SIX))).isTrue();
        assertThat(canReach(paths, pos(Column.F, Row.SIX))).isTrue();
        assertThat(canReach(paths, pos(Column.C, Row.THREE))).isTrue();
        assertThat(canReach(paths, pos(Column.C, Row.FIVE))).isTrue();
        assertThat(canReach(paths, pos(Column.G, Row.THREE))).isTrue();
        assertThat(canReach(paths, pos(Column.G, Row.FIVE))).isTrue();
    }

    @Test
    @DisplayName("보드 범위를 벗어나는 경로를 제외하고, 후보 경로를 정상적으로 생성한다")
    void fromCornerHasTwoPaths() {
        HorseMovement movement = new HorseMovement();
        Position corner = pos(Column.A, Row.ZERO);

        Paths paths = movement.candidatePaths(corner);

        assertThat(paths.asList()).hasSize(2);
        assertThat(canReach(paths, pos(Column.B, Row.TWO))).isTrue();
        assertThat(canReach(paths, pos(Column.C, Row.ONE))).isTrue();
    }

    @Test
    @DisplayName("말의 각 이동 경로는 2개의 좌표를 갖는다")
    void eachPathHasTwoPositions() {
        HorseMovement movement = new HorseMovement();
        Paths paths = movement.candidatePaths(pos(Column.E, Row.FOUR));

        paths.asList().forEach(path -> {
            assertThat(path.intermediates()).hasSize(1);
        });
    }
}
