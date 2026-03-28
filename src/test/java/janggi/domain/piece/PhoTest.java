package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.domain.Board;
import janggi.domain.point.Point;
import janggi.domain.point.Route;
import janggi.domain.status.Team;
import java.util.LinkedHashMap;
import java.util.List;
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
        board = new Board();
        Map<Point, Piece> pieces = new LinkedHashMap<>();
        pieces.put(Point.of(1, 1), new Pho(Team.HAN));
        pieces.put(Point.of(1, 2), new Cha(Team.HAN));
        pieces.put(Point.of(1, 3), new Cha(Team.CHO));
        pieces.put(Point.of(1, 5), new Pho(Team.CHO));
        pieces.put(Point.of(1, 6), new Pho(Team.CHO));
        board.init(pieces);
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
        Route route = pho.getRoute(from, to);

        // then
        assertThat(route.getRoutes().size()).isEqualTo(result);
    }

    @Test
    @DisplayName("이동할 수 없는 위치를 목적지로 할 경우 예외 발생")
    void destination_exception() {
        // given
        Piece pho = new Pho(Team.CHO);
        Point from = Point.of(0, 0);
        Point to = Point.of(1, 1);

        // when & then
        assertThatThrownBy(() -> pho.getRoute(from, to))
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
        Route route = pho.getRoute(from, to);
        List<Piece> pieces = board.getPieces(route);

        // then
        assertThat(pho.canMove(pieces)).isTrue();
    }

    @Test
    @DisplayName("경유지에 뛰어넘을 기물이 2개 이상 있으면 움직일 수 없다.")
    void can_not_pho_move() {
        // given
        Piece pho = new Pho(Team.CHO);
        Point from = Point.of(1, 1);
        Point to = Point.of(1, 4);

        // when
        Route route = pho.getRoute(from, to);
        List<Piece> pieces = board.getPieces(route);

        // then
        assertThat(pho.canMove(pieces)).isFalse();
    }

    @Test
    @DisplayName("경유지에 뛰어넘을 기물이 포일 경우 움직일 수 없다.")
    void huddle_is_pho_can_not_move() {
        // given
        Piece pho = new Pho(Team.CHO);
        Point from = Point.of(1, 5);
        Point to = Point.of(1, 7);

        // when
        Route route = pho.getRoute(from, to);
        List<Piece> pieces = board.getPieces(route);

        // then
        assertThat(pho.canMove(pieces)).isFalse();
    }
}
