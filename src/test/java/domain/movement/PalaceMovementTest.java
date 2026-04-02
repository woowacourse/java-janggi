package domain.movement;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GeneralMovement 클래스 테스트")
class PalaceMovementTest {

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    private boolean canReach(Paths paths, Position target) {
        return paths.asList().stream().anyMatch(p -> p.endsAt(target));
    }

    @Test
    @DisplayName("궁성 중앙(e1)에서 상하좌우 4 + 대각선 4 = 총 8개 경로를 생성한다")
    void fromPalaceCenterHasEightCandidatePaths() {
        PalaceMovement movement = new PalaceMovement();
        Position palaceCenter = pos(Column.E, Row.ONE);

        Paths paths = movement.candidatePaths(palaceCenter);

        assertThat(paths.asList()).hasSize(8);
        assertThat(canReach(paths, pos(Column.E, Row.ZERO))).isTrue();
        assertThat(canReach(paths, pos(Column.E, Row.TWO))).isTrue();
        assertThat(canReach(paths, pos(Column.D, Row.ONE))).isTrue();
        assertThat(canReach(paths, pos(Column.F, Row.ONE))).isTrue();
        assertThat(canReach(paths, pos(Column.D, Row.ZERO))).isTrue();
        assertThat(canReach(paths, pos(Column.F, Row.ZERO))).isTrue();
        assertThat(canReach(paths, pos(Column.D, Row.TWO))).isTrue();
        assertThat(canReach(paths, pos(Column.F, Row.TWO))).isTrue();
    }

    @Test
    @DisplayName("궁성 코너(d0)에서 궁성 안 이동 가능 경로만 생성한다")
    void fromPalaceCornerHasThreeCandidatePaths() {
        PalaceMovement movement = new PalaceMovement();
        Position corner = pos(Column.D, Row.ZERO);

        Paths paths = movement.candidatePaths(corner);

        assertThat(paths.asList()).hasSize(3);
        assertThat(canReach(paths, pos(Column.E, Row.ZERO))).isTrue();
        assertThat(canReach(paths, pos(Column.D, Row.ONE))).isTrue();
        assertThat(canReach(paths, pos(Column.E, Row.ONE))).isTrue();
    }

    @Test
    @DisplayName("궁성 밖에 위치하면 후보 경로를 생성하지 않는다")
    void fromOutsidePalaceHasNoPaths() {
        PalaceMovement movement = new PalaceMovement();

        assertThat(movement.candidatePaths(pos(Column.E, Row.FOUR)).asList()).isEmpty();
        assertThat(movement.candidatePaths(pos(Column.A, Row.ZERO)).asList()).isEmpty();
    }
}
