package janggi.domain.movestep;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MoveStepTest {

    @Test
    @DisplayName("움직임의 방향이 위를 향하는 지 알 수 있다.")
    void test1() {
        //given
        final var upMoveVectors = Set.of(MoveStep.UP, MoveStep.LEFT_UP, MoveStep.RIGHT_UP);

        //when & then
        assertThat(upMoveVectors).allMatch(MoveStep::isUpDirection);
    }

    @Test
    @DisplayName("움직임의 방향이 아래를 향하는 지 알 수 있다.")
    void test2() {
        //given
        final var downMoveVectors = Set.of(MoveStep.DOWN, MoveStep.LEFT_DOWN, MoveStep.RIGHT_DOWN);

        //when & then
        assertThat(downMoveVectors).allMatch(MoveStep::isDownDirection);
    }
}
