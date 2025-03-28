package janggi.domain.piece;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NoneTest {
    @DisplayName("빈 칸을 움직이려 할 경우 예외가 발생한다.")
    @Test
    void moveTest() {
        Piece none = new None();
        Position position = new Position(5, 5);

        assertThatThrownBy(() -> none.getMovableValidator(position, position).accept(new Pieces(new HashMap<>())))
                .isInstanceOf(IllegalArgumentException.class);
    }
}