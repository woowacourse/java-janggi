package janggi.domain.path.path_provider.movement_path_provider;

import janggi.domain.path.Path;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static janggi.test_util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class UpLeftRightPathProviderTest {

    @Test
    void 위와_좌우로_한번_움직인_위치들을_반환한다() {
        // given
        final UpLeftRightPathProvider sut = new UpLeftRightPathProvider();

        // when
        final Set<Path> result = sut.get(POSITION_5_5);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                new Path(List.of(POSITION_5_5, POSITION_5_6)),
                new Path(List.of(POSITION_5_5, POSITION_4_5)),
                new Path(List.of(POSITION_5_5, POSITION_6_5))
        );
    }

}