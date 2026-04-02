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

public class ChaTest {

    @ParameterizedTest
    @CsvSource(value = {"5:0:4", "5:9:3", "8:5:2", "0:5:4"}, delimiter = ':')
    @DisplayName("시작 및 도착 지점에 따른 경유지 반환")
    void straight_back_route(int x, int y, int result) {
        // given
        Piece cha = new Cha(Team.CHO);
        Point from = Point.of(5,5);
        Point to = Point.of(x, y);

        // when
        List<Point> route = cha.getRoute(from, to);

        // then
        assertThat(route.size()).isEqualTo(result);
    }

    @Test
    @DisplayName("궁성 안에서 대각선으로 이동하는 기능")
    void palace_diagonal_move() {
        // given
        Piece cha = new Cha(Team.CHO);
        Point from = Point.of(3, 0);
        Point to = Point.of(5, 2);

        // when
        List<Point> route = cha.getRoute(from, to);

        // then
        assertThat(route.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("대각선으로 목적지로 할 경우 예외 발생")
    void destination_exception() {
        // given
        Piece cha = new Cha(Team.CHO);
        Point from = Point.of(0,0);
        Point to = Point.of(7, 1);

        // when & then
        assertThatThrownBy(() -> cha.getRoute(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("대각선");
    }
}
