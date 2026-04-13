package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.exception.BoardErrorMessage;
import domain.board.exception.InvalidSangSetupException;
import org.junit.jupiter.api.Test;

class SangSetupTypeTest {

    @Test
    void one을_입력받으면_LEFT를_반환한다() {
        int number = 1;
        assertThat(SangSetupType.from(number))
                .isEqualTo(SangSetupType.LEFT);
    }

    @Test
    void 잘못된_상차림_번호를_입력받으면_예외를_던진다() {
        int number = 0;
        assertThatThrownBy(() -> SangSetupType.from(number))
                .isInstanceOf(InvalidSangSetupException.class)
                .hasMessage(BoardErrorMessage.INVALID_SANG_SETUP.message());
    }
}
