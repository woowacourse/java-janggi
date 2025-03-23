package domain.piece.linear_moving_piece;

import static domain.constant.JanggiPieceConstant.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Pattern;
import domain.piece.Empty;
import domain.piece.JanggiPiece;
import domain.position.JanggiPosition;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 포Test {

    @Test
    void 포는_포를_넘을_수_없다() {
        // given
        JanggiPiece 포 = CHO_포;
        JanggiPiece hurdlePiece = CHO_포;
        int hurdleCount = 1;
        JanggiPiece targetPiece = CHO_졸;

        // when & then
        assertThatThrownBy(() -> 포.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("포는 포를 넘을 수 없습니다.");
    }

    @Test
    void 포는_포를_잡을_수_없다() {
        // given
        JanggiPiece 포 = CHO_포;
        JanggiPiece hurdlePiece = CHO_졸;
        int hurdleCount = 1;
        JanggiPiece targetPiece = HAN_포;

        // when & then
        assertThatThrownBy(() -> 포.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("포는 포를 잡을 수 없습니다.");
    }

    @Test
    void 포는_장애물을_1개만_뛰어넘을_수_있다() {
        // given
        JanggiPiece 포 = CHO_포;
        JanggiPiece hurdlePiece = CHO_졸;
        int hurdleCount = 2;
        JanggiPiece targetPiece = HAN_병;

        // when & then
        assertThatThrownBy(() -> 포.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("포는 장애물 1개를 뛰어넘어야 합니다.");
    }

    @Test
    void 포는_장애물을_1개가_있어야_움직일_수_있다() {
        // given
        JanggiPiece 포 = CHO_포;
        JanggiPiece hurdlePiece = new Empty();
        int hurdleCount = 0;
        JanggiPiece targetPiece = HAN_병;

        // when & then
        assertThatThrownBy(() -> 포.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("포는 장애물 1개를 뛰어넘어야 합니다.");
    }

    @Test
    void 같은_팀인_기물은_잡을_수_없다() {
        // given
        JanggiPiece 포 = CHO_포;
        JanggiPiece hurdlePiece = CHO_졸;
        int hurdleCount = 1;
        JanggiPiece targetPiece = CHO_졸;

        // when & then
        assertThatThrownBy(() -> 포.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("같은 팀의 기물은 잡을 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("provide포Route")
    void 포의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> expected) {
        // given
        int beforeRow = 0;
        int beforeColumn = 1;
        JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

        // when
        List<Pattern> route = CHO_포.getRoute(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(route).containsAll(expected);
    }

    static Stream<Arguments> provide포Route() {
        return Stream.of(
                Arguments.of(new JanggiPosition(5, 1),
                        List.of(
                                Pattern.MOVE_UP,
                                Pattern.MOVE_UP,
                                Pattern.MOVE_UP,
                                Pattern.MOVE_UP,
                                Pattern.MOVE_UP
                        )
                ),
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
        Assertions.assertThatThrownBy(() -> CHO_포.getRoute(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
