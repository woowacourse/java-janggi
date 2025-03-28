package domain.janggiPiece;

import domain.path.Path;
import domain.position.JanggiPosition;
import domain.position.JanggiPositionFactory;
import domain.type.JanggiTeam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class PawnTest {

    @DisplayName("궁성 외부에서 한나라는 하, 좌, 우로만 움직일 수 있다.")
    @Test
    void redPawnNormalPosition() {
        // given
        JanggiPosition startPosition = JanggiPositionFactory.of(3, 4);
        Pawn redPawn = new Pawn(JanggiTeam.RED);
        List<Path> expected = List.of(
                new Path(List.of(
                        JanggiPositionFactory.of(4, 4)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(3, 3)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(3, 5)
                ))
        );

        // when
        List<Path> paths = redPawn.getCoordinatePaths(startPosition);

        // then
        assertThat(paths).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("궁성 외부에서 초나라는 상, 좌, 우로만 움직일 수 있다.")
    @Test
    void bluePawnNormalPosition() {
        // given
        JanggiPosition startPosition = JanggiPositionFactory.of(6, 4);
        Pawn bluePawn = new Pawn(JanggiTeam.BLUE);
        List<Path> expected = List.of(
                new Path(List.of(
                        JanggiPositionFactory.of(5, 4)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(6, 3)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(6, 5)
                ))
        );

        // when
        List<Path> paths = bluePawn.getCoordinatePaths(startPosition);

        // then
        assertThat(paths).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("한나라 - 궁성 내부에서 연결된 모든 길 중에서 전진하는 방향으로 모두 움직일 수 있다.")
    @ParameterizedTest(name = "{0}")
    @MethodSource
    void redInCastlePosition(String desc, JanggiPosition startPosition, List<Path> expected) {
        // given
        Pawn redPawn = new Pawn(JanggiTeam.RED);

        // when
        List<Path> paths = redPawn.getCoordinatePaths(startPosition);

        // then
        assertThat(paths).containsExactlyInAnyOrderElementsOf(expected);
    }

    private static Stream<Arguments> redInCastlePosition() {
        return Stream.of(
                Arguments.of(
                        "궁성의 왼쪽 상단 모서리에서 출발하는 경우",
                        JanggiPositionFactory.of(7, 3),
                        List.of(
                                new Path(List.of(
                                        JanggiPositionFactory.of(7, 2)
                                )),
                                new Path(List.of(
                                        JanggiPositionFactory.of(7, 4)
                                )),
                                new Path(List.of(
                                        JanggiPositionFactory.of(8, 3)
                                )),
                                new Path(List.of(
                                        JanggiPositionFactory.of(8, 4)
                                ))
                        )
                ),
                Arguments.of(
                        "궁성의 중앙에서 출발하는 경우",
                        JanggiPositionFactory.of(8, 4),
                        List.of(
                                new Path(List.of(
                                        JanggiPositionFactory.of(8, 3)
                                )),
                                new Path(List.of(
                                        JanggiPositionFactory.of(8, 5)
                                )),
                                new Path(List.of(
                                        JanggiPositionFactory.of(9, 3)
                                )),
                                new Path(List.of(
                                        JanggiPositionFactory.of(9, 4)
                                )),
                                new Path(List.of(
                                        JanggiPositionFactory.of(9, 5)
                                ))
                        )
                ),
                Arguments.of(
                        "궁성의 오른쪽 하단 모서리에서 출발하는 경우",
                        JanggiPositionFactory.of(9, 5),
                        List.of(
                                new Path(List.of(
                                        JanggiPositionFactory.of(9, 6)
                                )),
                                new Path(List.of(
                                        JanggiPositionFactory.of(9, 4)
                                ))
                        )
                )
        );
    }

    @DisplayName("초나라 - 궁성 내부에서 연결된 모든 길 중에서 전진하는 방향으로 모두 움직일 수 있다.")
    @ParameterizedTest(name = "{0}")
    @MethodSource
    void blueInCastlePosition(String desc, JanggiPosition startPosition, List<Path> expected) {
        // given
        Pawn redPawn = new Pawn(JanggiTeam.BLUE);

        // when
        List<Path> paths = redPawn.getCoordinatePaths(startPosition);

        // then
        assertThat(paths).containsExactlyInAnyOrderElementsOf(expected);
    }

    private static Stream<Arguments> blueInCastlePosition() {
        return Stream.of(
                Arguments.of(
                        "궁성의 왼쪽 상단 모서리에서 출발하는 경우",
                        JanggiPositionFactory.of(0, 3),
                        List.of(
                                new Path(List.of(
                                        JanggiPositionFactory.of(0, 2)
                                )),
                                new Path(List.of(
                                        JanggiPositionFactory.of(0, 4)
                                ))
                        )
                ),
                Arguments.of(
                        "궁성의 중앙에서 출발하는 경우",
                        JanggiPositionFactory.of(1, 4),
                        List.of(
                                new Path(List.of(
                                        JanggiPositionFactory.of(1, 3)
                                )),
                                new Path(List.of(
                                        JanggiPositionFactory.of(1, 5)
                                )),
                                new Path(List.of(
                                        JanggiPositionFactory.of(0, 3)
                                )),
                                new Path(List.of(
                                        JanggiPositionFactory.of(0, 4)
                                )),
                                new Path(List.of(
                                        JanggiPositionFactory.of(0, 5)
                                ))
                        )
                ),
                Arguments.of(
                        "궁성의 오른쪽 하단 모서리에서 출발하는 경우",
                        JanggiPositionFactory.of(2, 5),
                        List.of(
                                new Path(List.of(
                                        JanggiPositionFactory.of(1, 4)
                                )),
                                new Path(List.of(
                                        JanggiPositionFactory.of(2, 4)
                                )),
                                new Path(List.of(
                                        JanggiPositionFactory.of(1, 5)
                                )),
                                new Path(List.of(
                                        JanggiPositionFactory.of(2, 6)
                                ))
                        )
                )
        );
    }
}
