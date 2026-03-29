package janggi.domain.dynasty;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;


class DynastyTest {
    
    @ParameterizedTest
    @CsvSource(value = {
            "CHO, HAN",
            "HAN, CHO",
    })
    @DisplayName("다음 Dynasty를 반환한다.")
    public void next_success(Dynasty before, Dynasty after) throws Exception {

        // when
        Dynasty result = before.next();

        // then
        assertThat(result).isEqualTo(after);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1, CHO, 1",
            "1, HAN, 10",
    })
    @DisplayName("Dynasty 타입에 맞게 row를 처리한다")
    public void resolveRow_success(int origin, Dynasty dynasty, int result) throws Exception {
        // when
        int resolved = dynasty.resolveRow(origin);

        // then
        assertThat(resolved).isEqualTo(result);
    }

}
