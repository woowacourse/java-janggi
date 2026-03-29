package janggi.domain.dynasty;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;
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

    @Test
    @DisplayName("Dynasty는 CHO - HAN의 순서를 보장한다.")
    public void dynasty_order() throws Exception {
        // when & then
        assertThat(Dynasty.values())
                .containsExactly(CHO, HAN);
    }

}
