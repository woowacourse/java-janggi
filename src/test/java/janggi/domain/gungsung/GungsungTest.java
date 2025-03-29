package janggi.domain.gungsung;

import janggi.domain.path.Path;
import janggi.test_util.BaseTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static janggi.test_util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class GungsungTest extends BaseTest {

    @Test
    void 궁은_해당_위치에서_시작하는_모든_일직선_Path를_반환할_수_있다() {
        // given
        final Gungsung gungSung = new Gungsung();

        // when
        final Set<Path> result = gungSung.getAllPathsFrom(POSITION_6_1);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                new Path(List.of(POSITION_6_1, POSITION_6_2, POSITION_6_3)),
                new Path(List.of(POSITION_6_1, POSITION_6_2)),
                new Path(List.of(POSITION_6_1, POSITION_5_2, POSITION_4_3)),
                new Path(List.of(POSITION_6_1, POSITION_5_2)),
                new Path(List.of(POSITION_6_1, POSITION_5_1, POSITION_4_1)),
                new Path(List.of(POSITION_6_1, POSITION_5_1))
        );
    }

}