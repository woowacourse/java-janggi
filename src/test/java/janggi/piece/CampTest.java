package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CampTest {

    @DisplayName("상대 진영을 알 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "CHU, HAN",
            "HAN, CHU"
    })
    void reverseTest(Camp camp, Camp expected) {
        // when
        Camp reversed = camp.reverse();

        // then
        assertThat(reversed)
                .isSameAs(expected);
    }
}
