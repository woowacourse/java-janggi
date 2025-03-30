package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.palace.Palace;
import janggi.board.palace.PalaceGenerator;
import janggi.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PalaceTest {

    @DisplayName("궁성 영역을 가진다.")
    @Test
    void palaceArea() {
        //given
        final PalaceGenerator palaceGenerator = new PalaceGenerator();

        //when
        final Palace actual = palaceGenerator.generate();

        //then
        assertThat(actual.getArea()).hasSize(18);
    }

    @DisplayName("현재 위치가 궁성에 포함된다면 true를 반환한다.")
    @Test
    void isInPalaceByPosition() {
        //given
        final PalaceGenerator palaceGenerator = new PalaceGenerator();
        final Palace palace = palaceGenerator.generate();

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
        final PalaceGenerator palaceGenerator = new PalaceGenerator();
        final Palace palace = palaceGenerator.generate();

        final Position currentPosition = new Position(3, 3);

        //when
        final boolean actual = palace.isInPalace(currentPosition);

        //then
        assertThat(actual).isFalse();
    }

}
