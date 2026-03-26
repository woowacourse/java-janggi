package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class GungTest {
    private static final Map<Position, Piece> EMPTY_PIECES = new HashMap<>();

    @Test
    void 궁은_오른쪽으로_1칸_이동할_수_있다() {
        // given
        Piece gung = new Gung(Side.HAN);
        Position departure = new Position(1, 1);
        Position destination = new Position(1, 2);
        // when
        boolean isMovable = gung.isMovable(EMPTY_PIECES, departure, destination);
        // then
        assertThat(isMovable).isTrue();
    }
}
