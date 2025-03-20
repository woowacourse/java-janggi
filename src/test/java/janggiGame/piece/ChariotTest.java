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

class ChariotTest {
    @DisplayName("차는 목적지로 가는 경로를 구할 수 있다.")
    @Test
    void chariotCanGetRoute() {
        // given
        Dot origin = Board.findBy(1, 1);
        Dot destination = Board.findBy(1, 3);
        Chariot chariot = new Chariot(Dynasty.HAN);

        // when
        List<Dot> actual = chariot.getRoute(origin, destination);

        List<Dot> expected = List.of(Board.findBy(1, 2));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("차가 목적지로 갈 수 없다면 예외를 발생시킨다")
    @Test
    void chariotCannotGetRoute() {
        // given
        Dot origin = Board.findBy(1, 1);
        Dot destination = Board.findBy(2, 3);
        Chariot chariot = new Chariot(Dynasty.HAN);

        // when // then
        assertThatCode(() -> chariot.getRoute(origin, destination))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }


    @DisplayName("차는 이동 경로에 어떤 말도 없다면 이동 가능하다")
    @Test
    void chariotJudgeMovable() {
        // given
        Map<Dot, Piece> routesWithPiece = new LinkedHashMap<>();
        Chariot chariot = new Chariot(Dynasty.HAN);

        routesWithPiece.put(Board.findBy(1, 2), null);
        routesWithPiece.put(Board.findBy(1, 3), null);

        // when // then
        assertThatCode(() -> chariot.validateMove(routesWithPiece, null))
                .doesNotThrowAnyException();
    }

    @DisplayName("차는 이동 경로에 기물이 존재한다면 이동할 수 없다")
    @Test
    void chariotJudgeMovable2() {
        // given
        Map<Dot, Piece> routesWithPiece = new LinkedHashMap<>();
        Chariot chariot = new Chariot(Dynasty.HAN);

        routesWithPiece.put(Board.findBy(1, 2), null);
        routesWithPiece.put(Board.findBy(1, 3), new Chariot(Dynasty.HAN));

        // when // then
        assertThatCode(() -> chariot.validateMove(routesWithPiece, null))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("차는 목적지에 같은 나라의 기물이 존재한다면 이동할 수 없다")
    @Test
    void chariotJudgeMovable3() {
        // given
        Map<Dot, Piece> routesWithPiece = new LinkedHashMap<>();
        Chariot chariot = new Chariot(Dynasty.HAN);

        routesWithPiece.put(Board.findBy(1, 2), null);
        routesWithPiece.put(Board.findBy(1, 3), null);

        // when // then
        assertThatCode(() -> chariot.validateMove(routesWithPiece, new Chariot(Dynasty.HAN)))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR] ");
    }
}