package domain.position;

import domain.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class PositionRankTest {

    @Test
    void 랭크는_1미만의_값으로_생성하려고하면_예외가_발생한다() {
        // given
        int value = 0;

        // expected
        assertThatThrownBy(() -> PositionRank.of(value, Country.초나라))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("랭크는 1 이상이어야 합니다.");
    }

    @Test
    void 랭크는_10초과의_값으로_생성하려고하면_예외가_발생한다() {
        // given
        int value = 11;

        // expected
        assertThatThrownBy(() -> PositionRank.of(value, Country.초나라))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("랭크는 10 이하이어야 합니다.");
    }

    @Test
    void 나라가_null이면_예외가_발생한다() {
        // given
        final Country country = null;

        // expected
        assertThatThrownBy(() -> PositionRank.of(1, country))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("나라는 필수값입니다.");
    }

    @Test
    void 랭크는_값이_같으면_같다고_판단된다() {
        // given
        final PositionRank rank1 = PositionRank.of(10, Country.한나라);
        final PositionRank rank2 = PositionRank.of(10, Country.한나라);

        // expected
        assertThat(rank1).isEqualTo(rank2);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void 랭크에_값을_더하여_다음_랭크를_구할_수_있다(int addingValue) {
        // given
        final PositionRank rank = PositionRank.of(5, Country.초나라);

        // when
        PositionRank newRank = rank.add(addingValue);

        // then
        assertThat(newRank).isEqualTo(PositionRank.of(5 + addingValue, Country.초나라));
    }

    @ParameterizedTest
    @ValueSource(ints = {-6, -5})
    void 랭크에_값을_더했을_때_1_미만이면_예외가_발생한다(int addingValue) {
        // given
        final PositionRank rank = PositionRank.of(5, Country.초나라);

        // expected
        assertThatThrownBy(() -> rank.add(addingValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("랭크는 1 이상이어야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 7})
    void 랭크에_값을_더했을_때_10_초과이면_예외가_발생한다(int addingValue) {
        // given
        final PositionRank rank = PositionRank.of(5, Country.초나라);

        // expected
        assertThatThrownBy(() -> rank.add(addingValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("랭크는 10 이하이어야 합니다.");
    }
}
