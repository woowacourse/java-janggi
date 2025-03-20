package domain.pattern;

import domain.Position;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 포PathTest {

    @ParameterizedTest
    @MethodSource("provide포Path")
    void 포의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(Position afterPosition, List<Pattern> path) {
        // given
        int beforeRow = 0;
        int beforeColumn = 1;
        Position beforePosition = new Position(beforeRow, beforeColumn);
        Path pathOf포 = new 포Path();

        // when
        List<Pattern> 포path = pathOf포.getPath(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(포path).containsAll(path);
    }

    static Stream<Arguments> provide포Path() {
        Path pathOf포 = new 포Path();
        return Stream.of(
                Arguments.of(new Position(5, 1),
                        List.of(pathOf포.getPatterns(Direction.UP).get(0),
                                pathOf포.getPatterns(Direction.UP).get(0),
                                pathOf포.getPatterns(Direction.UP).get(0),
                                pathOf포.getPatterns(Direction.UP).get(0),
                                pathOf포.getPatterns(Direction.UP).get(0)
                        ),
                        Arguments.of(new Position(0, 9),
                                List.of(pathOf포.getPatterns(Direction.RIGHT).get(0),
                                        pathOf포.getPatterns(Direction.RIGHT).get(0),
                                        pathOf포.getPatterns(Direction.RIGHT).get(0),
                                        pathOf포.getPatterns(Direction.RIGHT).get(0),
                                        pathOf포.getPatterns(Direction.RIGHT).get(0),
                                        pathOf포.getPatterns(Direction.RIGHT).get(0),
                                        pathOf포.getPatterns(Direction.RIGHT).get(0),
                                        pathOf포.getPatterns(Direction.RIGHT).get(0)
                                )
                        )
                ));
    }

    @Test
    void 포의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // given
        int beforeRow = 0;
        int beforeColumn = 1;
        Position beforePosition = new Position(beforeRow, beforeColumn);

        int afterRow = 9;
        int afterColumn = 2;
        Position afterPosition = new Position(afterRow, afterColumn);

        // when & then
        Assertions.assertThatThrownBy(() -> new 포Path().getPath(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
