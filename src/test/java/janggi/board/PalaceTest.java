package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.palace.Palace;
import janggi.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PalaceTest {

    @DisplayName("궁성 영역을 가진다.")
    @Test
    void palaceArea() {
        //given //when
        final Palace actual = Palace.AREA;

        //then
        assertThat(actual.getArea()).hasSize(18);
    }

    @DisplayName("현재 위치가 궁성에 포함된다면 true를 반환한다.")
    @Test
    void isInPalaceByPosition() {
        //given
        final Palace palace = Palace.AREA;

        final Position currentPosition = new Position(0, 3);

        //when
        final boolean actual = palace.isInPalace(currentPosition);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("현재 위치가 궁성에 포함되지 않는다면 false를 반환한다.")
    @Test
    void isOutPalaceByPosition() {
        //given
        final Palace palace = Palace.AREA;

        final Position currentPosition = new Position(3, 3);

        //when
        final boolean actual = palace.isInPalace(currentPosition);

        //then
        assertThat(actual).isFalse();
    }

}
