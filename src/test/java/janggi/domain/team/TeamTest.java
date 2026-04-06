package janggi.domain.team;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class TeamTest {

    @ParameterizedTest(name = "rowDiff= {0} -> 후진= {1}")
    @CsvSource({
            "-1, true",
            "-2, true",
            "0, false",
            "1, false"})
    void 한나라의_후진_여부를_확인한다(int rowDiff, boolean expected) {
        // when
        boolean result = Team.HAN.isBackward(rowDiff);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest(name = "rowDiff={0} -> 후진={1}")
    @CsvSource({
            "-1, false",
            "-2, false",
            "0, false",
            "1, true"})
    void 초나라의_후진_여부를_확인한다(int rowDiff, boolean expected) {
        // when
        boolean result = Team.CHO.isBackward(rowDiff);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest(name = "전={0}, 후={1}")
    @CsvSource({
            "HAN, CHO",
            "CHO, HAN"})
    void 한나라와_초나라는_서로_변환된다(Team target, Team expected) {
        // when
        Team result = target.convert();

        // then
        assertThat(result).isEqualTo(expected);
    }
}
