package janggiGame.piece;

import janggiGame.board.Board;
import janggiGame.board.Dot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class CannonTest {
    @DisplayName("포는 목적지로 가는 경로를 구할 수 있다.")
    @Test
    void cannonCanGetRoute() {
        Dot origin = Board.findBy(1, 1);
        Dot destination = Board.findBy(1, 3);
        Cannon cannon = new Cannon(Dynasty.HAN);

        // when
        List<Dot> actual = cannon.getRoute(origin, destination);

        List<Dot> expected = List.of(Board.findBy(1, 2));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("포가 목적지로 갈 수 없다면 예외를 발생시킨다")
    @Test
    void cannonCannotGetRoute() {
        // given
        Dot origin = Board.findBy(1, 1);
        Dot destination = Board.findBy(2, 3);
        Cannon cannon = new Cannon(Dynasty.HAN);

        // when // then
        assertThatCode(() -> cannon.getRoute(origin, destination))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("포는 이동 경로에 단 하나의 말만 존재한다면 이동 가능하다")
    @Test
    void cannonJudgeMovable1() {
        // given
        Map<Dot, Piece> routesWithPiece = new LinkedHashMap<>();
        Cannon cannon = new Cannon(Dynasty.HAN);

        routesWithPiece.put(Board.findBy(1, 2), null);
        routesWithPiece.put(Board.findBy(1, 3), new Chariot(Dynasty.HAN));

        // when // then
        assertThatCode(() -> cannon.validateMove(routesWithPiece, null))
                .doesNotThrowAnyException();
    }

    @DisplayName("포가 자신을 제외한 다른 포를 넘으려고 할 때 예외를 발생 시킨다.")
    @Test
    void cannonJudgeMovable2() {
        // given
        Map<Dot, Piece> routesWithPiece = new LinkedHashMap<>();
        Cannon cannon = new Cannon(Dynasty.HAN);

        routesWithPiece.put(Board.findBy(1, 2), null);
        routesWithPiece.put(Board.findBy(1, 3), new Cannon(Dynasty.HAN));

        // when // then
        assertThatCode(() -> cannon.validateMove(routesWithPiece, null))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("포는 포를 공격할 수 없다.")
    @Test
    void cannonJudgeMovable3() {
        // given
        Map<Dot, Piece> routesWithPiece = new LinkedHashMap<>();
        Cannon cannon = new Cannon(Dynasty.HAN);

        routesWithPiece.put(Board.findBy(1, 2), null);
        routesWithPiece.put(Board.findBy(1, 3), new Chariot(Dynasty.HAN));

        // when // then
        assertThatCode(() -> cannon.validateMove(routesWithPiece, new Cannon(Dynasty.CHO))).isInstanceOf(
                        UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}