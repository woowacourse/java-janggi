package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import model.piece.Piece;
import model.piece.PieceType;
import model.piece.Team;
import model.position.Column;
import model.position.Position;
import model.position.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class GuardRouteFinderTest {
    private final Piece piece = new Piece(Team.RED, PieceType.GUARD);
    private final Position departure = new Position(Column.TWO, Row.FIVE);

    @Nested
    @DisplayName("Guard의 이동 가능한 경로를 구한다.")
    class FindDirectionOfGuardRouteFinder {

        @Test
        @DisplayName("Up 인 경우")
        void case_up() {
            Position arrival = new Position(Column.ONE, Row.FIVE);
            assertValidDirection(departure, arrival);
        }

        @Test
        @DisplayName("Down 인 경우")
        void case_down() {
            Position arrival = new Position(Column.THREE, Row.FIVE);
            assertValidDirection(departure, arrival);
        }

        @Test
        @DisplayName("Left 인 경우")
        void case_left() {
            Position arrival = new Position(Column.TWO, Row.FOUR);
            assertValidDirection(departure, arrival);
        }

        @Test
        @DisplayName("Right 인 경우")
        void case_right() {
            Position arrival = new Position(Column.TWO, Row.SIX);
            assertValidDirection(departure, arrival);
        }

        private void assertValidDirection(Position departure, Position arrival) {
            List<Position> findDirection = piece.calculateAllDirection(departure, arrival);
            assertThat(findDirection).containsExactly(arrival);
        }
    }

    @DisplayName("Guard가 갈 수 없는 경로라면, 예외를 던져야 한다")
    @Test
    void cannot_go_position_then_throw_exception() {
        Position arrival = new Position(Column.FOUR, Row.THREE);
        assertThatThrownBy(() -> piece.calculateAllDirection(departure, arrival))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
