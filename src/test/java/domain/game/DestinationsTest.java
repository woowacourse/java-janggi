package domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DestinationsTest {
    @Test
    @DisplayName("목적지는 중복 좌표를 제거해서 관리한다")
    void removeDuplicatePositions() {
        Position first = Position.of(3, 3);
        Position second = Position.of(4, 4);

        Destinations destinations = new Destinations(List.of(first, second, first, second));

        assertThat(destinations.getPositions()).containsExactly(first, second);
    }
}
