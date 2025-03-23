package domain.piece.limited_moving_piece;

import static domain.constant.JanggiPieceConstant.CHO_궁;
import static domain.constant.JanggiPieceConstant.CHO_졸;
import static domain.constant.JanggiPieceConstant.EMPTY;
import static domain.constant.JanggiPieceConstant.HAN_병;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import domain.Direction;
import domain.Pattern;
import domain.piece.JanggiPiece;
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
        return Stream.of(
                Arguments.of(new JanggiPosition(8, 5), List.of(Pattern.MOVE_UP)),
                Arguments.of(new JanggiPosition(9, 4), List.of(Pattern.MOVE_LEFT)),
                Arguments.of(new JanggiPosition(9, 6), List.of(Pattern.MOVE_RIGHT)),
                Arguments.of(new JanggiPosition(0, 5), List.of(Pattern.MOVE_DOWN)));
    }

    @Test
    void 궁이_이동할_수_없는_경로면_예외를_발생시킨다() {
        // given
        int beforeRow = 9;
        int beforeColumn = 5;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

        int afterRow = 8;
        int afterColumn = 4;
        JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

        // when & then
        assertThatThrownBy(
                        () -> CHO_궁.getRoute(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 도착지에_같은_편의_말이_존재하는_경우_예외를_발생시킨다() {
        // given
        JanggiPiece piece = CHO_궁;
        JanggiPiece hurdlePiece = EMPTY;
        int hurdleCount = 0;
        JanggiPiece targetPiece = CHO_졸;

        // when & then
        assertThatThrownBy(() -> piece.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 도착지에_상대_편의_말이_존재하는_경우_이동할_수_있다() {
        // given
        JanggiPiece piece = CHO_궁;
        JanggiPiece hurdlePiece = EMPTY;
        int hurdleCount = 0;
        JanggiPiece targetPiece = HAN_병;

        // when & then
        assertDoesNotThrow(() -> piece.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece));
    }
}
