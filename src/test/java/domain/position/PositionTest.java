package domain.position;

import domain.BaseTest;
import domain.Country;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.position.PositionFile.*;
import static domain.position.PositionRank.*;
import static org.assertj.core.api.Assertions.*;

class PositionTest extends BaseTest {

    @Test
    void 위치_생성시_랭크가_null이면_예외가_발생한다() {
        // given
        final PositionFile file = FILE_1;
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
        final PositionRank rank = of(10, Country.CHO);

        // expected
        assertThatThrownBy(() -> new Position(file, rank))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("파일은 필수값입니다.");
    }

    @Test
    void 파일과_랭크가_같으면_동일하다고_판단한다() {
        // given
        final PositionFile file1 = FILE_1;
        final PositionRank rank1 = of(10, Country.CHO);
        final PositionFile file2 = FILE_1;
        final PositionRank rank2 = of(10, Country.CHO);


        // expected
        assertThat(new Position(file1, rank1)).isEqualTo(new Position(file2, rank2));
    }

    @Test
    void 현재_위치에서_십자_모양의_위치들을_반환할_수_있다() {
        // given
        final Position curPosition = new Position(FILE_5, RANK_5);

        // when
        final List<Position> result = curPosition.getAllCrossPositions();

        // then
        assertThat(result).containsExactlyInAnyOrder(
                new Position(FILE_5, RANK_1),
                new Position(FILE_5, RANK_2),
                new Position(FILE_5, RANK_3),
                new Position(FILE_5, RANK_4),
                new Position(FILE_5, RANK_6),
                new Position(FILE_5, RANK_7),
                new Position(FILE_5, RANK_8),
                new Position(FILE_5, RANK_9),
                new Position(FILE_5, RANK_10),
                new Position(FILE_1, RANK_5),
                new Position(FILE_2, RANK_5),
                new Position(FILE_3, RANK_5),
                new Position(FILE_4, RANK_5),
                new Position(FILE_6, RANK_5),
                new Position(FILE_7, RANK_5),
                new Position(FILE_8, RANK_5),
                new Position(FILE_9, RANK_5)
        );
    }
}
