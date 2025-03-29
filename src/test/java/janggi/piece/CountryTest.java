package janggi.piece;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CountryTest {

    @Nested
    @DisplayName("나라 생성")
    class Construct {

        @DisplayName("나라의 수가 올바른지 테스트")
        @Test
        void teamType() {
            // given
            final int expectedSize = 2;
            final List<Country> expectedTypes = List.of(
                    Country.HAN,
                    Country.CHO
            );

            // when
            final List<Country> actual = Arrays.asList(Country.values());

            // then
            Assertions.assertThat(actual)
                    .containsAll(expectedTypes)
                    .hasSize(expectedSize);
        }
    }
}
