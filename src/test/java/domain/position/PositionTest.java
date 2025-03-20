package domain.position;

import domain.Country;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static testUtil.TestConstant.*;

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

    @Test
    void 현재_위치에서_십자_모양의_위치들을_반환할_수_있다() {
        // given
        final Position curPosition = new Position(PositionFile.마, RANK_5);

        // when
        final List<Position> result = curPosition.getAllCrossPositions();

        // then
        assertThat(result).containsExactlyInAnyOrder(
                new Position(PositionFile.마, RANK_1),
                new Position(PositionFile.마, RANK_2),
                new Position(PositionFile.마, RANK_3),
                new Position(PositionFile.마, RANK_4),
                new Position(PositionFile.마, RANK_6),
                new Position(PositionFile.마, RANK_7),
                new Position(PositionFile.마, RANK_8),
                new Position(PositionFile.마, RANK_9),
                new Position(PositionFile.마, RANK_10),
                new Position(PositionFile.가, RANK_5),
                new Position(PositionFile.나, RANK_5),
                new Position(PositionFile.다, RANK_5),
                new Position(PositionFile.라, RANK_5),
                new Position(PositionFile.바, RANK_5),
                new Position(PositionFile.사, RANK_5),
                new Position(PositionFile.아, RANK_5),
                new Position(PositionFile.자, RANK_5)
        );
    }
}
