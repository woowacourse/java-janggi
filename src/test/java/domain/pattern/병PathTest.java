package domain.pattern;

import domain.JanggiPosition;
import domain.piece.Piece;
import domain.piece.Side;
import domain.piece.졸병;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 병PathTest {
    Piece piece = new 졸병(Side.HAN);

    @ParameterizedTest
    @MethodSource("provide병Path")
    void 병의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // given
        JanggiPosition beforePosition = new JanggiPosition(7, 5);

        // when
        List<Pattern> 병path = piece.findPath(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(병path)
                .isEqualTo(path);
    }

    static Stream<Arguments> provide병Path() {
        Path pathOf병 = new 병Path();
        return Stream.of(
                Arguments.of(new JanggiPosition(8, 5), pathOf병.getPatterns(Direction.DOWN)),
                Arguments.of(new JanggiPosition(7, 4), pathOf병.getPatterns(Direction.LEFT)),
                Arguments.of(new JanggiPosition(7, 6), pathOf병.getPatterns(Direction.RIGHT))
        );
    }

    @Test
    void 병의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // given
        JanggiPosition beforePosition = new JanggiPosition(7, 5);
        JanggiPosition afterPosition = new JanggiPosition(6, 5);

        // when & then
        Assertions.assertThatThrownBy(() -> piece.findPath(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }

}
