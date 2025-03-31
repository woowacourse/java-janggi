package janggi.value;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RelativePositionTest {

    @DisplayName("상대 위치의 합산을 구할 수 있다.")
    @Test
    void calculateTotal() {
        List<RelativePosition> positions = List.of(
                new RelativePosition(5, 3),
                new RelativePosition(-2, -2),
                new RelativePosition(1, 1));
        RelativePosition total = RelativePosition.calculateTotal(positions);

        assertThat(total).isEqualTo(new RelativePosition(4, 2));
    }

    @DisplayName("단위 상대위치를 구할 수 있다.")
    @Test
    void calculateUnit() {
        RelativePosition relativePosition = new RelativePosition(5, 3);
        RelativePosition unit = relativePosition.calculateUnit();

        assertThat(unit).isEqualTo(new RelativePosition(1, 1));
    }

    @DisplayName("절대 경로를 구할 수 있다.")
    @Test
    void calculateAbsolutePosition() {
        RelativePosition relativePosition = new RelativePosition(3, 3);
        Position absolutePosition = relativePosition.calculateAbsolutePosition(new Position(1, 2));

        assertThat(absolutePosition).isEqualTo(new Position(4, 5));
    }
}