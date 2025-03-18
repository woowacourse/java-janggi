package domain.position;

import domain.Country;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PositionTest {

    @Test
    void 위치_생성시_랭크가_null이면_예외가_발생한다() {
        // given
        final PositionFile file = PositionFile.가;
        final PositionRank rank = null;

        // expected
        assertThatThrownBy(() -> new Position(file, rank))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("랭크는 필수값입니다.");
    }

    @Test
    void 위치_생성시_파일이_null이면_예외가_발생한다() {
        // given
        final PositionFile file = null;
        final PositionRank rank = PositionRank.of(10, Country.초나라);

        // expected
        assertThatThrownBy(() -> new Position(file, rank))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("파일은 필수값입니다.");
    }

    @Test
    void 파일과_랭크가_같으면_동일하다고_판단한다() {
        // given
        final PositionFile file1 = PositionFile.가;
        final PositionRank rank1 = PositionRank.of(10, Country.초나라);
        final PositionFile file2 = PositionFile.가;
        final PositionRank rank2 = PositionRank.of(10, Country.초나라);


        // expected
        assertThat(new Position(file1, rank1)).isEqualTo(new Position(file2, rank2));
    }



}