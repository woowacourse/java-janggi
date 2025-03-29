package janggi.domain.path.path_filter;

import janggi.domain.path.Path;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static janggi.test_util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class LastPositionAllyPathFilterTest {

    @Test
    void 마지막이_우리팀으로_끝나는_경로를_필터링한다() {
        // given
        final Set<Path> paths = new HashSet<>();
        paths.add(new Path(List.of(POSITION_3_5, POSITION_3_6, POSITION_3_7, POSITION_3_8)));
        paths.add(new Path(List.of(POSITION_2_5, POSITION_2_6, POSITION_2_7, POSITION_2_8)));
        final LastPositionAllyPathFilter sut = new LastPositionAllyPathFilter();

        // when
        final Set<Path> result = sut.filter(
                paths,
                new PathFilterRequest(null,
                        List.of(new Piece(PieceType.JOL, POSITION_3_8)),
                        List.of(new Piece(PieceType.BYEONG, POSITION_2_8))
                )
        );

        // then
        assertThat(result).containsExactly(
                new Path(List.of(POSITION_2_5, POSITION_2_6, POSITION_2_7, POSITION_2_8))
        );
    }

}