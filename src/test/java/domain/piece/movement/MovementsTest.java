package domain.piece.movement;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.coordiante.Coordinate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MovementsTest {

    @DisplayName("궁성 중앙에서는 모든 대각선 이동이 추가된다")
    @Test
    void addMovementIfInGung_center() {
        Coordinate from = new Coordinate(2, 5);

        Movements movements = new Movements(List.of(Movement.UP, Movement.DOWN));
        movements.addMovementIfInGung(from);

        assertThat(movements.getMovements()).contains(
                Movement.DOWN_RIGHT, Movement.DOWN_LEFT,
                Movement.UP_LEFT, Movement.UP_RIGHT
        );
    }

    @DisplayName("궁성 오른쪽 아래 꼭짓점에서는 왼쪽 위 방향이 추가된다")
    @Test
    void addMovementIfInGung_rightDownCorner() {
        Coordinate from = new Coordinate(3, 6);

        Movements movements = new Movements(List.of());
        movements.addMovementIfInGung(from);

        assertThat(movements.getMovements()).contains(Movement.UP_LEFT);
    }

    @DisplayName("궁성 왼쪽 아래 꼭짓점에서는 UP_RIGHT가 추가된다")
    @Test
    void addMovementIfInGung_downLeftCorner() {
        Coordinate from = new Coordinate(3, 4);

        Movements movements = new Movements(List.of());
        movements.addMovementIfInGung(from);

        assertThat(movements.getMovements()).contains(Movement.UP_RIGHT);
    }

    @DisplayName("궁성 오른쪽 위 꼭짓점에서는 DOWN_LEFT가 추가된다")
    @Test
    void addMovementIfInGung_upRightCorner() {
        Coordinate from = new Coordinate(1, 6);

        Movements movements = new Movements(List.of());
        movements.addMovementIfInGung(from);

        assertThat(movements.getMovements()).contains(Movement.DOWN_LEFT);
    }

    @DisplayName("궁성 왼쪽 위 꼭짓점에서는 DOWN_RIGHT가 추가된다")
    @Test
    void addMovementIfInGung_upLeftCorner() {
        Coordinate from = new Coordinate(1, 4);

        Movements movements = new Movements(List.of());
        movements.addMovementIfInGung(from);

        assertThat(movements.getMovements()).contains(Movement.DOWN_RIGHT);
    }
}
