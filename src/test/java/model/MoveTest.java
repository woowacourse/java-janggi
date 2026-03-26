package model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    @Test
    void 시작점과_끝점이_같으면_움직일_수_없다() {
        Position samePosition = Position.of(1, 1);
        assertThatThrownBy(() -> Move.of(samePosition, samePosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 시작점과_끝점이_다르면_움직일_수_있다() {
        Position from = Position.of(1, 1);
        Position to = Position.of(2, 2);
        assertDoesNotThrow(() -> Move.of(from, to));
    }
}
