package object;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CoordinateTest {
    @Test
    void 포지션은_숫자를_가진다() {
        // given

        // when

        // then
        Assertions.assertThatNoException().isThrownBy(() -> new Coordinate(0, 0));
    }

    @Test
    void 포지션은_더할_수_있다() {
        // given
        Coordinate coordinate = new Coordinate(0, 0);
        Coordinate addCoordinate = new Coordinate(0, 1);

        // when
        Coordinate newCoordinate = coordinate.add(addCoordinate);

        // then
        Assertions.assertThat(newCoordinate).isEqualTo(new Coordinate(0, 1));
    }
}
