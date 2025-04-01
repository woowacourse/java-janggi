package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import model.piece.movement.JolRouteFinder;
import model.position.Column;
import model.position.Position;
import model.position.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class JolRouteFinderTest {
    private final JolRouteFinder jolDirectionFinder = new JolRouteFinder();
    private final Position departure = new Position(Column.THREE, Row.THREE);

    @Nested
    @DisplayName("Jol의 이동 가능한 경로를 구한다.")
    class FindDirectionOfJolRouteFinder {

        @Test
        @DisplayName("Up 인 경우")
        void case_up() {
            Position arrival = new Position(Column.TWO, Row.THREE);
            assertValidDirection(departure, arrival);
        }

        @Test
        @DisplayName("Jol은 Down이 없기에, 아래로 움직인다면 예외가 발생해야 한다.")
        void case_down() {
            Position arrival = new Position(Column.FOUR, Row.THREE);
            assertThatThrownBy(() -> jolDirectionFinder.calculateAllRoute(departure, arrival))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Left 인 경우")
        void case_left() {
            Position arrival = new Position(Column.THREE, Row.TWO);
            assertValidDirection(departure, arrival);
        }

        @Test
        @DisplayName("Right 인 경우")
        void case_right() {
            Position arrival = new Position(Column.THREE, Row.FOUR);
            assertValidDirection(departure, arrival);
        }

        private void assertValidDirection(Position departure, Position arrival) {
            List<Position> findDirection = jolDirectionFinder.calculateAllRoute(departure, arrival);
            assertThat(findDirection).containsExactly(arrival);
        }
    }

    @DisplayName("jol이 갈 수 없는 경로라면, 예외를 던져야 한다")
    @Test
    void cannot_go_position_then_throw_exception() {
        Position arrival = new Position(Column.THREE, Row.FIVE);
        assertThatThrownBy(() -> jolDirectionFinder.calculateAllRoute(departure, arrival))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
