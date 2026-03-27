package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SangTest {

    @ParameterizedTest
    @CsvSource(value = {"8:7", "8:3", "2:7", "2:3", "7:8", "3:8", "7:2", "3:2"}, delimiter = ':')
    @DisplayName("상이 움직일 때, 경유지는 두 곳이다.")
    void straight_back_route(int column, int row) {
        // given
        Piece sang = new Sang(Team.CHO);
        Point from = Point.of(5,5);
        Point to = Point.of(column, row);

        // when
        List<Point> route = sang.getRoute(from, to);

        // then
        assertThat(route.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("이동할 수 없는 위치를 목적지로 할 경우 예외 발생")
    void destination_exception() {
        // given
        Piece sang = new Sang(Team.CHO);
        Point from = Point.of(0,0);
        Point to = Point.of(1, 1);

        // when & then
        assertThatThrownBy(() -> sang.getRoute(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("규칙");
    }
}
