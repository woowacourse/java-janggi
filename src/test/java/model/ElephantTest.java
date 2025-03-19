package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ElephantTest {

    @DisplayName("이동하는 모든 경로를 가져온다")
    @Test
    void calculate_all_direction() {
        Elephant elephant = new Elephant(new Position(5, 5), Team.RED);
        List<List<Position>> moveResults = elephant.calculateAllDirection();
        List<List<Position>> expected = List.of(
            List.of(new Position(4, 5), new Position(3, 4), new Position(2, 3)),
            List.of(new Position(4, 5), new Position(3, 6), new Position(2, 7)),
            List.of(new Position(5, 4), new Position(4, 3), new Position(3, 2)),
            List.of(new Position(5, 4), new Position(6, 3), new Position(7, 2)),
            List.of(new Position(5, 6), new Position(4, 7), new Position(3, 8)),
            List.of(new Position(5, 6), new Position(6, 7), new Position(7, 8)),
            List.of(new Position(6, 5), new Position(7, 4), new Position(8, 3)),
            List.of(new Position(6, 5), new Position(7, 6), new Position(8, 7))
        );
        assertThat(moveResults).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("0~9행, 0~8열을 벗어나면 빈 리스트를 반환해야 한다")
    @Test
    void invalid_direction_calculation_then_empty_list() {
        Elephant elephant = new Elephant(new Position(0, 2), Team.RED);
        List<List<Position>> moveResults = elephant.calculateAllDirection();
        List<List<Position>> expected = List.of(
            List.of(),
            List.of(),
            List.of(),
            List.of(),
            List.of(),
            List.of(new Position(0, 3), new Position(1, 4), new Position(2, 5)),
            List.of(new Position(1, 2), new Position(2, 1), new Position(3, 0)),
            List.of(new Position(1, 2), new Position(2, 3), new Position(3, 4))
        );
        assertThat(moveResults).containsExactlyInAnyOrderElementsOf(expected);
    }
}
