package domain.piece.limited_moving_piece;

import static domain.constant.JanggiPieceConstant.CHO_상;

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

public class 상Test {

    @ParameterizedTest
    @MethodSource("provide상Route")
    void 상의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> expected) {
        // given
        int beforeRow = 6;
        int beforeColumn = 5;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

        // when
        List<Pattern> 상route = CHO_상.getRoute(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(상route)
                .isEqualTo(expected);
    }

    static Stream<Arguments> provide상Route() {
        Map<Direction, List<Pattern>> 상routes = JanggiPieceType.상.getRoutes();
        return Stream.of(
                Arguments.of(new JanggiPosition(4, 8), 상routes.get(Direction.RIGHT_UP)),
                Arguments.of(new JanggiPosition(8, 8), 상routes.get(Direction.RIGHT_DOWN)),
                Arguments.of(new JanggiPosition(9, 7), 상routes.get(Direction.DOWN_RIGHT)),
                Arguments.of(new JanggiPosition(9, 3), 상routes.get(Direction.DOWN_LEFT)),
                Arguments.of(new JanggiPosition(8, 2), 상routes.get(Direction.LEFT_DOWN)),
                Arguments.of(new JanggiPosition(4, 2), 상routes.get(Direction.LEFT_UP)),
                Arguments.of(new JanggiPosition(3, 3), 상routes.get(Direction.UP_LEFT)),
                Arguments.of(new JanggiPosition(3, 7), 상routes.get(Direction.UP_RIGHT))
        );
    }

    @Test
    void 상이_이동할_수_없는_경로면_예외를_발생시킨다() {
        // given
        int beforeRow = 6;
        int beforeColumn = 5;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

        int afterRow = 4;
        int afterColumn = 6;
        JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

        // when & then
        Assertions.assertThatThrownBy(() -> CHO_상.getRoute(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
