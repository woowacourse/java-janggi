/*
package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ChariotTest {
    
    private Piece chariot = new Chariot(new Position(5, 5), Team.RED);

    @DisplayName("chariot이 위로 세 칸 움직일 경우, 행이 -3 되어야 한다.")
    @Test
    void when_chariot_move_then_column_minus_one() {
        chariot.up(3);

        Position expectedPosition = new Position(2, 5);
        Position currentPosition = chariot.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("chariot이 아래로 세 칸 움직일 경우, 행이 +3 되어야 한다.")
    @Test
    void when_chariot_move_then_column_plus_one() {
        chariot.down(3);

        Position expectedPosition = new Position(8, 5);
        Position currentPosition = chariot.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("chariot이 좌측으로 세 칸 움직일 경우, 열이 -3 되어야 한다.")
    @Test
    void when_chariot_move_then_row_minus_one() {
        chariot.left(3);

        Position expectedPosition = new Position(5, 2);
        Position currentPosition = chariot.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("chariot이 우측으로 세 칸 움직일 경우, 열이 +3 되어야 한다.")
    @Test
    void when_chariot_move_then_row_plus_one() {
        chariot.right(3);

        Position expectedPosition = new Position(5, 8);
        Position currentPosition = chariot.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("chariot이 10행 9열을 벗어나면 예외가 발생한다")
    @Nested
    class chariotMoveException {

        @DisplayName("up인 경우")
        @Test
        void when_up() {
            assertThatThrownBy(() -> chariot.up(6))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("down인 경우")
        @Test
        void when_down() {
            assertThatThrownBy(() -> chariot.down(6))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("left인 경우")
        @Test
        void when_left() {
            assertThatThrownBy(() -> chariot.left(6))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("right인 경우")
        @Test
        void when_right() {
            assertThatThrownBy(() -> chariot.right(6))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
*/
