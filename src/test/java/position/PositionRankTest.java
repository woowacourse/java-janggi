package position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static testUtil.TestConstant.RANK_1;
import static testUtil.TestConstant.RANK_2;
import static testUtil.TestConstant.RANK_3;
import static testUtil.TestConstant.RANK_4;
import static testUtil.TestConstant.RANK_5;

import game.Country;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PositionRankTest {

    @Test
    void 랭크는_1미만의_값으로_생성하려고하면_예외가_발생한다() {
        // given
        int value = 0;

        // expected
        assertThatThrownBy(() -> PositionRank.ofEachCountry(value, Country.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("랭크는 1 이상이어야 합니다.");
    }

    @Test
    void 랭크는_10초과의_값으로_생성하려고하면_예외가_발생한다() {
        // given
        int value = 11;

        // expected
        assertThatThrownBy(() -> PositionRank.ofEachCountry(value, Country.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("랭크는 10 이하이어야 합니다.");
    }

    @Test
    void 나라가_null이면_예외가_발생한다() {
        // given
        final Country country = null;

        // expected
        assertThatThrownBy(() -> PositionRank.ofEachCountry(1, country))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("나라는 필수값입니다.");
    }

    @Test
    void 랭크는_값이_같으면_같다고_판단된다() {
        // given
        final PositionRank rank1 = PositionRank.ofEachCountry(10, Country.HAN);
        final PositionRank rank2 = PositionRank.ofEachCountry(10, Country.HAN);

        // expected
        assertThat(rank1).isEqualTo(rank2);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void 랭크에_값을_더하여_다음_랭크를_구할_수_있다(int addingValue) {
        // given
        final PositionRank rank = PositionRank.ofEachCountry(5, Country.CHO);

        // when
        PositionRank newRank = rank.add(addingValue);

        // then
        assertThat(newRank).isEqualTo(PositionRank.ofEachCountry(5 + addingValue, Country.CHO));
    }

    @ParameterizedTest
    @ValueSource(ints = {-6, -5})
    void 랭크에_값을_더했을_때_1_미만이면_예외가_발생한다(int addingValue) {
        // given
        final PositionRank rank = PositionRank.ofEachCountry(5, Country.CHO);

        // expected
        assertThatThrownBy(() -> rank.add(addingValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("랭크는 1 이상이어야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 7})
    void 랭크에_값을_더했을_때_10_초과이면_예외가_발생한다(int addingValue) {
        // given
        final PositionRank rank = PositionRank.ofEachCountry(5, Country.CHO);

        // expected
        assertThatThrownBy(() -> rank.add(addingValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("랭크는 10 이하이어야 합니다.");
    }

    @Test
    void 한_랭크에서_다른_랭크까지_사이의_모든_랭크를_구할_수_있다() {
        // given
        final PositionRank from = RANK_1;
        final PositionRank to = RANK_5;

        // when
        final List<PositionRank> result = from.getBetweenRanks(to);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                RANK_2,
                RANK_3,
                RANK_4
        );
    }
}
