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

class JumpPathFilterTest {

    @Test
    void 중간에_특정_개수만큼_뛰어넘지_않은_경로를_필터링한다() {
        // given
        final Set<Path> paths = new HashSet<>();
        paths.add(new Path(List.of(POSITION_1_6, POSITION_2_6, POSITION_3_6)));
        paths.add(new Path(List.of(POSITION_3_6, POSITION_4_6, POSITION_5_6, POSITION_6_6)));
        paths.add(new Path(List.of(POSITION_1_6, POSITION_2_6, POSITION_3_6, POSITION_4_6, POSITION_5_6, POSITION_6_6)));
        final JumpPathFilter sut = new JumpPathFilter(1);

        // when
        final Set<Path> result = sut.filter(
                paths,
                new PathFilterRequest(
                        null,
                        List.of(),
                        List.of(new Piece(PieceType.JOL, POSITION_5_6), new Piece(PieceType.BYEONG, POSITION_2_6))
                )
        );

        // then
        assertThat(result).containsExactlyInAnyOrder(
                new Path(List.of(POSITION_1_6, POSITION_2_6, POSITION_3_6)),
                new Path(List.of(POSITION_3_6, POSITION_4_6, POSITION_5_6, POSITION_6_6))
        );
    }

    @Test
    void 여러번_뛰어넘을_수_있다() {
        // given
        final Set<Path> paths = new HashSet<>();
        paths.add(new Path(List.of(POSITION_1_6, POSITION_2_6, POSITION_3_6)));
        paths.add(new Path(List.of(POSITION_3_6, POSITION_4_6, POSITION_5_6, POSITION_6_6)));
        paths.add(new Path(List.of(POSITION_1_6, POSITION_2_6, POSITION_3_6, POSITION_4_6, POSITION_5_6, POSITION_6_6)));
        final JumpPathFilter sut = new JumpPathFilter(2);

        // when
        final Set<Path> result = sut.filter(
                paths,
                new PathFilterRequest(
                        null,
                        List.of(),
                        List.of(new Piece(PieceType.JOL, POSITION_5_6), new Piece(PieceType.BYEONG, POSITION_2_6))
                )
        );

        // then
        assertThat(result).containsExactlyInAnyOrder(
                new Path(List.of(POSITION_1_6, POSITION_2_6, POSITION_3_6, POSITION_4_6, POSITION_5_6, POSITION_6_6))
        );
    }
}
