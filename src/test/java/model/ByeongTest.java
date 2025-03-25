package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import model.piece.Byeong;
import model.position.Column;
import model.position.Position;
import model.position.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ByeongTest {

    private final Byeong byeong = new Byeong();
    private final Position departure = new Position(Column.THREE, Row.THREE);

    @Nested
    @DisplayName("Byeong의 이동 가능한 경로를 구한다.")
    class FindDirectionOfByeong {

        @Test
        @DisplayName("Up - Byeong은 Up이 없기에, 위로 움직인다면 예외가 발생해야 한다")
        void case_up() {
            Position arrival = new Position(Column.TWO, Row.THREE);
            assertThatThrownBy(() -> byeong.calculateAllDirection(departure, arrival))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Down인 경우")
        void case_down() {
            Position arrival = new Position(Column.FOUR, Row.THREE);
            assertValidDirection(departure, arrival);
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
            List<Position> findDirection = byeong.calculateAllDirection(departure, arrival);
            assertThat(findDirection).containsExactly(arrival);
        }
    }

    @DisplayName("Byeong이 갈 수 없는 경로라면, 예외를 던져야 한다")
    @Test
    void cannot_go_position_then_throw_exception() {
        Position arrival = new Position(Column.THREE, Row.FIVE);
        assertThatThrownBy(() -> byeong.calculateAllDirection(departure, arrival))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
