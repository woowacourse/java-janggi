package domain.util;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;

public class AssertUtils {

    public static void assertThatNoException(final ThrowingCallable code) {
        Assertions.assertThatNoException().isThrownBy(code);
    }
}
