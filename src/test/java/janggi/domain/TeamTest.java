package janggi.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @ParameterizedTest()
    @EnumSource(value = Team.class, names = {"HAN", "CHO"}, mode = EnumSource.Mode.EXCLUDE)
    void 한나라와_초나라를_제외한_팀은_변환될_수_없다(Team team) {
        assertThatThrownBy(team::convert)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 한나라, 초나라만 변환 가능합니다.");
    }
}
