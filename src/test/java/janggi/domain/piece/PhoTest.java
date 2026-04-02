package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.domain.Board;
import janggi.domain.Point;
import janggi.domain.status.Team;
import janggi.dto.PositionInfo;
import janggi.fixture.PositionInfoFixture;
import java.util.ArrayList;
import java.util.List;
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
        List<PositionInfo> info = new ArrayList<>();
        info.add(PositionInfoFixture.from(List.of("HAN", "PHO", "1", "1")));
        info.add(PositionInfoFixture.from(List.of("HAN", "CHA", "1", "2")));
        info.add(PositionInfoFixture.from(List.of("HAN", "CHA", "1", "3")));
        info.add(PositionInfoFixture.from(List.of("CHO", "PHO", "1", "5")));
        info.add(PositionInfoFixture.from(List.of("CHO", "PHO", "3", "0")));
        info.add(PositionInfoFixture.from(List.of("CHO", "JANG", "4", "1")));
        info.add(PositionInfoFixture.from(List.of("CHO", "PHO", "1", "6")));
        board.init(info);
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
        List<Point> route = pho.getRoute(from, to);

        // then
        assertThat(route.size()).isEqualTo(result);
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
        List<Point> route = pho.getRoute(from, to);
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
        List<Point> route = pho.getRoute(from, to);
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
        List<Point> route = pho.getRoute(from, to);
        List<Piece> pieces = board.getPieces(route);

        // then
        assertThat(pho.canMove(pieces)).isFalse();
    }

    @Test
    @DisplayName("궁성 안에서 대각선으로 이동하는 기능")
    void palace_diagonal_move() {
        // given
        Piece cha = new Pho(Team.CHO);
        Point from = Point.of(3, 0);
        Point to = Point.of(5, 2);

        // when
        List<Point> route = cha.getRoute(from, to);

        // then
        assertThat(route.size()).isEqualTo(1);
    }
}
