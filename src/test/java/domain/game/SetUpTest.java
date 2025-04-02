package domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.piece.Piece;
import domain.position.Position;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SetUpTest {

    private static Stream<Arguments> setUpAndPositions() {
        return Stream.of(
                Arguments.of(SetUp.INNER_ELEPHANT,
                        Position.of(2, 1),
                        Position.of(8, 1),
                        Position.of(3, 1),
                        Position.of(7, 1),
                        Function.identity()), // 한 팀
                Arguments.of(SetUp.OUTER_ELEPHANT,
                        Position.of(3, 10),
                        Position.of(7, 10),
                        Position.of(2, 10),
                        Position.of(8, 10),
                        (Function<Position, Position>) Position::flipUpDown), // 초 팀
                Arguments.of(SetUp.LEFT_ELEPHANT,
                        Position.of(3, 1),
                        Position.of(8, 1),
                        Position.of(2, 1),
                        Position.of(7, 1),
                        Function.identity()), // 한 팀
                Arguments.of(SetUp.RIGHT_ELEPHANT,
                        Position.of(2, 10),
                        Position.of(7, 10),
                        Position.of(3, 10),
                        Position.of(8, 10),
                        (Function<Position, Position>) Position::flipUpDown) // 초 팀
        );
    }

    @ParameterizedTest
    @MethodSource("setUpAndPositions")
    void 상차림을_초기화한다(final SetUp setUp, final Position horsePosition1, final Position horsePosition2,
                    final Position elephantPosition1, final Position elephantPosition2,
                    final Function<Position, Position> teamSide) {
        // when
        List<Piece> pieces = setUp.initializeHorseAndElephant(teamSide);

        // then
        assertAll(() -> {
            assertThat(pieces.get(0).getPosition()).isEqualTo(horsePosition1);
            assertThat(pieces.get(1).getPosition()).isEqualTo(horsePosition2);
            assertThat(pieces.get(2).getPosition()).isEqualTo(elephantPosition1);
            assertThat(pieces.get(3).getPosition()).isEqualTo(elephantPosition2);
        });
    }
}
