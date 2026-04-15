package domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.BoardState;
import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.movement.vo.Paths;
import domain.piece.Team;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("SoldierMovement 클래스 테스트")
class SoldierMovementTest {

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    private boolean canReach(Paths paths, Position target) {
        return paths.asList().stream().anyMatch(path -> path.positions().getLast().equals(target));
    }

    private BoardState emptyBoard() {
        return StubBoardState.empty();
    }

    @Test
    @DisplayName("HAN 졸은 전진(row+1), 좌, 우 방향으로 이동 가능하다")
    void hanSoldierMovesForwardAndSideways() {
        SoldierMovement movement = new SoldierMovement(Team.HAN);
        Position pos = pos(Column.E, Row.FOUR);

        Paths paths = movement.findPotentialPaths(pos);

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

        Paths paths = movement.findPotentialPaths(pos);

        assertThat(paths.asList()).hasSize(3);
        assertThat(canReach(paths, pos(Column.E, Row.FOUR))).isTrue();
        assertThat(canReach(paths, pos(Column.D, Row.FIVE))).isTrue();
        assertThat(canReach(paths, pos(Column.F, Row.FIVE))).isTrue();
    }

    @Test
    @DisplayName("졸은 후보 경로와 실제 통과 가능한 도착지가 동일하다")
    void findValidReachablePositionsMatchCandidateDestinations() {
        SoldierMovement movement = new SoldierMovement(Team.HAN);
        Position source = pos(Column.E, Row.FOUR);

        List<Position> destinations = movement.findReachablePositions(source, emptyBoard());

        assertThat(destinations).containsExactlyInAnyOrder(
                pos(Column.E, Row.FIVE),
                pos(Column.D, Row.FOUR),
                pos(Column.F, Row.FOUR)
        );
    }

    @Test
    @DisplayName("궁성 중앙의 HAN 졸은 전방 대각선으로 이동할 수 있다")
    void hanSoldierCanMoveForwardDiagonallyInsidePalace() {
        SoldierMovement movement = new SoldierMovement(Team.HAN);
        Position source = pos(Column.E, Row.ONE);

        List<Position> destinations = movement.findReachablePositions(source, emptyBoard());

        assertThat(destinations).containsExactlyInAnyOrder(
                pos(Column.E, Row.TWO),
                pos(Column.D, Row.ONE),
                pos(Column.F, Row.ONE),
                pos(Column.D, Row.TWO),
                pos(Column.F, Row.TWO)
        );
    }

    @Test
    @DisplayName("궁성 중앙의 CHO 졸은 전방 대각선으로 이동할 수 있다")
    void choSoldierCanMoveForwardDiagonallyInsidePalace() {
        SoldierMovement movement = new SoldierMovement(Team.CHO);
        Position source = pos(Column.E, Row.EIGHT);

        List<Position> destinations = movement.findReachablePositions(source, emptyBoard());

        assertThat(destinations).containsExactlyInAnyOrder(
                pos(Column.E, Row.SEVEN),
                pos(Column.D, Row.EIGHT),
                pos(Column.F, Row.EIGHT),
                pos(Column.D, Row.SEVEN),
                pos(Column.F, Row.SEVEN)
        );
    }
}
