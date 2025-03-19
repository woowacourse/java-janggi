package model;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ByeongTest {

    @DisplayName("이동 가능한 모든 위치를 가져온다")
    @Test
    void calculate_all_direction() {
        Byeong byeong = new Byeong(new Position(5, 5));
        List<List<Position>> moveResults = byeong.calculateAllDirection();
        List<List<Position>> expected = List.of(
            List.of(new Position(6, 5)),
            List.of(new Position(5, 4)),
            List.of(new Position(5, 6))
        );
        assertThat(moveResults).isEqualTo(expected);
    }

    @DisplayName("0~9행, 0~8열을 벗어나면 빈 리스트를 반환해야 한다")
    @Test
    void invalid_direction_calculation_then_empty_list() {
        Byeong byeong = new Byeong(new Position(9, 2));
        List<List<Position>> moveResults = byeong.calculateAllDirection();
        List<List<Position>> expected = List.of(
            List.of(),
            List.of(new Position(9, 1)),
            List.of(new Position(9, 3))
        );
        assertThat(moveResults).isEqualTo(expected);
    }
}