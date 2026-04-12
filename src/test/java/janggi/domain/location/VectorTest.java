package janggi.domain.location;


import janggi.domain.board.Vector;
import janggi.domain.rule.route.Direction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VectorTest {

    @Test
    @DisplayName("벡터는 방향과 최대 거리를 가진다.")
    void shouldHaveDirectionAndDistance() {
        int maxDistance = 2;
        Vector vector = new Vector(Direction.BACK_LEFT, maxDistance);

        Assertions.assertThat(vector.direction()).isEqualTo(Direction.BACK_LEFT);
        Assertions.assertThat(vector.distance()).isEqualTo(maxDistance);
    }
}
