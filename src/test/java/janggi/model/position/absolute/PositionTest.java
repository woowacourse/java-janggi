package janggi.model.position.absolute;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionTest {
    @DisplayName("같은 행이면 true를 반환한다.")
    @Test
    void isSameRow() {
        //given
        Position position = new Position(Row.SIX, Column.THREE);
        Position other = new Position(Row.SIX, Column.FIVE);

        //when & then
        assertThat(position.isSameRow(other))
                .isEqualTo(true);
    }

    @DisplayName("같은 열이면 true를 반환한다.")
    @Test
    void isSameColumn() {
        //given
        Position position = new Position(Row.SIX, Column.THREE);
        Position other = new Position(Row.FIVE, Column.THREE);

        //when & then
        assertThat(position.isSameColumn(other))
                .isEqualTo(true);
    }

    @DisplayName("두 지점 사이의 가로/세로 거리 중 크키가 큰 값을 절대값으로 반환한다.")
    @Test
    void getDistanceTo() {
        //given
        Position position = new Position(Row.NINE, Column.FIVE);
        Position other = new Position(Row.SIX, Column.FOUR);

        //when & then
        assertThat(position.getDistanceTo(other))
                .isEqualTo(3);
    }

    @DisplayName("other보다 북쪽에 있는지 반환한다.")
    @Test
    void isLocatedNorthOf() {
        //given
        Position position = new Position(Row.SEVEN, Column.FIVE);
        Position other = new Position(Row.SIX, Column.FIVE);

        //when & then
        assertThat(position.isLocatedNorthOf(new Position(Row.SIX, Column.FIVE))).isFalse();
        assertThat(position.isLocatedNorthOf(new Position(Row.EIGHT, Column.FIVE))).isTrue();
    }
}
