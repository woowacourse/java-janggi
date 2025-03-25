package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MoveVectorTest {

    @Test
    @DisplayName("움직임의 방향이 위를 향하는 지 알 수 있다.")
    void test1() {
        //given
        final var upMoveVectors = Set.of(MoveVector.UP, MoveVector.LEFT_UP, MoveVector.RIGHT_UP);

        //when & then
        assertThat(upMoveVectors).allMatch(MoveVector::isUpDirection);
    }

    @Test
    @DisplayName("움직임의 방향이 아래를 향하는 지 알 수 있다.")
    void test2() {
        //given
        final var downMoveVectors = Set.of(MoveVector.DOWN, MoveVector.LEFT_DOWN, MoveVector.RIGHT_DOWN);

        //when & then
        assertThat(downMoveVectors).allMatch(MoveVector::isDownDirection);
    }

    @DisplayName("움직임들을 결합하여 하나의 움직임으로 만들 수 있다.")
    @Test
    void test3() {
        // given
        MoveVector left = MoveVector.LEFT;
        MoveVector down = MoveVector.DOWN;

        // when
        MoveVector combined = MoveVector.combine(left, down);

        // then
        assertAll(
            () -> assertThat(combined.deltaX()).isEqualTo(left.deltaX() + down.deltaX()),
            () -> assertThat(combined.deltaY()).isEqualTo(left.deltaY() + down.deltaY())
        );
    }
}
