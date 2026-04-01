package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.domain.piece.Implementation.Cha;
import janggi.domain.point.Point;
import janggi.domain.point.Points;
import janggi.domain.status.Team;
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
        Points points = cha.getRoutePoints(from, to);

        // then
        assertThat(points.getPoints().size()).isEqualTo(result);
    }

    @Test
    @DisplayName("이동할 수 없는 위치를 목적지로 할 경우 예외 발생")
    void destination_exception() {
        // given
        Piece cha = new Cha(Team.CHO);
        Point from = Point.of(0,0);
        Point to = Point.of(7, 1);

        // when & then
        assertThatThrownBy(() -> cha.getRoutePoints(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("궁성 안에서 이동 할 경우 대각선으로 이동 할 수 있다.")
    @CsvSource(value = {"3:0:5:2:4:1","3:7:5:9:4:8","3:9:5:7:4:8"}, delimiter = ':')
    void can_move_diagonal(int fromColumn, int fromRow, int toColumn, int toRow, int routeColumn, int routeRow) {
        Piece cha = new Cha(Team.CHO);
        Point from = Point.of(fromColumn, fromRow);
        Point to = Point.of(toColumn, toRow);
        Points routePoints = cha.getRoutePoints(from, to);
        assertThat(routePoints.getPoints().getFirst().getColumn()).isEqualTo(routeColumn);
        assertThat(routePoints.getPoints().getFirst().getRow()).isEqualTo(routeRow);
    }
}
