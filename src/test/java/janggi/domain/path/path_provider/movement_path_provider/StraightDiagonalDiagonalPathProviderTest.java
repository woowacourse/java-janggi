package janggi.domain.path.path_provider.movement_path_provider;

import janggi.domain.path.Path;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static janggi.test_util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class StraightDiagonalDiagonalPathProviderTest {

    @Test
    void 직선_대각선_대각선으로_움직인_경로를_반환한다() {
        // given
        final StraightDiagonalDiagonalPathProvider sut = new StraightDiagonalDiagonalPathProvider();

        // when
        final Set<Path> result = sut.get(POSITION_5_5);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                new Path(List.of(POSITION_5_5, POSITION_4_5, POSITION_3_4, POSITION_2_3)),
                new Path(List.of(POSITION_5_5, POSITION_4_5, POSITION_3_6, POSITION_2_7)),
                new Path(List.of(POSITION_5_5, POSITION_6_5, POSITION_7_4, POSITION_8_3)),
                new Path(List.of(POSITION_5_5, POSITION_6_5, POSITION_7_6, POSITION_8_7)),
                new Path(List.of(POSITION_5_5, POSITION_5_4, POSITION_4_3, POSITION_3_2)),
                new Path(List.of(POSITION_5_5, POSITION_5_4, POSITION_6_3, POSITION_7_2)),
                new Path(List.of(POSITION_5_5, POSITION_5_6, POSITION_4_7, POSITION_3_8)),
                new Path(List.of(POSITION_5_5, POSITION_5_6, POSITION_6_7, POSITION_7_8))
        );
    }

}