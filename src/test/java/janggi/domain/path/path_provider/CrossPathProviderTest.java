package janggi.domain.path.path_provider;

import janggi.domain.path.Path;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static janggi.test_util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class CrossPathProviderTest {

    @Test
    void 십자모양으로_움직인_위치들을_반환한다() {
        // given
        final CrossPathProvider sut = new CrossPathProvider();

        // when
        final Set<Path> result = sut.get(POSITION_5_5);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                new Path(List.of(POSITION_5_5, POSITION_5_4)),
                new Path(List.of(POSITION_5_5, POSITION_5_4, POSITION_5_3)),
                new Path(List.of(POSITION_5_5, POSITION_5_4, POSITION_5_3, POSITION_5_2)),
                new Path(List.of(POSITION_5_5, POSITION_5_4, POSITION_5_3, POSITION_5_2, POSITION_5_1)),
                new Path(List.of(POSITION_5_5, POSITION_5_6)),
                new Path(List.of(POSITION_5_5, POSITION_5_6, POSITION_5_7)),
                new Path(List.of(POSITION_5_5, POSITION_5_6, POSITION_5_7, POSITION_5_8)),
                new Path(List.of(POSITION_5_5, POSITION_5_6, POSITION_5_7, POSITION_5_8, POSITION_5_9)),
                new Path(List.of(POSITION_5_5, POSITION_5_6, POSITION_5_7, POSITION_5_8, POSITION_5_9, POSITION_5_10)),
                new Path(List.of(POSITION_5_5, POSITION_4_5)),
                new Path(List.of(POSITION_5_5, POSITION_4_5, POSITION_3_5)),
                new Path(List.of(POSITION_5_5, POSITION_4_5, POSITION_3_5, POSITION_2_5)),
                new Path(List.of(POSITION_5_5, POSITION_4_5, POSITION_3_5, POSITION_2_5, POSITION_1_5)),
                new Path(List.of(POSITION_5_5, POSITION_6_5)),
                new Path(List.of(POSITION_5_5, POSITION_6_5, POSITION_7_5)),
                new Path(List.of(POSITION_5_5, POSITION_6_5, POSITION_7_5, POSITION_8_5)),
                new Path(List.of(POSITION_5_5, POSITION_6_5, POSITION_7_5, POSITION_8_5, POSITION_9_5))
        );
    }

}