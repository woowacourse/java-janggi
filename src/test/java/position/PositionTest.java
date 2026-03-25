package position;

import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PositionTest {
    @Test
    @DisplayName("장기판안에 맞는 좌표를 생성할 수 있다.")
    void of_ReturnsPosition_WhenCoordinateIsWithinBoard() {
        Position position = Position.of(1, 2);

        assertThat(position.row()).isEqualTo(1);
        assertThat(position.column()).isEqualTo(2);
    }

    @Test
    @DisplayName("장기판안에 맞지 않는 좌표를 생성할 시 예외가 발생한다")
    void of_ThrowException_WhenCoordinateIsOutsideBoard() {
        assertThatThrownBy(() -> Position.of(10, 11))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
