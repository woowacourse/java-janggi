package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GeneralTest {

    @DisplayName("이동 가능한 모든 위치를 가져온다")
    @Test
    void calculate_all_direction() {
        General general = new General(new Position(5, 5), Team.RED);
        List<List<Position>> moveResults = general.calculateAllDirection();
        List<List<Position>> expected = List.of(
            List.of(new Position(4, 5)),
            List.of(new Position(6, 5)),
            List.of(new Position(5, 4)),
            List.of(new Position(5, 6))
        );
        assertThat(moveResults).isEqualTo(expected);
    }

    @DisplayName("0~9행, 0~8열을 벗어나면 빈 리스트를 반환해야 한다")
    @Test
    void invalid_direction_calculation_then_empty_list() {
        General general = new General(new Position(0, 2), Team.RED);
        List<List<Position>> moveResults = general.calculateAllDirection();
        List<List<Position>> expected = List.of(
            List.of(),
            List.of(new Position(1, 2)),
            List.of(new Position(0, 1)),
            List.of(new Position(0, 3))
        );
        assertThat(moveResults).isEqualTo(expected);
    }
}