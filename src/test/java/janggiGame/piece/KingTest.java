package janggiGame.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggiGame.Position;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class KingTest {
    @DisplayName("장의 목적지로 가는 경로는 항상 비어있다.")
    @Test
    void kingCanGetIntermediatePoints() {
        // given
        Position origin = Position.of(1, 1);
        Position destination = Position.of(1, 0);
        Advisor king = new Advisor(Dynasty.HAN);

        // when
        List<Position> actual = king.getIntermediatePoints(origin, destination);

        // then
        assertThat(actual).isEmpty();
    }

    @DisplayName("장은 목적지에 같은 나라의 기물이 존재한다면 이동할 수 없다")
    @Test
    void kingJudgeMovable3() {
        // given
        Map<Position, Piece> routesWithPiece = new LinkedHashMap<>();
        Advisor king = new Advisor(Dynasty.HAN);

        // when // then
        assertThatCode(() -> king.validateMove(routesWithPiece, new Chariot(Dynasty.HAN)))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR] ");
    }
}