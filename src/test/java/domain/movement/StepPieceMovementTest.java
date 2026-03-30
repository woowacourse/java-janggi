package domain.movement;

import domain.board.Col;
import domain.board.Position;
import domain.board.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("StepPieceMovement 클래스 테스트")
class StepPieceMovementTest {

    private Position pos(Col col, Row row) {
        return new Position(col, row);
    }

    @Test
    @DisplayName("기물이 보드 중앙에 위치한다 가정, 후보 경로를 정상적으로 생성한다")
    void fromCenterCreatesFourLinearPaths() {
        StepPieceMovement movement = new StepPieceMovement();
        Position center = pos(Col.E, Row.FOUR);

        Paths paths = movement.candidatePaths(center);

        assertThat(paths.asList()).hasSize(4);
    }

    @Test
    @DisplayName("각 경로는 보드 경계까지로 구성된다")
    void eachPathExtendsToEdge() {
        StepPieceMovement movement = new StepPieceMovement();
        Position center = pos(Col.E, Row.FOUR);

        Paths paths = movement.candidatePaths(center);

        Path upPath = paths.asList().stream()
                .filter(p -> p.endsAt(pos(Col.E, Row.ZERO)))
                .findFirst()
                .orElse(null);
        assertThat(upPath).isNotNull();

        Path downPath = paths.asList().stream()
                .filter(p -> p.endsAt(pos(Col.E, Row.NINE)))
                .findFirst()
                .orElse(null);
        assertThat(downPath).isNotNull();
    }

    @Test
    @DisplayName("위 방향 경로는 모든 중간 위치를 포함한다")
    void upPathContainsAllIntermediatePositions() {
        StepPieceMovement movement = new StepPieceMovement();
        Position start = pos(Col.E, Row.FOUR);

        Paths paths = movement.candidatePaths(start);

        Path upPath = paths.asList().stream()
                .filter(p -> p.endsAt(pos(Col.E, Row.ZERO)))
                .findFirst()
                .orElseThrow();

        List<Position> intermediates = upPath.intermediates();
        assertThat(intermediates).containsExactly(
                pos(Col.E, Row.THREE),
                pos(Col.E, Row.TWO),
                pos(Col.E, Row.ONE)
        );
    }

    @Test
    @DisplayName("경로 내 중간 위치들이 순서대로 정렬된다")
    void pathIntermediatesAreOrdered() {
        StepPieceMovement movement = new StepPieceMovement();
        Position start = pos(Col.A, Row.ZERO);

        Paths paths = movement.candidatePaths(start);

        Path downPath = paths.asList().stream()
                .filter(p -> p.endsAt(pos(Col.A, Row.NINE)))
                .findFirst()
                .orElseThrow();

        List<Position> intermediates = downPath.intermediates();
        assertThat(intermediates.get(0)).isEqualTo(pos(Col.A, Row.ONE));
        assertThat(intermediates.get(7)).isEqualTo(pos(Col.A, Row.EIGHT));
    }
}
