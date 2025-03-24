package janggi.view;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.piece.Camp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CampFormatterTest {

    @DisplayName("진영의 출력 형태로 포맷팅 할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "CHU, 초",
            "HAN, 한"
    })
    void formatTest(Camp camp, String expected) {
        // when
        String formatted = CampFormatter.format(camp);

        // then
        assertThat(formatted)
                .isEqualTo(expected);
    }
}
