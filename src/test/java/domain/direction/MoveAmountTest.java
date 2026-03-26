package domain.direction;

import static domain.util.AssertUtils.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MoveAmountTest {

    @ParameterizedTest
    @ValueSource(ints = {
            -1, // 엣지 케이스
            -10, -100, Integer.MIN_VALUE
    })
    void 이동_거리가_음수이면_예외를_던진다(int negativeAmount) {
        assertThatThrownBy(() -> new MoveAmount(negativeAmount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {
            0, // 엣지 케이스
            1, 10, 100, Integer.MAX_VALUE
    })
    void 이동_거리가_음수가_아니면_정상적으로_생성된다(int amount) {
        assertThatNoException(() -> new MoveAmount(amount));
    }
}
