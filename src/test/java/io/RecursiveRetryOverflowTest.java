package io;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RecursiveRetryOverflowTest {

    private int callCount = 0;

    private <T> T retryUntilSuccess(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            return retryUntilSuccess(action);
        }
    }

    @Test
    void 재귀_호출이_몇_번_만에_StackOverflow가_발생하는지_확인한다() {
        Supplier<String> alwaysFails = () -> {
            callCount++;
            throw new IllegalArgumentException("항상 실패");
        };

        assertThatThrownBy(() -> retryUntilSuccess(alwaysFails))
                .isInstanceOf(StackOverflowError.class);

        System.out.println("StackOverflowError 발생 시 callCount: " + callCount);
    }
}
