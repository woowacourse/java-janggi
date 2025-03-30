package janggi.domain;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoundTest {

    @DisplayName("한나라의 경우 기본 기물 점수에 덤으로 인한 추가 점수 1.5점을 더한다.")
    @Test
    void test1() {
        // given
        Board board = new Board(Map.of());
        Side hanSide = Side.HAN;
        Round round = new Round(board, hanSide);

        // when
        Map<Side, Double> actual = round.getCurrentPoints();
        double expected = 1.5;

        // then
        assertThat(actual.get(hanSide)).isEqualTo(expected);
    }
}
