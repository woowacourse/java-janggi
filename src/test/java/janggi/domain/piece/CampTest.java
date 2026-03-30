package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.exception.ExceptionMessage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CampTest {

    @ParameterizedTest
    @CsvSource({
            "CHO, HAN",
            "HAN, CHO"
    })
    void next를_호출하면_상대_진영을_반환한다(Camp current, Camp expectedResult) {
        // when
        Camp next = current.next();
        // then
        assertThat(next).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            "CHO, -1",
            "HAN, 1"
    })
    void 후진하려고하면_예외가_발생한다(Camp camp, int rowDirection) {
        assertThatThrownBy(() -> camp.validateForwardDirection(rowDirection))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_BACKWARD_MOVEMENT.getMessage());
    }
}
