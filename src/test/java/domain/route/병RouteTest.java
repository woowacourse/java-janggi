package domain.route;

import domain.JanggiPosition;
import domain.pattern.Direction;
import domain.pattern.Pattern;
import domain.route.limited_route.병Route;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 병RouteTest {
    @ParameterizedTest
    @MethodSource("provide병Route")
    void 병의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // given
        JanggiPosition beforePosition = new JanggiPosition(7, 5);
        JanggiPieceRoute pathOf병 = new 병Route();

        // when
        List<Pattern> 병path = pathOf병.getRoute(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(병path)
                .isEqualTo(path);
    }

    static Stream<Arguments> provide병Route() {
        JanggiPieceRoute pathOf병 = new 병Route();
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
        Assertions.assertThatThrownBy(() -> new 병Route().getRoute(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }

}
