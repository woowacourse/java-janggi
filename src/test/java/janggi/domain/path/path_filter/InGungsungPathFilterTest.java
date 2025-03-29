package janggi.domain.path.path_filter;

import janggi.domain.gungsung.Gungsung;
import janggi.domain.path.Path;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static janggi.test_util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class InGungsungPathFilterTest {

    @Test
    void 궁_내부의_움직임이_아닌_경로를_필터링한다() {
        // given
        final Set<Path> paths = new HashSet<>();
        paths.add(new Path(List.of(POSITION_4_1, POSITION_4_2, POSITION_4_3)));
        paths.add(new Path(List.of(POSITION_6_3, POSITION_5_2)));
        paths.add(new Path(List.of(POSITION_7_4, POSITION_6_3, POSITION_5_2)));
        paths.add(new Path(List.of(POSITION_4_2, POSITION_4_3, POSITION_4_4)));
        final InGungsungPathFilter sut = new InGungsungPathFilter(new Gungsung());

        // when
        final Set<Path> result = sut.filter(
                paths,
                new PathFilterRequest(
                        null,
                        List.of(),
                        List.of()
                )
        );

        // then
        assertThat(result).containsExactlyInAnyOrder(
                new Path(List.of(POSITION_4_1, POSITION_4_2, POSITION_4_3)),
                new Path(List.of(POSITION_6_3, POSITION_5_2))
        );
    }

}