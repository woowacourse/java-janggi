package janggi.domain.movement;

import static janggi.domain.movement.MoveStep.DOWN;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoveProcessTest {

    @DisplayName("움직임 단위를 순서대로 결합하여 움직임 벡터로써 반복할 수 있다.")
    @Test
    void test1() {
        // given
        // when
        MoveProcess moveProcess = new MoveProcess(MoveStep.LEFT, DOWN);
        final var iterator = moveProcess.iterator();

        // then
        assertAll(
            () -> assertThat(iterator.next()).isEqualTo(MoveStep.LEFT),
            () -> assertThat(iterator.next()).isEqualTo(MoveStep.DOWN),
            () -> assertThat(iterator.hasNext()).isFalse()
        );
    }
}
