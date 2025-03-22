package domain.route;

import domain.JanggiPosition;
import domain.pattern.Pattern;
import domain.route.limited_route.졸Route;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 졸RouteTest {

    @ParameterizedTest
    @MethodSource("provide졸Route")
    void 졸의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> route) {
        // given
        int beforeRow = 7;
        int beforeColumn = 5;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);
        JanggiPieceRoute routeOf졸 = new 졸Route();

        // when
        List<Pattern> 졸route = routeOf졸.getRoute(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(졸route)
                .isEqualTo(route);
    }

    static Stream<Arguments> provide졸Route() {
        JanggiPieceRoute routeOf졸 = new 졸Route();
        return Stream.of(
                Arguments.of(new JanggiPosition(6, 5), routeOf졸.getPatterns(Direction.UP)),
                Arguments.of(new JanggiPosition(7, 4), routeOf졸.getPatterns(Direction.LEFT)),
                Arguments.of(new JanggiPosition(7, 6), routeOf졸.getPatterns(Direction.RIGHT))
        );
    }

    @Test
    void 졸의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // given
        int beforeRow = 7;
        int beforeColumn = 5;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

        int afterRow = 8;
        int afterColumn = 5;
        JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

        // when & then
        Assertions.assertThatThrownBy(() -> new 졸Route().getRoute(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
