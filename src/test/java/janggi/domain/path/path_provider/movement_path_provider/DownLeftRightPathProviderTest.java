package janggi.domain.path.path_provider.movement_path_provider;

import janggi.domain.path.Path;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static janggi.test_util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class DownLeftRightPathProviderTest {

    @Test
    void 아래와_좌우로_한번_움직인_위치들을_반환한다() {
        // given
        final DownLeftRightPathProvider sut = new DownLeftRightPathProvider();

        // when
        final Set<Path> result = sut.get(POSITION_5_5);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                new Path(List.of(POSITION_5_5, POSITION_5_4)),
                new Path(List.of(POSITION_5_5, POSITION_4_5)),
                new Path(List.of(POSITION_5_5, POSITION_6_5))
        );
    }

}