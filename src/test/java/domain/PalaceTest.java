package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class PalaceTest {
    @ParameterizedTest
    @MethodSource("hasPalacePosition")
    void 궁성_내부에_위치하는지_확인(Position position) {

        assertThat(Palace.isPalace(position)).isTrue();
    }

    static Stream<Position> hasPalacePosition() {
        return Stream.of(
                Position.from(1,4),
                Position.from(1,5),
                Position.from(1,6),
                Position.from(2,4),
                Position.from(2,5),
                Position.from(2,6),
                Position.from(3,4),
                Position.from(3,5),
                Position.from(3,6),
                Position.from(8,4),
                Position.from(8,5),
                Position.from(8,6),
                Position.from(9,4),
                Position.from(9,5),
                Position.from(9,6),
                Position.from(10,4),
                Position.from(10,5),
                Position.from(10,6)
        );
    }

    @ParameterizedTest
    @MethodSource("hasPalaceCornerPosition")
    void 궁성_코너에_위치하는지_확인(Position position) {

        assertThat(Palace.isPalaceCorner(position)).isTrue();
    }

    static Stream<Position> hasPalaceCornerPosition() {
        return Stream.of(
                Position.from(1, 4),
                Position.from(1, 6),
                Position.from(3, 4),
                Position.from(3, 6),
                Position.from(8, 4),
                Position.from(8, 6),
                Position.from(10, 4),
                Position.from(10, 6)
        );
    }

    @ParameterizedTest
    @MethodSource("hasPalaceCenterPosition")
    void 궁성_중앙에_위치하는지_확인(Position position) {

        assertThat(Palace.isPalaceCenter(position)).isTrue();
    }

    static Stream<Position> hasPalaceCenterPosition() {
        return Stream.of(
                Position.from(2, 5),
                Position.from(9, 5)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidPalaceCenterPosition")
    void 궁성_중앙에_위치하지_않는지_확인(Position position) {

        assertThat(Palace.isPalaceCenter(position)).isFalse();
    }
    static Stream<Position> invalidPalaceCenterPosition() {
        return Stream.of(
                Position.from(1, 4),
                Position.from(1, 6),
                Position.from(3, 4),
                Position.from(3, 6),
                Position.from(8, 4),
                Position.from(8, 6),
                Position.from(10, 4),
                Position.from(10, 6)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidPalaceCornerPosition")
    void 궁성_코너에_위치하지_않는지_확인(Position position) {

        assertThat(Palace.isPalaceCorner(position)).isFalse();
    }

    static Stream<Position> invalidPalaceCornerPosition() {
        return Stream.of(
                Position.from(2, 5),
                Position.from(9, 5),
                Position.from(1, 5),
                Position.from(3, 5),
                Position.from(8, 5),
                Position.from(9, 6),
                Position.from(9, 5),
                Position.from(10, 5)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidPalacePosition")
    void 궁성_내에_위치하지_않는지_확인(Position position) {

        assertThat(Palace.isPalace(position)).isFalse();
    }

    static Stream<Position> invalidPalacePosition() {
        return Stream.of(
                Position.from(2, 3),
                Position.from(9, 7),
                Position.from(1, 3),
                Position.from(3, 7),
                Position.from(8, 3),
                Position.from(9, 7),
                Position.from(9, 3),
                Position.from(10, 7)
        );
    }
}
