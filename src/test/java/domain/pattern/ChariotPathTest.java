package domain.pattern;

import static domain.Fixtures._FIVE_ONE;
import static domain.Fixtures._NINE_TWO;
import static domain.Fixtures._ZERO_NINE;
import static domain.Fixtures._ZERO_ONE;

import domain.JanggiPosition;
import domain.piece.Chariot;
import domain.piece.Piece;
import domain.piece.Side;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ChariotPathTest {
    Piece piece = new Chariot(Side.CHO);

    @ParameterizedTest
    @MethodSource("provide차Path")
    void 차의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // when
        List<Pattern> 차path = piece.findMovablePath(_ZERO_ONE, afterPosition);

        // when & then
        Assertions.assertThat(차path).containsAll(path);
    }

    static Stream<Arguments> provide차Path() {
        Path pathOf차 = new ChariotPath();
        return Stream.of(
                Arguments.of(_FIVE_ONE,
                        List.of(pathOf차.getPatterns(Direction.UP).getFirst(),
                                pathOf차.getPatterns(Direction.UP).getFirst(),
                                pathOf차.getPatterns(Direction.UP).getFirst(),
                                pathOf차.getPatterns(Direction.UP).getFirst(),
                                pathOf차.getPatterns(Direction.UP).getFirst())),
                Arguments.of(_ZERO_NINE,
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
        // when & then
        Assertions.assertThatThrownBy(() -> piece.findMovablePath(_ZERO_ONE, _NINE_TWO))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
