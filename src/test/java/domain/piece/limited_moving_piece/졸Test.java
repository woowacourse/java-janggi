package domain.piece.limited_moving_piece;

import static domain.constant.JanggiPieceConstant.CHO_졸;

import domain.Pattern;
import domain.position.JanggiPosition;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 졸Test {

    @ParameterizedTest
    @MethodSource("provide졸Route")
    void 졸의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> expected) {
        // given
        int beforeRow = 7;
        int beforeColumn = 5;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

        // when
        List<Pattern> route = CHO_졸.getRoute(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(route)
                .isEqualTo(expected);
    }

    static Stream<Arguments> provide졸Route() {
        return Stream.of(
                Arguments.of(new JanggiPosition(6, 5), List.of(Pattern.MOVE_UP)),
                Arguments.of(new JanggiPosition(7, 4), List.of(Pattern.MOVE_LEFT)),
                Arguments.of(new JanggiPosition(7, 6), List.of(Pattern.MOVE_RIGHT))
        );
    }

    @Test
    void 졸이_이동할_수_없는_경로면_예외를_발생시킨다() {
        // given
        int beforeRow = 7;
        int beforeColumn = 5;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

        int afterRow = 8;
        int afterColumn = 5;
        JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

        // when & then
        Assertions.assertThatThrownBy(() -> CHO_졸.getRoute(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
