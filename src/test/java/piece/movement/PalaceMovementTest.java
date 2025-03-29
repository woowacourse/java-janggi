package piece.movement;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import board.Position;

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

}
