package janggi.game;

import janggi.piece.Team;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiTest {
    @Test
    @DisplayName("현재 차례가 아닌 기물을 선택하면 예외를 뱉는다.")
    void test() {
        // given
        Janggi janggi = new Janggi(new Pieces(), Team.CHO);

        // when & then
        Assertions.assertThatThrownBy(() -> janggi.judgeUnitTurn(new Position(0, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("현재 차례가 아닙니다.");
    }

    @Test
    @DisplayName("졸의 이동경로를 구한다.")
    void test1() {
        // given
        Janggi janggi = new Janggi(new Pieces(), Team.CHO);

        // when
        List<Route> routes = janggi.searchAvailableRoutes(new Position(0, 3));

        // then
        Assertions.assertThat(routes).hasSize(2);
    }

    @Test
    @DisplayName("포의 이동경로를 구한다.")
    void test2() {
        // given
        Janggi janggi = new Janggi(new Pieces(), Team.CHO);

        // when & then
        Assertions.assertThatThrownBy(() -> janggi.searchAvailableRoutes(new Position(1, 7)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 기물의 이동 가능한 경로가 없습니다.");
    }

    @Test
    @DisplayName("경로 중에 기물이 있다면 거짓이다")
    void test3() {
        // given
        Janggi janggi = new Janggi(new Pieces(), Team.CHO);

        // when
        boolean isFalse = janggi.isAvailablePath(Route.of(List.of
                (new Position(0, 1), new Position(0, 2), new Position(0, 3), new Position(0, 4))));

        // then
        Assertions.assertThat(isFalse).isFalse();
    }

    @Test
    @DisplayName("경로 중에 기물이 없다면 참이다")
    void test4() {
        // given
        Janggi janggi = new Janggi(new Pieces(), Team.CHO);

        // when
        boolean isTrue = janggi.isAvailablePath(Route.of(List.of
                (new Position(0, 1), new Position(0, 2), new Position(0, 4))));

        // then
        Assertions.assertThat(isTrue).isTrue();
    }

    @Test
    @DisplayName("현재 턴과 다른 팀의 기물이 장기판에 없는지 확인한다")
    void test5() {
        // given
        Janggi janggi = new Janggi(new Pieces(), Team.CHO);

        // when
        boolean isEnemyUnit = janggi.isNoneEnemyUnit();

        // then
        Assertions.assertThat(isEnemyUnit).isFalse();
    }


}
