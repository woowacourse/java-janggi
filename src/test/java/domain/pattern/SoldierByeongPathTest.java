package domain.pattern;

import static domain.Fixtures.EIGHT_FIVE;
import static domain.Fixtures.SEVEN_FIVE;
import static domain.Fixtures.SEVEN_FOUR;
import static domain.Fixtures.SEVEN_SIX;
import static domain.Fixtures.SIX_FIVE;

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
        List<Pattern> byeongPath = piece.findMovablePath(SEVEN_FIVE, afterPosition);

        // then
        Assertions.assertThat(byeongPath)
                .isEqualTo(path);
    }

    static Stream<Arguments> provideByeongPath() {
        Path pathOfByeong = new SoldierByeongPath();
        return Stream.of(
                Arguments.of(EIGHT_FIVE, pathOfByeong.getPatterns(Direction.DOWN)),
                Arguments.of(SEVEN_FOUR, pathOfByeong.getPatterns(Direction.LEFT)),
                Arguments.of(SEVEN_SIX, pathOfByeong.getPatterns(Direction.RIGHT))
        );
    }

    @Test
    void 병의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // when & then
        Assertions.assertThatThrownBy(() -> piece.findMovablePath(SEVEN_FIVE, SIX_FIVE))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
