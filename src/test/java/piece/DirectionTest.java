package piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DirectionTest {

    @Test
    void 직진_방향은_위_아래_왼쪽_오른쪽이_있다() {
        assertThat(Direction.getStraightDirection()).contains(
                Direction.LEFT,
                Direction.RIGHT,
                Direction.TOP,
                Direction.BOTTOM
        );
    }

}
