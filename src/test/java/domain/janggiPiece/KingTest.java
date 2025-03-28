package domain.janggiPiece;

import domain.path.Path;
import domain.position.JanggiPosition;
import domain.position.JanggiPositionFactory;
import domain.type.JanggiTeam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class KingTest {
    @DisplayName("궁성 외부를 제외한 모든 이동 경로를 반환할 수 있다.")
    @ParameterizedTest
    @MethodSource
    void kingTest(JanggiPosition startPosition, List<Path> expected) {
        // given
        King king = new King(JanggiTeam.RED);

        // when
        List<Path> paths = king.getCoordinatePaths(startPosition);

        // then
        assertThat(paths).containsExactlyInAnyOrderElementsOf(expected);
    }

    private static Stream<Arguments> kingTest() {
        return Stream.of(
                Arguments.of(
                        JanggiPositionFactory.of(0, 3),
                        List.of(
                                new Path(List.of(
                                        JanggiPositionFactory.of(1, 3))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(1, 4))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(0, 4))
                                )
                        )
                ),
                Arguments.of(
                        JanggiPositionFactory.of(0, 4),
                        List.of(
                                new Path(List.of(
                                        JanggiPositionFactory.of(0, 3))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(1, 4))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(0, 5))
                                )
                        )
                ),
                Arguments.of(
                        JanggiPositionFactory.of(0, 5),
                        List.of(
                                new Path(List.of(
                                        JanggiPositionFactory.of(0, 4))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(1, 4))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(1, 5))
                                )
                        )
                ),
                Arguments.of(
                        JanggiPositionFactory.of(1, 3),
                        List.of(
                                new Path(List.of(
                                        JanggiPositionFactory.of(0, 3))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(1, 4))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(2, 3))
                                )
                        )
                ),
                Arguments.of(
                        JanggiPositionFactory.of(1, 4),
                        List.of(
                                new Path(List.of(
                                        JanggiPositionFactory.of(0, 3))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(0, 4))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(0, 5))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(1, 3))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(1, 5))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(2, 3))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(2, 4))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(2, 5))
                                )
                        )
                ),
                Arguments.of(
                        JanggiPositionFactory.of(1, 5),
                        List.of(
                                new Path(List.of(
                                        JanggiPositionFactory.of(0, 5))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(1, 4))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(2, 5))
                                )
                        )
                ),
                Arguments.of(
                        JanggiPositionFactory.of(2, 3),
                        List.of(
                                new Path(List.of(
                                        JanggiPositionFactory.of(1, 3))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(1, 4))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(2, 4))
                                )
                        )
                ),
                Arguments.of(
                        JanggiPositionFactory.of(2, 4),
                        List.of(
                                new Path(List.of(
                                        JanggiPositionFactory.of(2, 3))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(1, 4))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(2, 5))
                                )
                        )
                ),
                Arguments.of(
                        JanggiPositionFactory.of(2, 5),
                        List.of(
                                new Path(List.of(
                                        JanggiPositionFactory.of(2, 4))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(1, 4))
                                ),
                                new Path(List.of(
                                        JanggiPositionFactory.of(1, 5))
                                )
                        )
                )
        );
    }
}
