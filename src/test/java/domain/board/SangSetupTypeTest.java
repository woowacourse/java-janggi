package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SangSetupTypeTest {

    @Test
    void one을_입력받으면_LEFT를_반환한다() {
        int number = 1;
        assertThat(SangSetupType.from(number))
                .isEqualTo(SangSetupType.LEFT);
    }
}
