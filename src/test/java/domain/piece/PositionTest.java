package domain.piece;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PositionTest {

    @Test
    void 좌표를_생성한다() {
        assertThatCode(() -> new Position(1, 2))
                .doesNotThrowAnyException();
    }

    @Test
    void 잘못된_좌표_입력인_경우_예외가_발생한다() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Position(-1, 5))
                .withMessage("[ERROR] 좌표 입력은 9X10 보드 이내만 가능합니다.");
    }
}
