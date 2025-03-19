package janggi.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PositionTest {
    @DisplayName("좌표 위치 객체 생성 확인")
    @Test
    void createPosition() {
        Position position = new Position(1, 2);

        assertAll(
                () -> assertThat(position.getX()).isEqualTo(1),
                () -> assertThat(position.getY()).isEqualTo(2)
        );
    }

    @DisplayName("갱신된 좌표를 가진 새 객체 반환 확인")
    @Test
    void updatePosition() {
        Position position = new Position(1, 2);

        Position newPosition = position.updatePosition(1, 2);

        assertThat(position).isNotSameAs(newPosition);
    }
}
