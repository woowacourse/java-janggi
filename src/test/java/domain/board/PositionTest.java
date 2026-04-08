package domain.board;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PositionTest {
    @Test
    void 장기판의_좌표의_범위는_가로_9칸_세로_10칸이다() {
        Position position = new Position(2, 7);

        assertThat(position).isEqualTo(new Position(2, 7));
    }

    @Test
    void 장기판의_가로_범위를_벗어날_경우_예외를_뱐환한다() {
        assertThrows(IllegalArgumentException.class, () ->
                new Position(9, 7));
    }

    @Test
    void 장기판의_세로_범위를_벗어날_경우_예외를_뱐환한다() {
        assertThrows(IllegalArgumentException.class, () ->
                new Position(7, 11));
    }

    @Test
    void 초의_궁성_영역인지_확인한다(){
        Position position = new Position(4, 0);
        boolean inChoPalace = Palace.isInAnyPalace(position);
        assertThat(inChoPalace).isTrue();
    }

    @Test
    void 한의_궁성_영역인지_확인한다(){
        Position position = new Position(4, 9);
        boolean isHanPalace = Palace.isInAnyPalace(position);
        assertThat(isHanPalace).isTrue();
    }
}
