package domain.movement;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GeneralMovement 클래스 테스트")
class FourDirectionMovementTest {

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    private boolean canReach(Paths paths, Position target) {
        return paths.asList().stream().anyMatch(p -> p.endsAt(target));
    }

    @Test
    @DisplayName("기물이 보드 중앙에 위치한다 가정, 후보 경로를 정상적으로 생성한다")
    void fromCenterHasFourCandidatePaths() {
        FourDirectionMovement movement = new FourDirectionMovement();
        Position center = pos(Column.E, Row.FOUR);

        Paths paths = movement.candidatePaths(center);

        assertThat(paths.asList()).hasSize(4);
        assertThat(canReach(paths, pos(Column.E, Row.THREE))).isTrue();
        assertThat(canReach(paths, pos(Column.E, Row.FIVE))).isTrue();
        assertThat(canReach(paths, pos(Column.D, Row.FOUR))).isTrue();
        assertThat(canReach(paths, pos(Column.F, Row.FOUR))).isTrue();
    }

    @Test
    @DisplayName("보드 범위를 벗어나는 경로를 제외하고, 후보 경로를 정상적으로 생성한다")
    void fromTopLeftCornerHasTwoPaths() {
        FourDirectionMovement movement = new FourDirectionMovement();
        Position topLeft = pos(Column.A, Row.ZERO);

        Paths paths = movement.candidatePaths(topLeft);

        assertThat(paths.asList()).hasSize(2);
        assertThat(canReach(paths, pos(Column.A, Row.ONE))).isTrue();  // down
        assertThat(canReach(paths, pos(Column.B, Row.ZERO))).isTrue(); // right
    }

    @Test
    @DisplayName("궁의 각 이동 경로는 1개의 좌표를 갖는다")
    void eachCandidatePathHasOnePosition() {
        FourDirectionMovement movement = new FourDirectionMovement();
        Paths paths = movement.candidatePaths(pos(Column.E, Row.FOUR));

        paths.asList().forEach(path ->
                assertThat(path.intermediates()).isEmpty()
        );
    }
}
