package janggi.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SetupCommandTest {

    @Test
    @DisplayName("차림판 예외 테스트")
    void normalInputTest() {
        int wrongNumber = 5;
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new SetupCommand(wrongNumber));
    }
}
