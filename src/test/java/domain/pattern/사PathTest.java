package domain.pattern;

import domain.JanggiPosition;
import domain.piece.Piece;
import domain.piece.Side;
import domain.piece.사;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 사PathTest {
    Piece piece = new 사(Side.CHO);

    @ParameterizedTest
    @MethodSource("provide사Path")
    void 사의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // given
        int beforeRow = 9;
        int beforeColumn = 5;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

        // when
        List<Pattern> 사path = piece.findPath(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(사path)
                .isEqualTo(path);
    }

    static Stream<Arguments> provide사Path() {
        Path pathOf사 = new 사Path();
        return Stream.of(
                Arguments.of(new JanggiPosition(8, 5), pathOf사.getPatterns(Direction.UP)),
                Arguments.of(new JanggiPosition(9, 4), pathOf사.getPatterns(Direction.LEFT)),
                Arguments.of(new JanggiPosition(9, 6), pathOf사.getPatterns(Direction.RIGHT)),
                Arguments.of(new JanggiPosition(0, 5), pathOf사.getPatterns(Direction.DOWN)));
    }

    @Test
    void 사의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // given
        int beforeRow = 9;
        int beforeColumn = 5;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

        int afterRow = 8;
        int afterColumn = 4;
        JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

        // when & then
        Assertions.assertThatThrownBy(() -> piece.findPath(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
