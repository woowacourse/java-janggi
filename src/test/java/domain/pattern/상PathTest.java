package domain.pattern;

import domain.JanggiPosition;
import domain.piece.Piece;
import domain.piece.Side;
import domain.piece.상;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 상PathTest {
    Piece piece = new 상(Side.CHO);

    @ParameterizedTest
    @MethodSource("provide상Path")
    void 상의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // given
        int beforeRow = 6;
        int beforeColumn = 5;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

        // when
        List<Pattern> 상path = piece.findPath(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(상path)
                .isEqualTo(path);
    }

    static Stream<Arguments> provide상Path() {
        Path pathOf상 = new 상Path();
        return Stream.of(
                Arguments.of(new JanggiPosition(4, 8), pathOf상.getPatterns(Direction.RIGHT_UP)),
                Arguments.of(new JanggiPosition(8, 8), pathOf상.getPatterns(Direction.RIGHT_DOWN)),
                Arguments.of(new JanggiPosition(9, 7), pathOf상.getPatterns(Direction.DOWN_RIGHT)),
                Arguments.of(new JanggiPosition(9, 3), pathOf상.getPatterns(Direction.DOWN_LEFT)),
                Arguments.of(new JanggiPosition(8, 2), pathOf상.getPatterns(Direction.LEFT_DOWN)),
                Arguments.of(new JanggiPosition(4, 2), pathOf상.getPatterns(Direction.LEFT_UP)),
                Arguments.of(new JanggiPosition(3, 3), pathOf상.getPatterns(Direction.UP_LEFT)),
                Arguments.of(new JanggiPosition(3, 7), pathOf상.getPatterns(Direction.UP_RIGHT))
        );
    }

    @Test
    void 상의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // given
        int beforeRow = 6;
        int beforeColumn = 5;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

        int afterRow = 4;
        int afterColumn = 6;
        JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

        // when & then
        Assertions.assertThatThrownBy(() -> piece.findPath(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
