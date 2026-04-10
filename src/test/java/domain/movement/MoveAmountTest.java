package domain.movement;

import static domain.util.AssertUtils.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
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
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 거리는 0 이상이어야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {
            0, // 엣지 케이스
            1, 10, 100, Integer.MAX_VALUE
    })
    void 이동_거리가_음수가_아니면_정상적으로_생성된다(int amount) {
        assertThatNoException(() -> new MoveAmount(amount));
    }

    @Nested
    class 이동_거리가_외부_값_이상인지_판단한다 {

        @ParameterizedTest
        @ValueSource(ints = {
                4, 5, // 엣지 케이스
                0, -100, Integer.MIN_VALUE
        })
        void 이동_거리가_외부_값보다_크거나_같다면_true를_반환한다(int lessAmount) {
            // given
            MoveAmount moveAmount = new MoveAmount(5);

            // when
            boolean greaterOrEqual = moveAmount.isGreaterOrEqual(lessAmount);
            
            // then
            assertThat(greaterOrEqual).isTrue();
        }

        @ParameterizedTest
        @ValueSource(ints = {
                6, // 엣지 케이스
                10, 100, Integer.MAX_VALUE
        })
        void 이동_거리가_외부_값보다_작다면_false를_반환한다(int greaterAmount) {
            // given
            MoveAmount moveAmount = new MoveAmount(5);

            // when
            boolean greaterOrEqual = moveAmount.isGreaterOrEqual(greaterAmount);

            // then
            assertThat(greaterOrEqual).isFalse();
        }
    }
}
