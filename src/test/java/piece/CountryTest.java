package piece;

import domain.piece.Country;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class CountryTest {

    @Nested
    @DisplayName("팀 타입 생성")
    class Construct {

        @DisplayName("팀 타입의 사이즈가 올바른지 테스트한다.")
        @Test
        void size() {
            // given
            final int expectedSize = 2;
            final List<Country> expectedTypes = List.of(
                    Country.CHO,
                    Country.HAN
            );

            // when
            // then
            Assertions.assertThat(Arrays.asList(Country.values()))
                    .containsAll(expectedTypes)
                    .hasSize(expectedSize);
        }
    }
}
