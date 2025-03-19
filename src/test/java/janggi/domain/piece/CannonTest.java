package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.ReplaceUnderBar;
import janggi.domain.Side;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@ReplaceUnderBar
class CannonTest {

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 3, 4", "1, 2, 4, 5"})
    void 이동하고자_하는_x_y좌표가_현재_x_y좌표와_모두_다르면_움직일_수_없다(int x, int y, int moveX, int moveY) {
        Cannon cannon = new Cannon(Side.CHO, new Position(x, y));

        assertThat(cannon.isMoveablePosition(new Position(moveX, moveY))).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 3, 2", "1, 2, 1, 4"})
    void 이동하고자_하는_x_y좌표가_현재_x_y좌표와_하나만_다르면_움직일_수_있다(int x, int y, int moveX, int moveY) {
        Cannon cannon = new Cannon(Side.CHO, new Position(x, y));

        assertThat(cannon.isMoveablePosition(new Position(moveX, moveY))).isTrue();
    }
}
