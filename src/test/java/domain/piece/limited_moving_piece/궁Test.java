package domain.piece.limited_moving_piece;

import static domain.constant.JanggiPieceConstant.CHO_궁;

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

public class 궁Test {

    @ParameterizedTest
    @MethodSource("provide궁Route")
    void 궁의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPostion, List<Pattern> expected) {
        // given
        int beforeRow = 9;
        int beforeColumn = 5;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

        // when
        List<Pattern> route = CHO_궁.getRoute(beforePosition, afterPostion);

        // when & then
        Assertions.assertThat(route)
                .isEqualTo(expected);
    }

    static Stream<Arguments> provide궁Route() {
        Map<Direction, List<Pattern>> routeOf궁 = JanggiPieceType.궁.getRoutes();
        return Stream.of(
                Arguments.of(new JanggiPosition(8, 5), routeOf궁.get(Direction.UP)),
                Arguments.of(new JanggiPosition(9, 4), routeOf궁.get(Direction.LEFT)),
                Arguments.of(new JanggiPosition(9, 6), routeOf궁.get(Direction.RIGHT)),
                Arguments.of(new JanggiPosition(0, 5), routeOf궁.get(Direction.DOWN)));
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
                        () -> CHO_궁.getRoute(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
