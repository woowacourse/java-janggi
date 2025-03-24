package position;

import static org.assertj.core.api.Assertions.assertThat;
import static position.Column.I;
import static position.PositionFixtures.A0;
import static position.PositionFixtures.B0;
import static position.PositionFixtures.E1;
import static position.Row.ZERO;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import route.Direction;

public class PositionTest {

    @Test
    @DisplayName("기물의 위치는 row와 column으로 나타낼 수 있다.")
    void createPalaceTest() {
        Position position = new Position(I, ZERO);
    }

    @Test
    @DisplayName("해당 경로로 이동하는 것이 가능한지 검사할 수 있다.")
    void canMoveTest() {
        // given
        Board board = new Board(Set.of());

        // when - then
        assertThat(E1.canMove(Direction.NORTH, board)).isTrue();
    }

    @Test
    @DisplayName("좌표를 이동시킬 수 있다.")
    void moveTest() {
        assertThat(A0.move(Direction.EAST)).isEqualTo(B0);
    }

}
