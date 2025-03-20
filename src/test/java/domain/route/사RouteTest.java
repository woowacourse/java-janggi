package domain.route;

import domain.JanggiPosition;
import domain.pattern.Direction;
import domain.pattern.Pattern;
import domain.route.LimitedRoute;
import domain.route.Route;
import domain.route.사Route;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 사RouteTest {

    @ParameterizedTest
    @MethodSource("provide사Route")
    void 사의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> route) {
        // given
        int beforeRow = 9;
        int beforeColumn = 5;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);
        Route routeOf사 = new 사Route();

        // when
        List<Pattern> 사route = routeOf사.getRoute(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(사route)
                .isEqualTo(route);
    }

    static Stream<Arguments> provide사Route() {
        LimitedRoute routeOf사 = new 사Route();
        return Stream.of(
                Arguments.of(new JanggiPosition(8, 5), routeOf사.getPatterns(Direction.UP)),
                Arguments.of(new JanggiPosition(9, 4), routeOf사.getPatterns(Direction.LEFT)),
                Arguments.of(new JanggiPosition(9, 6), routeOf사.getPatterns(Direction.RIGHT)),
                Arguments.of(new JanggiPosition(0, 5), routeOf사.getPatterns(Direction.DOWN)));
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
        Assertions.assertThatThrownBy(() -> new 사Route().getRoute(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
