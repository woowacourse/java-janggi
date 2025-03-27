package move;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import location.PathUtility;
import location.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Cannon;

public class PathUtilityTest {
    @Test
    @DisplayName("자신이 있는 위치로 이동할 수 없다.")
    void test10() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(2, 2);

        //when
        //then
        assertThatThrownBy(() -> PathUtility.checkNotSameStartWithEnd(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
