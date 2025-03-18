package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.ReplaceUnderBar;
import janggi.domain.Side;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@ReplaceUnderBar
public class CanonTest {

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 3, 4", "1, 2, 4, 5"})
    void x_y좌표가_모두_다른_위치로_움직일_수_없다(int x, int y, int moveX, int moveY) {
        Cannon cannon = new Cannon(Side.CHO, new Position(x, y));

        assertThatIllegalArgumentException()
            .isThrownBy(() -> cannon.move(moveX, moveY))
            .withMessage("해당 위치로 이동할 수 없습니다.");
    }
}
