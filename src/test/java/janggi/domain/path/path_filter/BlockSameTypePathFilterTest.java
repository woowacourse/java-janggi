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

class BlockSameTypePathFilterTest {

    @Test
    void 중간에_동일한_종류의_기물에_의해_막힌_경로를_필터링한다() {
        // given
        final Set<Path> paths = new HashSet<>();
        paths.add(new Path(List.of(POSITION_3_5, POSITION_3_6, POSITION_3_7, POSITION_3_8)));
        paths.add(new Path(List.of(POSITION_2_5, POSITION_2_6, POSITION_2_7, POSITION_2_8)));
        final BlockSameTypePathFilter sut = new BlockSameTypePathFilter();

        // when
        final Set<Path> result = sut.filter(
                paths,
                new PathFilterRequest(
                        new Piece(PieceType.JOL, POSITION_1_1),
                        List.of(),
                        List.of(new Piece(PieceType.JOL, POSITION_3_6), new Piece(PieceType.BYEONG, POSITION_2_6))
                )
        );

        // then
        assertThat(result).containsExactly(
                new Path(List.of(POSITION_2_5, POSITION_2_6, POSITION_2_7, POSITION_2_8))
        );
    }

}
