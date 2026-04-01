package domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("SoldierMovement 클래스 테스트")
class SoldierMovementTest {

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    private boolean canReach(Paths paths, Position target) {
        return paths.asList().stream().anyMatch(p -> p.endsAt(target));
    }

    @Test
    @DisplayName("HAN 졸은 전진(row+1), 좌, 우 방향으로 이동 가능하다")
    void hanSoldierMovesForwardAndSideways() {
        SoldierMovement movement = new SoldierMovement(Team.HAN);
        Position pos = pos(Column.E, Row.FOUR);

        Paths paths = movement.candidatePaths(pos);

        assertThat(paths.asList()).hasSize(3);
        assertThat(canReach(paths, pos(Column.E, Row.FIVE))).isTrue();
        assertThat(canReach(paths, pos(Column.D, Row.FOUR))).isTrue();
        assertThat(canReach(paths, pos(Column.F, Row.FOUR))).isTrue();
    }

    @Test
    @DisplayName("CHO 졸은 전진(row-1), 좌, 우 방향으로 이동 가능하다")
    void choSoldierMovesForwardAndSideways() {
        SoldierMovement movement = new SoldierMovement(Team.CHO);
        Position pos = pos(Column.E, Row.FIVE);

        Paths paths = movement.candidatePaths(pos);

        assertThat(paths.asList()).hasSize(3);
        assertThat(canReach(paths, pos(Column.E, Row.FOUR))).isTrue();
        assertThat(canReach(paths, pos(Column.D, Row.FIVE))).isTrue();
        assertThat(canReach(paths, pos(Column.F, Row.FIVE))).isTrue();
    }

    @Test
    @DisplayName("졸의 각 이동 경로는 1개의 좌표를 갖는다")
    void eachPathHasOnePosition() {
        SoldierMovement movement = new SoldierMovement(Team.HAN);
        Paths paths = movement.candidatePaths(pos(Column.E, Row.FOUR));

        paths.asList().forEach(path ->
                assertThat(path.intermediates()).isEmpty()
        );
    }
}
