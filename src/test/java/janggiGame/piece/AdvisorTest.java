package janggiGame.piece;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggiGame.piece.oneMovePiece.Advisor;
import janggiGame.piece.straightMovePiece.Chariot;
import janggiGame.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class AdvisorTest {
    @DisplayName("사의 목적지로 가는 경로는 항상 비어있다.")
    @Test
    void advisorCanGetRoute() {
        // given
        Position origin = Position.getInstanceBy(3, 7);
        Position destination = Position.getInstanceBy(3, 8);
        Advisor advisor = new Advisor(Dynasty.HAN);

        // when
        List<Position> actual = advisor.getRoute(origin, destination);

        // then
        assertThat(actual).isEmpty();
    }

    @DisplayName("사는 목적지에 같은 나라의 기물이 존재한다면 이동할 수 없다")
    @Test
    void advisorJudgeMovable3() {
        // given
        Map<Position, Piece> routesWithPiece = new HashMap<>();
        Advisor advisor = new Advisor(Dynasty.HAN);

        // when // then
        assertThatCode(() -> advisor.validateMove(routesWithPiece, new Chariot(Dynasty.HAN)))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR] ");
    }

    @DisplayName("사는 궁성 밖으로 이동 할 수 없다")
    @Test
    void onlyCanMoveInPalace() {
        // given
        Advisor advisor = new Advisor(Dynasty.HAN);
        Position origin = Position.getInstanceBy(3, 7);
        Position destination = Position.getInstanceBy(2, 7);

        // when // then
        assertThatCode(() -> advisor.getRoute(origin, destination))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("사는 궁성 안에서 중심을 포함한 대각선 이동이 가능하다")
    @Test
    void canMoveDiagonalThroughCenter() {
        // given
        Advisor advisor = new Advisor(Dynasty.HAN);
        Position origin = Position.getInstanceBy(3, 7);
        Position destination = Position.getInstanceBy(4, 8);

        // when // then
        assertThatCode(() -> advisor.getRoute(origin, destination))
                .doesNotThrowAnyException();
    }
}
