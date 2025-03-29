package janggi.domain.movestep;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InfiniteMoveProcessTest {

    @DisplayName("움직임 단계 하나를 움직임 과정으로써 무한히 반복할 수 있다.")
    @Test
    void test1() {
        // given
        // when
        MoveProcess moveProcess = new InfiniteMoveProcess(MoveStep.LEFT);
        final var iterator = moveProcess.iterator();

        // then
        for (int repetition = 1; repetition <= 100; repetition++) {
            assertThat(iterator.next()).isEqualTo(MoveStep.LEFT);
        }
    }
}
