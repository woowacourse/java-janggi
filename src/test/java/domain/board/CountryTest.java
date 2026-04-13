package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CountryTest {
    @Test
    @DisplayName("초나라 기물의 현재 위치가 궁성 내부인지 확인한다.")
    void choPieceIsInPalaceTest() {
        Country country = Country.CHO;
        Position position = new Position(4, 1);
        Position invalidPosition = new Position(0, 8);

        assertThat(country.isInPalace(position)).isTrue();
        assertThat(country.isInPalace(invalidPosition)).isFalse();
    }

    @Test
    @DisplayName("한나라 기물의 현재 위치가 궁성 내부인지 확인한다.")
    void hanPieceIsInPalaceTest() {
        Country country = Country.HAN;
        Position position = new Position(4, 8);
        Position invalidPosition = new Position(8, 9);

        assertThat(country.isInPalace(position)).isTrue();
        assertThat(country.isInPalace(invalidPosition)).isFalse();
    }
}
