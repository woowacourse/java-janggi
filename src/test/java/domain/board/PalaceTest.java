package domain.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class PalaceTest {
    private final Palace palace = new Palace(new Position(4, 1));

    @Test
    void 궁성의_영역_안인지_확인한다() {
        Position position = new Position(3, 0);

        boolean inPalace = palace.isInPalace(position);
        assertThat(inPalace).isTrue();
    }

    @Test
    void 궁성의_영역_밖인지_확인한다() {
        Position position = new Position(3, 3);

        boolean inPalace = palace.isInPalace(position);
        assertThat(inPalace).isFalse();
    }

    @Test
    void 궁성의_중심인지_확인한다() {
        Position position = new Position(4, 1);

        boolean isCenter = palace.isCenter(position);
        assertThat(isCenter).isTrue();
    }

    @Test
    void 궁성의_중심이_아닌지_확인한다() {
        Position position = new Position(4, 0);

        boolean isCenter = palace.isCenter(position);
        assertThat(isCenter).isFalse();
    }

    @ParameterizedTest
    @MethodSource("cornerProvider")
    void 궁성의_코너인지_확인한다(Position position) {
        boolean isCorner = palace.isCorner(position);
        assertThat(isCorner).isTrue();
    }

    static Stream<Arguments> cornerProvider() {
        return Stream.of(
                Arguments.of(new Position(3, 0)),
                Arguments.of(new Position(3, 2)),
                Arguments.of(new Position(5, 0)),
                Arguments.of(new Position(5, 2))
        );
    }

    @Test
    void 궁성의_코너가_아닌지_확인한다() {
        Position position = new Position(3, 1);

        boolean isCorner = palace.isCorner(position);
        assertThat(isCorner).isFalse();
    }

}
