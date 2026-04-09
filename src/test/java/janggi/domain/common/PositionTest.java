package janggi.domain.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PositionTest {

    @Test
    @DisplayName("보드 범위 내에 있는 좌표라면 좌표를 반환한다")
    void 보드_범위_내_좌표_반환() {
        // given
        Position position = new Position(1, 9);

        // when
        Optional<Position> result = position.applyDirection(0, 1);

        // then
        assertThat(result).isPresent().contains(new Position(1, 10));
    }

    @Test
    @DisplayName("보드 범위 밖에 있는 y좌표라면 좌표를 Optional")
    void 보드_범위_밖_y좌표_반환_() {
        // given
        Position position = new Position(1, 10);

        // when
        Optional<Position> result = position.applyDirection(0, 1);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("보드 범위 밖에 있는 x좌표라면 좌표를 Optional")
    void 보드_범위_밖_x좌표_반환_() {
        // given
        Position position = new Position(9, 1);

        // when
        Optional<Position> result = position.applyDirection(1, 0);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("보드 범위 내에 있는 좌표라면 연속적으로 추가할 수 있다")
    void 보드_범위_내에_연속_좌표() {
        // given
        Position position = new Position(1, 5);
        Map<Position, List<Position>> continuousRoute = new HashMap<>();

        // when
        position.applyContinuousDirection(0, 1, continuousRoute);

        // then
        assertThat(continuousRoute).hasSize(5)
                .containsKey(new Position(1, 10));
    }
}
