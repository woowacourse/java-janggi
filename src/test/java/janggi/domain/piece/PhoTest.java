package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.domain.board.Board;
import janggi.domain.piece.Implementation.Cha;
import janggi.domain.piece.Implementation.Pho;
import janggi.domain.point.Point;
import janggi.domain.point.Points;
import janggi.domain.point.Route;
import janggi.domain.status.Team;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PhoTest {

    private Board board;

    @BeforeEach
    void setUp() {
        Map<Point, Piece> pieces = new LinkedHashMap<>();
        pieces.put(Point.of(1, 1), new Pho(Team.HAN));
        pieces.put(Point.of(1, 2), new Cha(Team.HAN));
        pieces.put(Point.of(1, 3), new Cha(Team.CHO));
        pieces.put(Point.of(1, 5), new Pho(Team.CHO));
        pieces.put(Point.of(1, 6), new Pho(Team.CHO));
        board = new Board(pieces);
    }

    @ParameterizedTest
    @CsvSource(value = {"0:5:4", "8:5:2", "5:0:4", "5:9:3"}, delimiter = ':')
    @DisplayName("포가 움직일 때, 경유지는 두 곳이다.")
    void straight_back_route(int x, int y, int result) {
        // given
        Piece pho = new Pho(Team.CHO);
        Point from = Point.of(5,5);
        Point to = Point.of(x, y);

        // when
        Points points = pho.getRoutePoints(from, to);

        // then
        assertThat(points.getPoints().size()).isEqualTo(result);
    }

    @Test
    @DisplayName("이동할 수 없는 위치를 목적지로 할 경우 예외 발생")
    void destination_exception() {
        // given
        Piece pho = new Pho(Team.CHO);
        Point from = Point.of(0, 0);
        Point to = Point.of(1, 1);

        // when & then
        assertThatThrownBy(() -> pho.getRoutePoints(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경유지에 뛰어넘을 기물이 1개만 있으면 움직일 수 있다.")
    void can_pho_move() {
        // given
        Piece pho = new Pho(Team.CHO);
        Point from = Point.of(1, 1);
        Point to = Point.of(1, 3);

        // when
        Points points = pho.getRoutePoints(from, to);
        Route route = board.getRoute(points);

        // then
        assertThat(pho.canMove(route)).isTrue();
    }

    @Test
    @DisplayName("경유지에 뛰어넘을 기물이 2개 이상 있으면 움직일 수 없다.")
    void can_not_pho_move() {
        // given
        Piece pho = new Pho(Team.CHO);
        Point from = Point.of(1, 1);
        Point to = Point.of(1, 4);

        // when
        Points points = pho.getRoutePoints(from, to);
        Route route = board.getRoute(points);

        // then
        assertThat(pho.canMove(route)).isFalse();
    }

    @Test
    @DisplayName("경유지에 뛰어넘을 기물이 포일 경우 움직일 수 없다.")
    void huddle_is_pho_can_not_move() {
        // given
        Piece pho = new Pho(Team.CHO);
        Point from = Point.of(1, 5);
        Point to = Point.of(1, 7);

        // when
        Points points = pho.getRoutePoints(from, to);
        Route pieces = board.getRoute(points);

        // then
        assertThat(pho.canMove(pieces)).isFalse();
    }

    @ParameterizedTest
    @DisplayName("궁성 안에서는 대각선으로 이동 할 수 있다.")
    @CsvSource(value = {"3:0:5:2:4:1","3:7:5:9:4:8","3:9:5:7:4:8"}, delimiter = ':')
    void can_move_diagonal(int fromColumn, int fromRow, int toColumn, int toRow, int routeColumn, int routeRow) {
        Piece pho = new Pho(Team.HAN);
        Point from = Point.of(fromColumn, fromRow);
        Point to = Point.of(toColumn, toRow);
        Points routePoints = pho.getRoutePoints(from, to);
        assertThat(routePoints.getPoints().getFirst().getColumn()).isEqualTo(routeColumn);
        assertThat(routePoints.getPoints().getFirst().getRow()).isEqualTo(routeRow);
    }
}
