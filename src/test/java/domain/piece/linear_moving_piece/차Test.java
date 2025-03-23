package domain.piece.linear_moving_piece;

import static domain.constant.JanggiPieceConstant.CHO_사;
import static domain.constant.JanggiPieceConstant.CHO_졸;
import static domain.constant.JanggiPieceConstant.CHO_차;
import static domain.constant.JanggiPieceConstant.EMPTY;
import static domain.constant.JanggiPieceConstant.HAN_병;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.Pattern;
import domain.piece.JanggiPiece;
import domain.position.JanggiPosition;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 차Test {

    @ParameterizedTest
    @MethodSource("provide차Route")
    void 차의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> expected) {
        // given
        int beforeRow = 0;
        int beforeColumn = 1;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

        // when
        List<Pattern> route = CHO_차.getRoute(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(route).containsAll(expected);
    }

    static Stream<Arguments> provide차Route() {
        return Stream.of(
                Arguments.of(new JanggiPosition(5, 1),
                        List.of(
                                Pattern.MOVE_UP,
                                Pattern.MOVE_UP,
                                Pattern.MOVE_UP,
                                Pattern.MOVE_UP,
                                Pattern.MOVE_UP
                        )),
                Arguments.of(new JanggiPosition(0, 9),
                        List.of(
                                Pattern.MOVE_RIGHT,
                                Pattern.MOVE_RIGHT,
                                Pattern.MOVE_RIGHT,
                                Pattern.MOVE_RIGHT,
                                Pattern.MOVE_RIGHT,
                                Pattern.MOVE_RIGHT,
                                Pattern.MOVE_RIGHT,
                                Pattern.MOVE_RIGHT
                        )));
    }

    @Test
    void 차가_이동할_수_없는_경로면_예외를_발생시킨다() {
        // given
        int beforeRow = 0;
        int beforeColumn = 1;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

        int afterRow = 9;
        int afterColumn = 2;
        JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

        // when & then
        Assertions.assertThatThrownBy(() -> CHO_차.getRoute(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 도착지에_같은_편의_말이_존재하는_경우_예외를_발생시킨다() {
        // given
        JanggiPiece piece = CHO_차;
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
        JanggiPiece piece = CHO_차;
        JanggiPiece hurdlePiece = EMPTY;
        int hurdleCount = 0;
        JanggiPiece targetPiece = HAN_병;

        // when & then
        assertDoesNotThrow(() -> piece.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece));
    }

    @Test
    void 경로에_장애물이_있으면_예외를_발생시킨다() {
        // given
        JanggiPiece piece = CHO_차;
        JanggiPiece hurdlePiece = CHO_졸;
        int hurdleCount = 1;
        JanggiPiece targetPiece = HAN_병;

        // when & then
        assertThatThrownBy(() -> piece.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece))
                .isInstanceOf(IllegalStateException.class);
    }
}
