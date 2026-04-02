package janggi.model.board.palace;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PalaceTest {

    @DisplayName("위치가 궁성 안이면 true를 반환한다.")
    @Test
    void isInPalace() {
        assertThat(Palace.isInPalace(new Position(Row.NINE, Column.FIVE)))
                .isTrue();

        assertThat(Palace.isInPalace(new Position(Row.TWO, Column.FIVE)))
                .isTrue();
    }

    @DisplayName("궁성 안에서 서로 이어져 있으면 false를 반환한다.")
    @Test
    void isNotAdjacent() {
        assertThat(Palace.isNotAdjacent(
                new Position(Row.EIGHT, Column.FOUR),
                new Position(Row.NINE, Column.FIVE)
        )).isFalse();

        assertThat(Palace.isNotAdjacent(
                new Position(Row.THREE, Column.FOUR),
                new Position(Row.TWO, Column.FIVE)
        )).isFalse();
    }
}