package janggiGame.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggiGame.piece.oneMovePiece.King;
import janggiGame.piece.straightMovePiece.Chariot;
import janggiGame.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class KingTest {
    @DisplayName("장의 목적지로 가는 경로는 항상 비어있다.")
    @Test
    void kingCanGetRoute() {
        // given
        Position origin = Position.getInstanceBy(4, 8);
        Position destination = Position.getInstanceBy(5, 8);
        King king = new King(Dynasty.HAN);

        // when
        List<Position> actual = king.getRoute(origin, destination);

        // then
        assertThat(actual).isEmpty();
    }

    @DisplayName("장은 목적지에 같은 나라의 기물이 존재한다면 이동할 수 없다")
    @Test
    void kingJudgeMovable3() {
        // given
        Map<Position, Piece> routesWithPiece = new HashMap<>();
        King king = new King(Dynasty.HAN);

        // when // then
        assertThatCode(() -> king.validateMove(routesWithPiece, new Chariot(Dynasty.HAN)))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR] ");
    }

    @DisplayName("장 궁성 밖으로 이동 할 수 없다")
    @Test
    void onlyCanMoveInPalace() {
        // given
        King king = new King(Dynasty.HAN);
        Position origin = Position.getInstanceBy(3, 7);
        Position destination = Position.getInstanceBy(2, 7);

        // when // then
        assertThatCode(() -> king.getRoute(origin, destination))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("장은 궁성 안에서 중심을 포함한 대각선 이동이 가능하다")
    @Test
    void canMoveDiagonalThroughCenter() {
        // given
        King king = new King(Dynasty.HAN);
        Position origin = Position.getInstanceBy(4, 8);
        Position destination = Position.getInstanceBy(3, 7);

        // when // then
        assertThatCode(() -> king.getRoute(origin, destination))
                .doesNotThrowAnyException();
    }
}
