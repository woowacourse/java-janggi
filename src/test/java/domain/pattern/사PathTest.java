package domain.pattern;

import domain.Position;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 사PathTest {

    @ParameterizedTest
    @MethodSource("provide사Path")
    void 사의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(Position afterPosition, List<Pattern> path) {
        // given
        int beforeRow = 9;
        int beforeColumn = 5;
        Position beforePosition = new Position(beforeRow, beforeColumn);
        Path pathOf사 = new 사Path();

        // when
        List<Pattern> 사path = pathOf사.getPath(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(사path)
                .isEqualTo(path);
    }

    static Stream<Arguments> provide사Path() {
        Path pathOf사 = new 사Path();
        return Stream.of(
                Arguments.of(new Position(8, 5), pathOf사.getPatterns(Direction.UP)),
                Arguments.of(new Position(9, 4), pathOf사.getPatterns(Direction.LEFT)),
                Arguments.of(new Position(9, 6), pathOf사.getPatterns(Direction.RIGHT)),
                Arguments.of(new Position(0, 5), pathOf사.getPatterns(Direction.DOWN)));
    }

    @Test
    void 사의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // given
        int beforeRow = 9;
        int beforeColumn = 5;
        Position beforePosition = new Position(beforeRow, beforeColumn);

        int afterRow = 8;
        int afterColumn = 4;
        Position afterPosition = new Position(afterRow, afterColumn);

        // when & then
        Assertions.assertThatThrownBy(() -> new 사Path().getPath(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
