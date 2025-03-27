package domain.pattern;

import static domain.Fixtures.NINE_ZERO;
import static domain.Fixtures.ONE_FIVE;
import static domain.Fixtures.ONE_ZERO;
import static domain.Fixtures.TWO_NINE;

import domain.JanggiPosition;
import domain.piece.Cannon;
import domain.piece.Piece;
import domain.piece.Side;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CannonPathTest {
    Piece piece = new Cannon(Side.CHO);

    @ParameterizedTest
    @MethodSource("provideCannonPath")
    void 포의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // when
        List<Pattern> cannonPath = piece.findMovablePath(ONE_ZERO, afterPosition);

        // then
        Assertions.assertThat(cannonPath).isEqualTo(path);
    }

    static Stream<Arguments> provideCannonPath() {
        Path pathOfCannon = new Path(Direction.createChariotOrCannonPatternMap());
        return Stream.of(
                Arguments.of(ONE_FIVE,
                        List.of(pathOfCannon.getPatterns(Direction.UP).getFirst(),
                                pathOfCannon.getPatterns(Direction.UP).getFirst(),
                                pathOfCannon.getPatterns(Direction.UP).getFirst(),
                                pathOfCannon.getPatterns(Direction.UP).getFirst(),
                                pathOfCannon.getPatterns(Direction.UP).getFirst()
                        )),
                Arguments.of(NINE_ZERO,
                        List.of(pathOfCannon.getPatterns(Direction.RIGHT).getFirst(),
                                pathOfCannon.getPatterns(Direction.RIGHT).getFirst(),
                                pathOfCannon.getPatterns(Direction.RIGHT).getFirst(),
                                pathOfCannon.getPatterns(Direction.RIGHT).getFirst(),
                                pathOfCannon.getPatterns(Direction.RIGHT).getFirst(),
                                pathOfCannon.getPatterns(Direction.RIGHT).getFirst(),
                                pathOfCannon.getPatterns(Direction.RIGHT).getFirst(),
                                pathOfCannon.getPatterns(Direction.RIGHT).getFirst()
                        )
                ));
    }

    @Test
    void 포의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // when & then
        Assertions.assertThatThrownBy(() -> piece.findMovablePath(ONE_ZERO, TWO_NINE))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
