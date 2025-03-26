package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CountryTest {

    @DisplayName("나라(턴)를 교체한다.")
    @Test
    void countryTest1() {
        Country country = Country.CHO;

        Country convertedTurn = country.convertCountry();

        assertThat(convertedTurn).isEqualTo(Country.HAN);
    }
}
