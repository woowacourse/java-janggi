package domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MoveUnitTest {

    @Test
    @DisplayName("움직임의 방향이 위를 향하는 지 알 수 있다.")
    void test1() {
        //given
        final var upMoveVectors = Set.of(MoveUnit.UP, MoveUnit.LEFT_UP, MoveUnit.RIGHT_UP);

        //when & then
        assertThat(upMoveVectors).allMatch(MoveUnit::isUpDirection);
    }

    @Test
    @DisplayName("움직임의 방향이 아래를 향하는 지 알 수 있다.")
    void test2() {
        //given
        final var downMoveVectors = Set.of(MoveUnit.DOWN, MoveUnit.LEFT_DOWN, MoveUnit.RIGHT_DOWN);

        //when & then
        assertThat(downMoveVectors).allMatch(MoveUnit::isDownDirection);
    }
}
