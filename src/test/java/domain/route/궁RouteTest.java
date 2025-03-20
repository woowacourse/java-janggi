package domain.route;

import domain.JanggiPosition;
import domain.pattern.Pattern;
import domain.route.limited_route.궁Route;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 궁RouteTest {
    @ParameterizedTest
    @MethodSource("provide궁Route")
    void 궁의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPostion, List<Pattern> route) {
        // given
        int beforeRow = 9;
        int beforeColumn = 5;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);
        JanggiPieceRoute routeOf궁 = new 궁Route();

        // when
        List<Pattern> 궁route = routeOf궁.getRoute(beforePosition, afterPostion);

        // when & then
        Assertions.assertThat(궁route)
                .isEqualTo(route);
    }

    static Stream<Arguments> provide궁Route() {
        JanggiPieceRoute routeOf궁 = new 궁Route();
        return Stream.of(
                Arguments.of(new JanggiPosition(8, 5), routeOf궁.getPatterns(Direction.UP)),
                Arguments.of(new JanggiPosition(9, 4), routeOf궁.getPatterns(Direction.LEFT)),
                Arguments.of(new JanggiPosition(9, 6), routeOf궁.getPatterns(Direction.RIGHT)),
                Arguments.of(new JanggiPosition(0, 5), routeOf궁.getPatterns(Direction.DOWN)));
    }

    @Test
    void 궁의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // given
        int beforeRow = 9;
        int beforeColumn = 5;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

        int afterRow = 8;
        int afterColumn = 4;
        JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

        // when & then
        Assertions.assertThatThrownBy(
                        () -> new 궁Route().getRoute(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
