package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DirectionTest {

    @Test
    void 방향은_위_아래_왼쪽_오른쪽이_있다() {
        assertThat(Direction.getStraightDirection()).contains(
                Direction.LEFT,
                Direction.RIGHT,
                Direction.TOP,
                Direction.BOTTOM
        );
    }

    @Test
    void 대각선_방향은_좌상단_우상단_좌하단_우하단이_있다() {
        assertThat(Direction.getCrossDirection()).contains(
                Direction.LEFT_TOP,
                Direction.LEFT_BOTTOM,
                Direction.RIGHT_TOP,
                Direction.RIGHT_BOTTOM
        );
    }

    @CsvSource(value = {
            "LEFT,LEFT_TOP,LEFT_BOTTOM",
            "RIGHT,RIGHT_TOP,RIGHT_BOTTOM",
            "TOP,LEFT_TOP,RIGHT_TOP",
            "BOTTOM,LEFT_BOTTOM,RIGHT_BOTTOM"
    })
    @ParameterizedTest
    void 전진_방향과_동일한_방향의_대각선_이동_경로를_반환한다(
            Direction direction,
            Direction direction1,
            Direction direction2
    ) {
        assertThat(direction.nextCrossDirection()).containsAll(List.of(direction1, direction2));
    }
}
