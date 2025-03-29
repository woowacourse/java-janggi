package janggi.board;

import janggi.piece.Team;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {
    @Test
    @DisplayName("현재 차례가 아닌 기물을 선택하면 예외를 뱉는다.")
    void test() {
        // given
        Board board = new Board(new Pieces(), Team.CHO);

        // when & then
        Assertions.assertThatThrownBy(() -> board.judgeUnitTurn(new Position(0, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("현재 차례가 아닙니다.");
    }

    @Test
    @DisplayName("졸의 이동경로를 구한다.")
    void test1() {
        // given
        Board board = new Board(new Pieces(), Team.CHO);

        // when
        List<Route> routes = board.searchAvailableRoutes(new Position(0, 3));

        // then
        Assertions.assertThat(routes).hasSize(2);
    }

    @Test
    @DisplayName("포의 이동경로를 구한다.")
    void test2() {
        // given
        Board board = new Board(new Pieces(), Team.CHO);

        // when & then
        Assertions.assertThatThrownBy(() -> board.searchAvailableRoutes(new Position(1, 7)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 기물의 이동 가능한 경로가 없습니다.");
    }

    @Test
    @DisplayName("경로 중에 기물이 있다면 거짓이다")
    void test3() {
        // given
        Board board = new Board(new Pieces(), Team.CHO);

        // when
        boolean isFalse = board.isAvailablePath(Route.of(List.of
                (new Position(0, 1), new Position(0, 2), new Position(0, 3), new Position(0, 4))));

        // then
        Assertions.assertThat(isFalse).isFalse();
    }

    @Test
    @DisplayName("경로 중에 기물이 없다면 참이다")
    void test4() {
        // given
        Board board = new Board(new Pieces(), Team.CHO);

        // when
        boolean isTrue = board.isAvailablePath(Route.of(List.of
                (new Position(0, 1), new Position(0, 2), new Position(0, 4))));

        // then
        Assertions.assertThat(isTrue).isTrue();
    }
}
