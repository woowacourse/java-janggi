package janggi.domain.path.path_provider;

import janggi.domain.gungsung.Gungsung;
import janggi.domain.path.Path;
import janggi.domain.position.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static janggi.test_util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class GungsungPathProviderTest {

    @ParameterizedTest
    @MethodSource("provideGungStartPositionAndResult")
    void 궁에서_움직인_위치들을_반환한다(Position start, List<Path> expectedResult) {
        // given
        final GungsungOneStepPathProvider sut = new GungsungOneStepPathProvider(new Gungsung());

        // when
        final Set<Path> result = sut.get(start);

        // then
        assertThat(result).containsExactlyInAnyOrderElementsOf(expectedResult);
    }

    public static Stream<Arguments> provideGungStartPositionAndResult() {
        return Stream.of(
                Arguments.of(POSITION_4_1, List.of(
                        new Path(List.of(POSITION_4_1, POSITION_5_1)),
                        new Path(List.of(POSITION_4_1, POSITION_4_2)),
                        new Path(List.of(POSITION_4_1, POSITION_5_2))
                )),
                Arguments.of(POSITION_5_1, List.of(
                        new Path(List.of(POSITION_5_1, POSITION_5_2)),
                        new Path(List.of(POSITION_5_1, POSITION_6_1)),
                        new Path(List.of(POSITION_5_1, POSITION_4_1))
                )),
                Arguments.of(POSITION_5_2, List.of(
                        new Path(List.of(POSITION_5_2, POSITION_4_1)),
                        new Path(List.of(POSITION_5_2, POSITION_5_1)),
                        new Path(List.of(POSITION_5_2, POSITION_6_1)),
                        new Path(List.of(POSITION_5_2, POSITION_4_2)),
                        new Path(List.of(POSITION_5_2, POSITION_6_2)),
                        new Path(List.of(POSITION_5_2, POSITION_4_3)),
                        new Path(List.of(POSITION_5_2, POSITION_5_3)),
                        new Path(List.of(POSITION_5_2, POSITION_6_3))
                )),
                Arguments.of(POSITION_5_5, List.of())
        );
    }

}