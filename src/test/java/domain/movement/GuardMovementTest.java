package domain.movement;

import domain.board.Col;
import domain.board.Position;
import domain.board.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GuardMovement 클래스 테스트")
class GuardMovementTest {

    private Position pos(Col col, Row row) {
        return new Position(col, row);
    }

    private boolean canReach(Paths paths, Position target) {
        return paths.asList().stream().anyMatch(p -> p.endsAt(target));
    }

    @Test
    @DisplayName("기물이 보드 중앙에 위치한다 가정, 후보 경로를 정상적으로 생성한다")
    void fromCenterHasFourCandidatePaths() {
        GuardMovement movement = new GuardMovement();
        Position center = pos(Col.E, Row.FOUR);

        Paths paths = movement.candidatePaths(center);

        assertThat(paths.asList()).hasSize(4);
        assertThat(canReach(paths, pos(Col.E, Row.THREE))).isTrue();
        assertThat(canReach(paths, pos(Col.E, Row.FIVE))).isTrue();
        assertThat(canReach(paths, pos(Col.D, Row.FOUR))).isTrue();
        assertThat(canReach(paths, pos(Col.F, Row.FOUR))).isTrue();
    }

    @Test
    @DisplayName("보드 범위를 벗어나는 경로를 제외하고, 후보 경로를 정상적으로 생성한다")
    void fromCornerHasTwoPaths() {
        GuardMovement movement = new GuardMovement();

        Paths paths = movement.candidatePaths(pos(Col.A, Row.ZERO));

        assertThat(paths.asList()).hasSize(2);
    }
}
