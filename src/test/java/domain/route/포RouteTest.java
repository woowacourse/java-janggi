package domain.route;

import domain.JanggiPosition;
import domain.pattern.Pattern;
import domain.route.linear_route.포Route;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 포RouteTest {

    @ParameterizedTest
    @MethodSource("provide포Route")
    void 포의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> route) {
        // given
        int beforeRow = 0;
        int beforeColumn = 1;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);
        JanggiPieceRoute routeOf포 = new 포Route();

        // when
        List<Pattern> 포route = routeOf포.getRoute(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(포route).containsAll(route);
    }

    static Stream<Arguments> provide포Route() {
        포Route routeOf포 = new 포Route();
        return Stream.of(
                Arguments.of(new JanggiPosition(5, 1),
                        List.of(routeOf포.getPattern(Direction.UP),
                                routeOf포.getPattern(Direction.UP),
                                routeOf포.getPattern(Direction.UP),
                                routeOf포.getPattern(Direction.UP),
                                routeOf포.getPattern(Direction.UP)
                        ),
                        Arguments.of(new JanggiPosition(0, 9),
                                List.of(routeOf포.getPattern(Direction.RIGHT),
                                        routeOf포.getPattern(Direction.RIGHT),
                                        routeOf포.getPattern(Direction.RIGHT),
                                        routeOf포.getPattern(Direction.RIGHT),
                                        routeOf포.getPattern(Direction.RIGHT),
                                        routeOf포.getPattern(Direction.RIGHT),
                                        routeOf포.getPattern(Direction.RIGHT),
                                        routeOf포.getPattern(Direction.RIGHT)
                                )
                        )
                ));
    }

    @Test
    void 포의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // given
        int beforeRow = 0;
        int beforeColumn = 1;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

        int afterRow = 9;
        int afterColumn = 2;
        JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

        // when & then
        Assertions.assertThatThrownBy(() -> new 포Route().getRoute(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
