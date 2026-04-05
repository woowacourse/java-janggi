package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class MovementsTest {
    @Test
    void 시작_지점에_대한_정상_경로를_반환한다() {
        Position start = new Position(5, 5);
        Movements movements = new Movements(List.of(Movement.RIGHT, Movement.DOWN));

        Optional<Route> result = movements.calculatePath(start);

        assertThat(result.get().route()).containsExactly(
                new Position(5, 5),
                new Position(5, 6),
                new Position(6, 6)
        );
    }

    @Test
    void 이동_과정_중_보드의_범위를_벗어나면_빈_값을_반환한다() {
        Position start = new Position(1, 1);
        Movements movements = new Movements(List.of(Movement.UP, Movement.UP));

        Optional<Route> result = movements.calculatePath(start);

        assertThat(result).isEmpty();
    }
}
