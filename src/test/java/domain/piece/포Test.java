package domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class 포Test {

    @Test
    void 포는_포를_넘을_수_없다() {
        // given
        JanggiPiece 포 = new 포(JanggiSide.CHO);
        JanggiPiece hurdlePiece = new 포(JanggiSide.CHO);
        int hurdleCount = 1;
        JanggiPiece targetPiece = new 졸병(JanggiSide.CHO);


        // when & then
        assertThatThrownBy(() -> 포.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("포는 포를 넘을 수 없습니다.");
    }

    @Test
    void 포는_포를_잡을_수_없다() {
        // given
        JanggiPiece 포 = new 포(JanggiSide.CHO);
        JanggiPiece hurdlePiece = new 졸병(JanggiSide.CHO);
        int hurdleCount = 1;
        JanggiPiece targetPiece = new 포(JanggiSide.HAN);


        // when & then
        assertThatThrownBy(() -> 포.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("포는 포를 잡을 수 없습니다.");
    }

    @Test
    void 포는_장애물을_1개만_뛰어넘을_수_있다() {
        // given
        JanggiPiece 포 = new 포(JanggiSide.CHO);
        JanggiPiece hurdlePiece = new 졸병(JanggiSide.CHO);
        int hurdleCount = 2;
        JanggiPiece targetPiece = new 졸병(JanggiSide.HAN);


        // when & then
        assertThatThrownBy(() -> 포.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("포는 장애물 1개를 뛰어넘어야 합니다.");
    }

    @Test
    void 포는_장애물을_1개가_있어야_움직일_수_있다() {
        // given
        JanggiPiece 포 = new 포(JanggiSide.CHO);
        JanggiPiece hurdlePiece = new Empty();
        int hurdleCount = 0;
        JanggiPiece targetPiece = new 졸병(JanggiSide.HAN);


        // when & then
        assertThatThrownBy(() -> 포.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("포는 장애물 1개를 뛰어넘어야 합니다.");
    }

    @Test
    void 같은_팀인_기물은_잡을_수_없다() {
        // given
        JanggiPiece 포 = new 포(JanggiSide.CHO);
        JanggiPiece hurdlePiece = new 졸병(JanggiSide.CHO);
        int hurdleCount = 1;
        JanggiPiece targetPiece = new 졸병(JanggiSide.CHO);


        // when & then
        assertThatThrownBy(() -> 포.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("같은 팀의 기물은 잡을 수 없습니다.");
    }
}
