package domain.position;

import domain.Country;
import org.junit.jupiter.api.Test;

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

        // when
        assertThat(rank1).isEqualTo(rank2);
    }
}
