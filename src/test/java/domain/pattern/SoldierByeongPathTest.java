package domain.pattern;

import static domain.Fixtures.FIVE_EIGHT;
import static domain.Fixtures.FIVE_SEVEN;
import static domain.Fixtures.FIVE_SIX;
import static domain.Fixtures.FOUR_SEVEN;
import static domain.Fixtures.SIX_SEVEN;

import domain.JanggiPosition;
import domain.piece.Piece;
import domain.piece.Side;
import domain.piece.Soldier;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SoldierByeongPathTest {
    Piece piece = new Soldier(Side.HAN);

    @ParameterizedTest
    @MethodSource("provideByeongPath")
    void 병의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // when
        List<Pattern> byeongPath = piece.findMovablePath(FIVE_SEVEN, afterPosition);

        // then
        Assertions.assertThat(byeongPath)
                .isEqualTo(path);
    }

    static Stream<Arguments> provideByeongPath() {
        Path pathOfByeong = new Path(Direction.createByeongPatternMap());
        return Stream.of(
                Arguments.of(FIVE_EIGHT, pathOfByeong.getPatterns(Direction.DOWN)),
                Arguments.of(FOUR_SEVEN, pathOfByeong.getPatterns(Direction.LEFT)),
                Arguments.of(SIX_SEVEN, pathOfByeong.getPatterns(Direction.RIGHT))
        );
    }

    @Test
    void 병의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // when & then
        Assertions.assertThatThrownBy(() -> piece.findMovablePath(FIVE_SEVEN, FIVE_SIX))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
