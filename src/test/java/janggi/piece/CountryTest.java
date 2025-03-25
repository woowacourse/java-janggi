package janggi.piece;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CountryTest {

    @Nested
    @DisplayName("팀 타입 생성")
    class Construct {

        @DisplayName("팀 타입의 사이즈가 올바른지 테스트한다.")
        @Test
        void teamType() {
            // given
            final int expectedSize = 2;
            final List<Country> expectedTypes = List.of(
                    Country.HAN,
                    Country.CHO
            );

            // when
            // then
            Assertions.assertThat(Arrays.asList(Country.values()))
                    .containsAll(expectedTypes)
                    .hasSize(expectedSize);
        }
    }
}
