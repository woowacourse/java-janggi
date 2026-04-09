package domain.board;

import domain.place.moveStrategy.Direction;
import domain.position.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PalaceTest {

    @Test
    @DisplayName("좌표가 궁성 내부에 있다면 true 반환하는지 테스트")
    void 궁성_내부_true_테스트() {
        //given
        Position position = new Position(5, 5);

        //when
        boolean result = Palace.isInPalace(position);

        //then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("좌표가 궁성 내부에 있다면 false 반환하는지 테스트")
    void 궁성_내부_false_테스트() {
        //given
        Position position = new Position(1, 4);

        //when
        boolean result = Palace.isInPalace(position);

        //then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("궁성 내에서 연결되고 있을 때 true 반환 테스트")
    void 궁성_내부_연결_true_테스트() {
        //given
        Position position = new Position(9, 4);
        Direction direction = Direction.RIGHT;

        //when
        boolean result = Palace.isConnected(position, direction);

        //then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("궁성 내에서 연결되고 있을 때 false 반환 테스트")
    void 궁성_내부_연결_false_테스트() {
        //given
        Position position = new Position(9, 4);
        Direction direction = Direction.RIGHT_DOWN;

        //when
        boolean result = Palace.isConnected(position, direction);

        //then
        Assertions.assertThat(result).isFalse();
    }
}
