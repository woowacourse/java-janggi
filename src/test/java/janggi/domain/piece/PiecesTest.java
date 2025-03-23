package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.SetupType;
import janggi.domain.Team;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class PiecesTest {

    @ParameterizedTest
    @MethodSource("provideSetupTypeAndTargetPieces")
    @DisplayName("상차림 옵션에 따라 이에 맞는 위치에 상, 마를 배치해야 한다")
    void createPieces(SetupType setupType, List<Piece> expected) {
        //given
        //when
        Pieces pieces = Pieces.createPieces(setupType, setupType);
        //then
        assertThat(pieces.getPieces())
                .usingRecursiveFieldByFieldElementComparator()
                .containsOnlyOnceElementsOf(expected);
    }

    private static Stream<Arguments> provideSetupTypeAndTargetPieces() {
        return Stream.of(
                Arguments.of(SetupType.LEFT_ELEPHANT, List.of(
                        new Elephant(Position.of(10, 2), Team.GREEN),
                        new Elephant(Position.of(10, 7), Team.GREEN),
                        new Horse(Position.of(10, 3), Team.GREEN),
                        new Horse(Position.of(10, 8), Team.GREEN),
                        new Elephant(Position.of(1, 3), Team.RED),
                        new Elephant(Position.of(1, 8), Team.RED),
                        new Horse(Position.of(1, 2), Team.RED),
                        new Horse(Position.of(1, 7), Team.RED)
                )),
                Arguments.of(SetupType.RIGHT_ELEPHANT, List.of(
                        new Elephant(Position.of(10, 3), Team.GREEN),
                        new Elephant(Position.of(10, 8), Team.GREEN),
                        new Horse(Position.of(10, 2), Team.GREEN),
                        new Horse(Position.of(10, 7), Team.GREEN),
                        new Elephant(Position.of(1, 2), Team.RED),
                        new Elephant(Position.of(1, 7), Team.RED),
                        new Horse(Position.of(1, 3), Team.RED),
                        new Horse(Position.of(1, 8), Team.RED)
                )),
                Arguments.of(SetupType.INNER_ELEPHANT, List.of(
                        new Elephant(Position.of(10, 3), Team.GREEN),
                        new Elephant(Position.of(10, 7), Team.GREEN),
                        new Horse(Position.of(10, 2), Team.GREEN),
                        new Horse(Position.of(10, 8), Team.GREEN),
                        new Elephant(Position.of(1, 3), Team.RED),
                        new Elephant(Position.of(1, 7), Team.RED),
                        new Horse(Position.of(1, 2), Team.RED),
                        new Horse(Position.of(1, 8), Team.RED)
                )),
                Arguments.of(SetupType.OUTER_ELEPHANT, List.of(
                        new Elephant(Position.of(10, 2), Team.GREEN),
                        new Elephant(Position.of(10, 8), Team.GREEN),
                        new Horse(Position.of(10, 3), Team.GREEN),
                        new Horse(Position.of(10, 7), Team.GREEN),
                        new Elephant(Position.of(1, 2), Team.RED),
                        new Elephant(Position.of(1, 8), Team.RED),
                        new Horse(Position.of(1, 3), Team.RED),
                        new Horse(Position.of(1, 7), Team.RED)
                ))
        );
    }
}
