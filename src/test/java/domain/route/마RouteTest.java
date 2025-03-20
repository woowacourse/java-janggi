package domain.route;

import domain.JanggiPosition;
import domain.pattern.Direction;
import domain.pattern.Pattern;
import domain.route.limited_route.마Route;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 마RouteTest {

    @ParameterizedTest
    @MethodSource("provide마Path")
    void 마의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // given
        JanggiPosition beforePosition = new JanggiPosition(6, 4);
        마Route pathOf마 = new 마Route();

        // when
        List<Pattern> 마path = pathOf마.getRoute(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(마path)
                .isEqualTo(path);
    }

    static Stream<Arguments> provide마Path() {
        마Route pathOf마 = new 마Route();
        return Stream.of(
                Arguments.of(new JanggiPosition(5, 2), pathOf마.getPatterns(Direction.LEFT_UP)),
                Arguments.of(new JanggiPosition(5, 6), pathOf마.getPatterns(Direction.RIGHT_UP)),
                Arguments.of(new JanggiPosition(4, 5), pathOf마.getPatterns(Direction.UP_RIGHT)),
                Arguments.of(new JanggiPosition(4, 3), pathOf마.getPatterns(Direction.UP_LEFT)),
                Arguments.of(new JanggiPosition(7, 2), pathOf마.getPatterns(Direction.LEFT_DOWN)),
                Arguments.of(new JanggiPosition(8, 3), pathOf마.getPatterns(Direction.DOWN_LEFT)),
                Arguments.of(new JanggiPosition(8, 5), pathOf마.getPatterns(Direction.DOWN_RIGHT)),
                Arguments.of(new JanggiPosition(7, 6), pathOf마.getPatterns(Direction.RIGHT_DOWN))
        );
    }

    @Test
    void 마의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // given
        JanggiPosition beforePosition = new JanggiPosition(6, 4);
        JanggiPosition afterPosition = new JanggiPosition(4, 4);

        // when & then
        Assertions.assertThatThrownBy(() -> new 마Route().getRoute(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
