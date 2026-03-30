package domain.direction;

import static domain.util.AssertUtils.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MoveAmountTest {

    private static final String NEGATIVE_AMOUNT_MESSAGE = "이동 거리는 0 이상이어야 합니다.";

    @ParameterizedTest
    @ValueSource(ints = {
            -1, // 엣지 케이스
            -10, -100, Integer.MIN_VALUE
    })
    void 이동_거리가_음수이면_예외를_던진다(int negativeAmount) {
        assertThatThrownBy(() -> new MoveAmount(negativeAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NEGATIVE_AMOUNT_MESSAGE);
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
