package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PositionTest {

    @DisplayName("정상: 위치 객체를 생성하는지 확인")
    @Test
    void createPosition() {
        Position position = new Position(1, 1);

        assertThat(position).isNotNull();
    }

    @DisplayName("예외: 위치가 보드 범위를 넘어간 경우")
    @Test
    void validatePositionRange() {
        assertThatThrownBy(() -> new Position(0, 10))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상: x축 거리를 계산하는지 확인")
    @Test
    void calculateDistanceX() {
        Position position = new Position(1, 1);
        Position other = new Position(3, 1);

        assertThat(position.distanceX(other)).isEqualTo(2);
    }

    @DisplayName("정상: y축 거리를 계산하는지 확인")
    @Test
    void calculateDistanceY() {
        Position position = new Position(1, 1);
        Position other = new Position(1, 3);

        assertThat(position.distanceY(other)).isEqualTo(2);
    }
}
