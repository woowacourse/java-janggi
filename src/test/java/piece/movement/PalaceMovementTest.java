package piece.movement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static piece.Direction.LEFT_BOTTOM;
import static piece.Direction.LEFT_TOP;
import static piece.Direction.RIGHT_BOTTOM;
import static piece.Direction.RIGHT_TOP;
import static piece.movement.PalaceMovement.getMatchedDiagonalDirections;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import board.Position;
import piece.Direction;

class PalaceMovementTest {

    @MethodSource
    @ParameterizedTest
    void 궁성_영역의_가운데_지점의_움직임을_계산한다(Position position, Set<Position> expected) {
        assertThat(PalaceMovement.applyMovement(position))
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    private static Stream<Arguments> 궁성_영역의_가운데_지점의_움직임을_계산한다() {
        return Stream.of(
                Arguments.of(new Position(2, 5), Set.of(
                        new Position(1, 4), new Position(1, 5), new Position(1, 6),
                        new Position(2, 4), new Position(2, 6),
                        new Position(3, 4), new Position(3, 5), new Position(3, 6)
                )),
                Arguments.of(new Position(9, 5), Set.of(
                        new Position(8, 4), new Position(8, 5), new Position(8, 6),
                        new Position(9, 4), new Position(9, 6),
                        new Position(10, 4), new Position(10, 5), new Position(10, 6)
                ))
        );
    }

    @MethodSource
    @ParameterizedTest
    void 궁성_영역의_왼쪽_위_꼭짓점_움직임을_계산한다(Position position, Set<Position> expected) {
        assertThat(PalaceMovement.applyMovement(position))
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    private static Stream<Arguments> 궁성_영역의_왼쪽_위_꼭짓점_움직임을_계산한다() {
        return Stream.of(
                Arguments.of(new Position(1, 4), Set.of(
                        new Position(1, 3), new Position(1, 5),
                        new Position(2, 4), new Position(2, 5)
                )),
                Arguments.of(new Position(8, 4), Set.of(
                        new Position(7, 4), new Position(8, 3), new Position(8, 5),
                        new Position(9, 4), new Position(9, 5)
                ))
        );
    }

    @MethodSource
    @ParameterizedTest
    void 궁성_영역의_오른쪽_위_꼭짓점_움직임을_계산한다(Position position, Set<Position> expected) {
        assertThat(PalaceMovement.applyMovement(position))
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    private static Stream<Arguments> 궁성_영역의_오른쪽_위_꼭짓점_움직임을_계산한다() {
        return Stream.of(
                Arguments.of(new Position(1, 6), Set.of(
                        new Position(1, 5), new Position(1, 7),
                        new Position(2, 5), new Position(2, 6)
                )),
                Arguments.of(new Position(8, 6), Set.of(
                        new Position(7, 6), new Position(8, 5), new Position(8, 7),
                        new Position(9, 5), new Position(9, 6)
                ))
        );
    }

    @MethodSource
    @ParameterizedTest
    void 궁성_영역의_왼쪽_아래_꼭짓점_움직임을_계산한다(Position position, Set<Position> expected) {
        assertThat(PalaceMovement.applyMovement(position))
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    private static Stream<Arguments> 궁성_영역의_왼쪽_아래_꼭짓점_움직임을_계산한다() {
        return Stream.of(
                Arguments.of(new Position(3, 4), Set.of(
                        new Position(2, 4), new Position(4, 4),
                        new Position(2, 5), new Position(3, 3), new Position(3, 5)
                )),
                Arguments.of(new Position(10, 4), Set.of(
                        new Position(9, 4), new Position(9, 5),
                        new Position(10, 5), new Position(10, 3)
                ))
        );
    }

    @MethodSource
    @ParameterizedTest
    void 궁성_영역의_오른쪽_아래_꼭짓점_움직임을_계산한다(Position position, Set<Position> expected) {
        assertThat(PalaceMovement.applyMovement(position))
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    private static Stream<Arguments> 궁성_영역의_오른쪽_아래_꼭짓점_움직임을_계산한다() {
        return Stream.of(
                Arguments.of(new Position(3, 6), Set.of(
                        new Position(2, 6), new Position(2, 5),
                        new Position(3, 5), new Position(3, 7), new Position(4, 6)
                )),
                Arguments.of(new Position(10, 6), Set.of(
                        new Position(9, 6), new Position(9, 5),
                        new Position(10, 5), new Position(10, 7)
                ))
        );
    }

    @MethodSource
    @ParameterizedTest
    void 궁성_영역_대변_가운데_지점의_움직임을_계산한다(Position position, Set<Position> expected) {
        assertThat(PalaceMovement.applyMovement(position))
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    private static Stream<Arguments> 궁성_영역_대변_가운데_지점의_움직임을_계산한다() {
        return Stream.of(
                Arguments.of(new Position(1, 5), Set.of(
                        new Position(2, 5), new Position(1, 4), new Position(1, 6)
                )),
                Arguments.of(new Position(2, 4), Set.of(
                        new Position(2, 3), new Position(2, 5), new Position(1, 4), new Position(3, 4)
                )),
                Arguments.of(new Position(2, 6), Set.of(
                        new Position(2, 5), new Position(2, 7), new Position(1, 6), new Position(3, 6)
                )),
                Arguments.of(new Position(3, 5), Set.of(
                        new Position(3, 4), new Position(3, 6), new Position(2, 5), new Position(4, 5)
                )),
                Arguments.of(new Position(8, 5), Set.of(
                        new Position(7, 5), new Position(9, 5), new Position(8, 4), new Position(8, 6)
                )),
                Arguments.of(new Position(9, 4), Set.of(
                        new Position(9, 3), new Position(9, 5), new Position(8, 4), new Position(10, 4)
                )),
                Arguments.of(new Position(9, 6), Set.of(
                        new Position(9, 5), new Position(9, 7), new Position(8, 6), new Position(10, 6)
                )),
                Arguments.of(new Position(10, 5), Set.of(
                        new Position(10, 4), new Position(10, 6), new Position(9, 5)
                ))
        );
    }

    @MethodSource
    @ParameterizedTest
    void 궁성_영역이_아니면_움직임을_적용할_수_없다(Position position) {
        assertThat(PalaceMovement.applyMovement(position)).isEmpty();
    }

    private static Stream<Arguments> 궁성_영역이_아니면_움직임을_적용할_수_없다() {
        return Stream.of(
                Arguments.of(new Position(2, 3)),
                Arguments.of(new Position(2, 7)),
                Arguments.of(new Position(4, 5)),
                Arguments.of(new Position(9, 3)),
                Arguments.of(new Position(9, 7)),
                Arguments.of(new Position(7, 5))
        );
    }

    @CsvSource(value = {
            "2,5,true", "9,5,true",
            "1,4,true", "8,4,true",
            "1,6,true", "8,6,true",
            "3,4,true", "10,4,true",
            "3,6,true", "10,6,true",
            "1,5,false", "2,4,false", "2,6,false", "3,5,false",
            "8,5,false", "9,4,false", "9,6,false", "10,5,false"
    })
    @ParameterizedTest
    void 궁성_영역의_위치가_대각선_방향_움직임을_가지고_있는지_알려준다(int row, int column, boolean expected) {
        assertThat(PalaceMovement.hasDiagonalDirectionPosition(new Position(row, column)))
                .isEqualTo(expected);
    }

    @MethodSource
    @ParameterizedTest
    void 위치와_맞는_궁성_영역의_대각선_방향을_돌려준다(int row, int column, List<Direction> expected) {
        assertThat(getMatchedDiagonalDirections(new Position(row, column)))
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    private static Stream<Arguments> 위치와_맞는_궁성_영역의_대각선_방향을_돌려준다() {
        return Stream.of(
                Arguments.of(2, 5, List.of(LEFT_TOP, RIGHT_TOP, LEFT_BOTTOM, RIGHT_BOTTOM)),
                Arguments.of(9, 5, List.of(LEFT_TOP, RIGHT_TOP, LEFT_BOTTOM, RIGHT_BOTTOM)),
                Arguments.of(1, 4, List.of(RIGHT_BOTTOM)),
                Arguments.of(8, 4, List.of(RIGHT_BOTTOM)),
                Arguments.of(1, 6, List.of(LEFT_BOTTOM)),
                Arguments.of(8, 6, List.of(LEFT_BOTTOM)),
                Arguments.of(3, 4, List.of(RIGHT_TOP)),
                Arguments.of(10, 4, List.of(RIGHT_TOP)),
                Arguments.of(3, 6, List.of(LEFT_TOP)),
                Arguments.of(10, 6, List.of(LEFT_TOP)),
                Arguments.of(1, 5, List.of())
        );
    }

    @Test
    void 궁성_영역이_아닌_위치는_대각선_방향을_돌려줄_수_없다() {
        assertThatThrownBy(() -> getMatchedDiagonalDirections(new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
