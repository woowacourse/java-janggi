package domain.piece.limited_moving_piece;

import static domain.constant.JanggiPieceConstant.CHO_졸;
import static domain.constant.JanggiPieceConstant.EMPTY;
import static domain.constant.JanggiPieceConstant.HAN_병;
import static domain.constant.JanggiPieceConstant.HAN_포;
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

public class 병Test {

    @ParameterizedTest
    @MethodSource("provide병Route")
    void 병의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> expected) {
        // given
        JanggiPosition beforePosition = new JanggiPosition(7, 5);

        // when
        List<Pattern> 병path = HAN_병.getRoute(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(병path)
                .isEqualTo(expected);
    }

    static Stream<Arguments> provide병Route() {
        return Stream.of(
                Arguments.of(new JanggiPosition(8, 5), List.of(Pattern.MOVE_DOWN)),
                Arguments.of(new JanggiPosition(7, 4), List.of(Pattern.MOVE_LEFT)),
                Arguments.of(new JanggiPosition(7, 6), List.of(Pattern.MOVE_RIGHT))
        );
    }

    @Test
    void 병이_이동할_수_없는_경로면_예외를_발생시킨다() {
        // given
        JanggiPosition beforePosition = new JanggiPosition(7, 5);
        JanggiPosition afterPosition = new JanggiPosition(6, 5);

        // when & then
        Assertions.assertThatThrownBy(() -> HAN_병.getRoute(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 도착지에_같은_편의_말이_존재하는_경우_예외를_발생시킨다() {
        // given
        JanggiPiece piece = HAN_병;
        JanggiPiece hurdlePiece = EMPTY;
        int hurdleCount = 0;
        JanggiPiece targetPiece = HAN_포;

        // when & then
        assertThatThrownBy(() -> piece.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 도착지에_상대_편의_말이_존재하는_경우_이동할_수_있다() {
        // given
        JanggiPiece piece = HAN_병;
        JanggiPiece hurdlePiece = EMPTY;
        int hurdleCount = 0;
        JanggiPiece targetPiece = CHO_졸;

        // when & then
        assertDoesNotThrow(() -> piece.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece));
    }
}
