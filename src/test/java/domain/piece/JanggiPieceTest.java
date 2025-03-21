package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class JanggiPieceTest {

    @Test
    void 기물을_잡으면_잡힌_기물의_상태가_바뀐다() {
        // given
        JanggiPiece piece = new 마(JanggiSide.CHO);

        // when
        piece.captureIfNotEmpty();

        // then
        assertThat(piece.isCaptured()).isTrue();
    }

    @Test
    void 포가_아닌_기물은_장애물을_넘을_수_없다() {
        // given
        JanggiPiece 마 = new 마(JanggiSide.CHO);
        JanggiPiece hurdlePiece = new Empty();
        int hurdleCount = 1;
        JanggiPiece targetPiece = new 졸(JanggiSide.HAN);


        // when & then
        assertThatThrownBy(() -> 마.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("해당 기물은 장애물을 뛰어넘을 수 없습니다.");
    }

    @Test
    void 같은_팀인_기물은_잡을_수_없다() {
        // given
        JanggiPiece 마 = new 마(JanggiSide.CHO);
        JanggiPiece hurdlePiece = new 졸(JanggiSide.CHO);
        int hurdleCount = 1;
        JanggiPiece targetPiece = new 졸(JanggiSide.CHO);


        // when & then
        assertThatThrownBy(() -> 마.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("같은 팀의 기물은 잡을 수 없습니다.");
    }

    @Test
    void 특정_기물이_같은_팀인지_확인할_수_있다() {
        // given
        JanggiPiece 마 = new 마(JanggiSide.CHO);
        JanggiPiece 궁 = new 궁(JanggiSide.CHO);

        // when & then
        assertThat(마.isMyTeam(궁)).isTrue();
    }
}
