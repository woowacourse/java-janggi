package janggi.domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InfiniteMoveVectorTest {

    @DisplayName("움직임 단위 하나를 움직임 벡터로써 무한히 반복할 수 있다.")
    @Test
    void test1() {
        // given
        // when
        MoveVector moveVector = new InfiniteMoveVector(MoveStep.LEFT);
        final var iterator = moveVector.iterator();

        // then
        for (int repetition = 1; repetition <= 100; repetition++) {
            assertThat(iterator.next()).isEqualTo(MoveStep.LEFT);
        }
    }
}
