package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Position;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ElephantTest {

    @DisplayName("시작점, 끝점, 현재 장기말 위치들이 주어졌을 때, 이동 가능하면 true를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "3, 8",
            "7, 8",
            "8, 7",
            "8, 3",
            "7, 2",
            "2, 7",
            "3, 2",
            "2, 3"
    })
    void shouldReturnTrueWhenCanMove(int destX, int destY) {
        // given
        Elephant elephant = new Elephant(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        boolean canMove = elephant.canMove(start, end, Map.of());

        // then
        assertThat(canMove).isTrue();
    }

    @DisplayName("시작점, 끝점, 현재 장기말 위치들이 주어졌을 때, 경로 상에 말이 존재하면 false를 반환한다.")
    @Test
    void shouldReturnFalseWhenPieceOnPath() {
        // given
        Elephant elephant = new Elephant(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(8, 5);

        // when
        boolean canMove = elephant.canMove(start, end, Map.of(new Position(7,5), new Tank(Side.BLUE)));

        // then
        assertThat(canMove).isFalse();
    }

    @DisplayName("시작점, 끝점, 현재 장기말 위치들이 주어졌을 때, 이동 규칙과 다르면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "6, 6",
            "4, 6",
            "6, 4",
            "4, 4"
    })
    void shouldReturnFalseWhenInvalidMovingRule(int destX, int destY) {
        // given
        Elephant elephant = new Elephant(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        boolean canMove = elephant.canMove(start, end, Map.of());

        // then
        assertThat(canMove).isFalse();
    }
}
