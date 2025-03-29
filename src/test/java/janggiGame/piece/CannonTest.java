package janggiGame.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggiGame.piece.straightMovePiece.Cannon;
import janggiGame.piece.straightMovePiece.Chariot;
import janggiGame.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {
    @DisplayName("포는 목적지로 가는 경로를 구할 수 있다.")
    @Test
    void cannonCanGetRoute() {
        Position origin = Position.getInstanceBy(1, 1);
        Position destination = Position.getInstanceBy(1, 3);
        Cannon cannon = new Cannon(Dynasty.HAN);

        // when
        List<Position> actual = cannon.getRoute(origin, destination);

        List<Position> expected = List.of(Position.getInstanceBy(1, 2));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("포가 목적지로 갈 수 없다면 예외를 발생시킨다")
    @Test
    void cannonCannotGetRoute() {
        // given
        Position origin = Position.getInstanceBy(1, 1);
        Position destination = Position.getInstanceBy(2, 3);
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
        Map<Position, Piece> routesWithPiece = new HashMap<>();
        Cannon cannon = new Cannon(Dynasty.HAN);

        routesWithPiece.put(Position.getInstanceBy(1, 2), null);
        routesWithPiece.put(Position.getInstanceBy(1, 3), new Chariot(Dynasty.HAN));

        // when // then
        assertThatCode(() -> cannon.validateMove(routesWithPiece, null))
                .doesNotThrowAnyException();
    }

    @DisplayName("포가 자신을 제외한 다른 포를 넘으려고 할 때 예외를 발생 시킨다.")
    @Test
    void cannonJudgeMovable2() {
        // given
        Map<Position, Piece> routesWithPiece = new HashMap<>();
        Cannon cannon = new Cannon(Dynasty.HAN);

        routesWithPiece.put(Position.getInstanceBy(1, 2), null);
        routesWithPiece.put(Position.getInstanceBy(1, 3), new Cannon(Dynasty.HAN));

        // when // then
        assertThatCode(() -> cannon.validateMove(routesWithPiece, null))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("포는 포를 공격할 수 없다.")
    @Test
    void cannonJudgeMovable3() {
        // given
        Map<Position, Piece> routesWithPiece = new HashMap<>();
        Cannon cannon = new Cannon(Dynasty.HAN);

        routesWithPiece.put(Position.getInstanceBy(1, 2), null);
        routesWithPiece.put(Position.getInstanceBy(1, 3), new Chariot(Dynasty.HAN));

        // when // then
        assertThatCode(() -> cannon.validateMove(routesWithPiece, new Cannon(Dynasty.CHO))).isInstanceOf(
                        UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("포는 궁성 안에서 중심을 포함한 대각선 이동 경로를 구하는 것이 가능하다")
    @Test
    void canMoveDiagonalThroughCenter() {
        // given
        Cannon cannon = new Cannon(Dynasty.HAN);
        Position origin = Position.getInstanceBy(3, 7);
        Position destination = Position.getInstanceBy(5, 9);

        // when // then
        assertThatCode(() -> cannon.getRoute(origin, destination))
                .doesNotThrowAnyException();
    }
}
