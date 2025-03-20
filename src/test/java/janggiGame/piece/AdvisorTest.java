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

class AdvisorTest {
    @DisplayName("사의 목적지로 가는 경로는 항상 비어있다.")
    @Test
    void advisorCanGetRoute() {
        // given
        Dot origin = Board.findBy(1, 1);
        Dot destination = Board.findBy(1, 0);
        Advisor advisor = new Advisor(Dynasty.HAN);

        // when
        List<Dot> actual = advisor.getRoute(origin, destination);

        // then
        assertThat(actual).isEmpty();
    }

    @DisplayName("사는 목적지에 같은 나라의 기물이 존재한다면 이동할 수 없다")
    @Test
    void advisorJudgeMovable3() {
        // given
        Map<Dot, Piece> routesWithPiece = new LinkedHashMap<>();
        Advisor advisor = new Advisor(Dynasty.HAN);

        // when // then
        assertThatCode(() -> advisor.validateMove(routesWithPiece, new Chariot(Dynasty.HAN)))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR] ");
    }
}