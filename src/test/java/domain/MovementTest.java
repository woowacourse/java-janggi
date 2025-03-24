package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovementTest {

    @Test
    @DisplayName("움직임의 방향이 위를 향하는 지 알 수 있다.")
    void test1() {
        //given
        final var upMovements = Set.of(Movement.UP, Movement.LEFT_UP, Movement.RIGHT_UP);

        //when & then
        assertThat(upMovements).allMatch(Movement::isUpDirection);
    }

    @Test
    @DisplayName("움직임의 방향이 아래를 향하는 지 알 수 있다.")
    void test2() {
        //given
        final var downMovements = Set.of(Movement.DOWN, Movement.LEFT_DOWN, Movement.RIGHT_DOWN);

        //when & then
        assertThat(downMovements).allMatch(Movement::isDownDirection);
    }

    @DisplayName("움직임들을 결합하여 하나의 움직임으로 만들 수 있다.")
    @Test
    void test3() {
        // given
        Movement left = Movement.LEFT;
        Movement down = Movement.DOWN;

        // when
        Movement combined = Movement.combine(left, down);

        // then
        assertAll(
            () -> assertThat(combined.deltaX()).isEqualTo(left.deltaX() + down.deltaX()),
            () -> assertThat(combined.deltaY()).isEqualTo(left.deltaY() + down.deltaY())
        );
    }
}
