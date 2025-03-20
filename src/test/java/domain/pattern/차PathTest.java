package domain.pattern;

import domain.JanggiPosition;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 차PathTest {

    @ParameterizedTest
    @MethodSource("provide차Path")
    void 차의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // given
        int beforeRow = 0;
        int beforeColumn = 1;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);
        Path pathOf차 = new 차Path();

        // when
        List<Pattern> 차path = pathOf차.getPath(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(차path).containsAll(path);
    }

    static Stream<Arguments> provide차Path() {
        Path pathOf차 = new 차Path();
        return Stream.of(
                Arguments.of(new JanggiPosition(5, 1),
                        List.of(pathOf차.getPatterns(Direction.UP).getFirst(),
                                pathOf차.getPatterns(Direction.UP).getFirst(),
                                pathOf차.getPatterns(Direction.UP).getFirst(),
                                pathOf차.getPatterns(Direction.UP).getFirst(),
                                pathOf차.getPatterns(Direction.UP).getFirst())),
                Arguments.of(new JanggiPosition(0, 9),
                        List.of(pathOf차.getPatterns(Direction.RIGHT).getFirst(),
                                pathOf차.getPatterns(Direction.RIGHT).getFirst(),
                                pathOf차.getPatterns(Direction.RIGHT).getFirst(),
                                pathOf차.getPatterns(Direction.RIGHT).getFirst(),
                                pathOf차.getPatterns(Direction.RIGHT).getFirst(),
                                pathOf차.getPatterns(Direction.RIGHT).getFirst(),
                                pathOf차.getPatterns(Direction.RIGHT).getFirst(),
                                pathOf차.getPatterns(Direction.RIGHT).getFirst()))
        );
    }

    @Test
    void 차의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // given
        int beforeRow = 0;
        int beforeColumn = 1;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

        int afterRow = 9;
        int afterColumn = 2;
        JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

        // when & then
        Assertions.assertThatThrownBy(() -> new 차Path().getPath(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
