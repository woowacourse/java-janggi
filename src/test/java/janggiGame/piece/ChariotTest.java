package janggiGame.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggiGame.piece.straightMovePiece.Chariot;
import janggiGame.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotTest {
    @DisplayName("차는 목적지로 가는 경로를 구할 수 있다.")
    @Test
    void chariotCanGetRoute() {
        // given
        Position origin = Position.getInstanceBy(1, 1);
        Position destination = Position.getInstanceBy(1, 3);
        Chariot chariot = new Chariot(Dynasty.HAN);

        // when
        List<Position> actual = chariot.getRoute(origin, destination);

        List<Position> expected = List.of(Position.getInstanceBy(1, 2));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("차가 목적지로 갈 수 없다면 예외를 발생시킨다")
    @Test
    void chariotCannotGetRoute() {
        // given
        Position origin = Position.getInstanceBy(1, 1);
        Position destination = Position.getInstanceBy(2, 3);
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
        Map<Position, Piece> routesWithPiece = new HashMap<>();
        Chariot chariot = new Chariot(Dynasty.HAN);

        routesWithPiece.put(Position.getInstanceBy(1, 2), null);
        routesWithPiece.put(Position.getInstanceBy(1, 3), null);

        // when // then
        assertThatCode(() -> chariot.validateMove(routesWithPiece, null))
                .doesNotThrowAnyException();
    }

    @DisplayName("차는 이동 경로에 기물이 존재한다면 이동할 수 없다")
    @Test
    void chariotJudgeMovable2() {
        // given
        Map<Position, Piece> routesWithPiece = new HashMap<>();
        Chariot chariot = new Chariot(Dynasty.HAN);

        routesWithPiece.put(Position.getInstanceBy(1, 2), null);
        routesWithPiece.put(Position.getInstanceBy(1, 3), new Chariot(Dynasty.HAN));

        // when // then
        assertThatCode(() -> chariot.validateMove(routesWithPiece, null))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("차는 목적지에 같은 나라의 기물이 존재한다면 이동할 수 없다")
    @Test
    void chariotJudgeMovable3() {
        // given
        Map<Position, Piece> routesWithPiece = new HashMap<>();
        Chariot chariot = new Chariot(Dynasty.HAN);

        routesWithPiece.put(Position.getInstanceBy(1, 2), null);
        routesWithPiece.put(Position.getInstanceBy(1, 3), null);

        // when // then
        assertThatCode(() -> chariot.validateMove(routesWithPiece, new Chariot(Dynasty.HAN)))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR] ");
    }

    @DisplayName("차는 궁성 안에서 중심을 포함한 대각선 이동이 가능하다")
    @Test
    void canMoveDiagonalThroughCenter() {
        // given
        Chariot chariot = new Chariot(Dynasty.HAN);
        Position origin = Position.getInstanceBy(3, 7);
        Position destination = Position.getInstanceBy(5, 9);

        // when // then
        assertThatCode(() -> chariot.getRoute(origin, destination))
                .doesNotThrowAnyException();
    }
}
