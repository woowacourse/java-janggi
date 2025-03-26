package domain.pattern;

import static domain.Fixtures.FIVE_ONE;
import static domain.Fixtures.NINE_TWO;
import static domain.Fixtures.ZERONINE;
import static domain.Fixtures.ZERO_ONE;

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
    @MethodSource("provideChariotPath")
    void 차의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // when
        List<Pattern> chariotPath = piece.findMovablePath(ZERO_ONE, afterPosition);

        // then
        Assertions.assertThat(chariotPath).containsAll(path);
    }

    static Stream<Arguments> provideChariotPath() {
        Path pathOfChariot = new ChariotPath();
        return Stream.of(
                Arguments.of(FIVE_ONE,
                        List.of(pathOfChariot.getPatterns(Direction.UP).getFirst(),
                                pathOfChariot.getPatterns(Direction.UP).getFirst(),
                                pathOfChariot.getPatterns(Direction.UP).getFirst(),
                                pathOfChariot.getPatterns(Direction.UP).getFirst(),
                                pathOfChariot.getPatterns(Direction.UP).getFirst())),
                Arguments.of(ZERONINE,
                        List.of(pathOfChariot.getPatterns(Direction.RIGHT).getFirst(),
                                pathOfChariot.getPatterns(Direction.RIGHT).getFirst(),
                                pathOfChariot.getPatterns(Direction.RIGHT).getFirst(),
                                pathOfChariot.getPatterns(Direction.RIGHT).getFirst(),
                                pathOfChariot.getPatterns(Direction.RIGHT).getFirst(),
                                pathOfChariot.getPatterns(Direction.RIGHT).getFirst(),
                                pathOfChariot.getPatterns(Direction.RIGHT).getFirst(),
                                pathOfChariot.getPatterns(Direction.RIGHT).getFirst()))
        );
    }

    @Test
    void 차의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // when & then
        Assertions.assertThatThrownBy(() -> piece.findMovablePath(ZERO_ONE, NINE_TWO))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
