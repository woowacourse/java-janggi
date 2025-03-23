package domain.piece.limited_moving_piece;

import static domain.constant.JanggiPieceConstant.CHO_마;

import domain.Direction;
import domain.Pattern;
import domain.piece.JanggiPieceType;
import domain.position.JanggiPosition;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 마Test {


    @ParameterizedTest
    @MethodSource("provide마Path")
    void 마의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> expected) {
        // given
        JanggiPosition beforePosition = new JanggiPosition(6, 4);

        // when
        List<Pattern> route = CHO_마.getRoute(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(route)
                .isEqualTo(expected);
    }

    static Stream<Arguments> provide마Path() {
        Map<Direction, List<Pattern>> pathOf마 = JanggiPieceType.마.getRoutes();
        return Stream.of(
                Arguments.of(new JanggiPosition(5, 2), pathOf마.get(Direction.LEFT_UP)),
                Arguments.of(new JanggiPosition(5, 6), pathOf마.get(Direction.RIGHT_UP)),
                Arguments.of(new JanggiPosition(4, 5), pathOf마.get(Direction.UP_RIGHT)),
                Arguments.of(new JanggiPosition(4, 3), pathOf마.get(Direction.UP_LEFT)),
                Arguments.of(new JanggiPosition(7, 2), pathOf마.get(Direction.LEFT_DOWN)),
                Arguments.of(new JanggiPosition(8, 3), pathOf마.get(Direction.DOWN_LEFT)),
                Arguments.of(new JanggiPosition(8, 5), pathOf마.get(Direction.DOWN_RIGHT)),
                Arguments.of(new JanggiPosition(7, 6), pathOf마.get(Direction.RIGHT_DOWN))
        );
    }

    @Test
    void 마의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // given
        JanggiPosition beforePosition = new JanggiPosition(6, 4);
        JanggiPosition afterPosition = new JanggiPosition(4, 4);

        // when & then
        Assertions.assertThatThrownBy(() -> CHO_마.getRoute(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
