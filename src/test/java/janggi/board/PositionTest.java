package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PositionTest {
    @DisplayName("정상: 새로운 위치를 생성하여 반환하는지 확인")
    @Test
    void updatePosition() {
        Position position = new Position(1, 1);

        Position updatedPosition = position.update(new Position(2, 2));

        assertThat(updatedPosition).isEqualTo(new Position(2, 2));
    }
}
