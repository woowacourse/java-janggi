package domain.piece;

import static domain.constant.JanggiPieceConstant.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

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
}
