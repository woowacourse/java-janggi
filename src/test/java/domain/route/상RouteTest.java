package domain.route;

import domain.JanggiPosition;
import domain.pattern.Direction;
import domain.pattern.Pattern;
import domain.route.limited_route.상Route;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 상RouteTest {

    @ParameterizedTest
    @MethodSource("provide상Route")
    void 상의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> route) {
        // given
        int beforeRow = 6;
        int beforeColumn = 5;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);
        JanggiPieceRoute routeOf상 = new 상Route();

        // when
        List<Pattern> 상route = routeOf상.getRoute(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(상route)
                .isEqualTo(route);
    }

    static Stream<Arguments> provide상Route() {
        JanggiPieceRoute routeOf상 = new 상Route();
        return Stream.of(
                Arguments.of(new JanggiPosition(4, 8), routeOf상.getPatterns(Direction.RIGHT_UP)),
                Arguments.of(new JanggiPosition(8, 8), routeOf상.getPatterns(Direction.RIGHT_DOWN)),
                Arguments.of(new JanggiPosition(9, 7), routeOf상.getPatterns(Direction.DOWN_RIGHT)),
                Arguments.of(new JanggiPosition(9, 3), routeOf상.getPatterns(Direction.DOWN_LEFT)),
                Arguments.of(new JanggiPosition(8, 2), routeOf상.getPatterns(Direction.LEFT_DOWN)),
                Arguments.of(new JanggiPosition(4, 2), routeOf상.getPatterns(Direction.LEFT_UP)),
                Arguments.of(new JanggiPosition(3, 3), routeOf상.getPatterns(Direction.UP_LEFT)),
                Arguments.of(new JanggiPosition(3, 7), routeOf상.getPatterns(Direction.UP_RIGHT))
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
        Assertions.assertThatThrownBy(() -> new 상Route().getRoute(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
