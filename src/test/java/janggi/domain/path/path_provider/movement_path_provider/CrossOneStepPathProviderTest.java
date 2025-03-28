package janggi.domain.path.path_provider.movement_path_provider;

import janggi.domain.path.Path;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static janggi.test_util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class CrossOneStepPathProviderTest {

    @Test
    void 십자모양으로_한번_움직인_위치들을_반환한다() {
        // given
        final CrossOneStepPathProvider sut = new CrossOneStepPathProvider();

        // when
        final Set<Path> result = sut.get(POSITION_5_5);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                new Path(List.of(POSITION_5_5, POSITION_5_6)),
                new Path(List.of(POSITION_5_5, POSITION_5_4)),
                new Path(List.of(POSITION_5_5, POSITION_4_5)),
                new Path(List.of(POSITION_5_5, POSITION_6_5))
        );
    }

}