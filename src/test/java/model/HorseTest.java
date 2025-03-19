package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HorseTest {

    @DisplayName("이동하는 모든 경로를 가져온다")
    @Test
    void calculate_all_direction() {
        Horse horse = new Horse(new Position(5, 5), Team.RED);
        List<List<Position>> moveResults = horse.calculateAllDirection();
        List<List<Position>> expected = List.of(
            List.of(new Position(4, 5), new Position(3, 4)),
            List.of(new Position(4, 5), new Position(3, 6)),
            List.of(new Position(5, 4), new Position(4, 3)),
            List.of(new Position(5, 4), new Position(6, 3)),
            List.of(new Position(5, 6), new Position(4, 7)),
            List.of(new Position(5, 6), new Position(6, 7)),
            List.of(new Position(6, 5), new Position(7, 4)),
            List.of(new Position(6, 5), new Position(7, 6))
        );
        assertThat(moveResults).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("0~9행, 0~8열을 벗어나면 빈 리스트를 반환해야 한다")
    @Test
    void invalid_direction_calculation_then_empty_list() {
        Horse horse = new Horse(new Position(0, 2), Team.RED);
        List<List<Position>> moveResults = horse.calculateAllDirection();
        List<List<Position>> expected = List.of(
            List.of(),
            List.of(),
            List.of(),
            List.of(new Position(0, 1), new Position(1, 0)),
            List.of(),
            List.of(new Position(0, 3), new Position(1, 4)),
            List.of(new Position(1, 2), new Position(2, 1)),
            List.of(new Position(1, 2), new Position(2, 3))
        );
        assertThat(moveResults).containsExactlyInAnyOrderElementsOf(expected);
    }
}
