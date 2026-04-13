package domain.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.state.ActiveTurn;
import org.junit.jupiter.api.Test;

class PlayerTest {

    @Test
    void 턴_상태를_토글하면_현재_턴_여부가_반전된다() {
        // Given: 초나라 플레이어가 자신의 턴(ActiveTurn)인 상태로 생성
        Player player = new Player(new Name("cho"), Side.CHO, new ActiveTurn());

        // When: 턴을 한 번 토글 (Active -> Waiting)
        player.toggleTurn();
        // Then
        assertThat(player.isCurrentTurn()).isFalse();

        // When: 턴을 다시 토글 (Waiting -> Active)
        player.toggleTurn();
        // Then
        assertThat(player.isCurrentTurn()).isTrue();
    }

    @Test
    void 플레이어는_자신의_기물이_아닌_상대방의_기물을_검증하면_예외가_발생한다() {
        // Given: 초나라 플레이어와 한나라 졸(Soldier)
        Player choPlayer = new Player(new Name("cho"), Side.CHO, new ActiveTurn());

        // PieceFactory를 사용하여 실제 도메인과 동일한 기물 생성 (전략 주입 포함)
        Piece hanPiece = PieceFactory.createSoldier(Side.HAN);

        // When & Then: 초나라 플레이어가 한나라 기물을 validateAlly 할 때 예외 발생
        assertThatThrownBy(() -> choPlayer.validateAlly(hanPiece))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상대방의 기물은 움직일 수 없습니다.");
    }

    @Test
    void 플레이어는_자신의_기물을_검증하면_예외가_발생하지_않는다() {
        // Given: 초나라 플레이어와 초나라 졸(Soldier)
        Player choPlayer = new Player(new Name("cho"), Side.CHO, new ActiveTurn());
        Piece choPiece = PieceFactory.createSoldier(Side.CHO);

        // When & Then: 예외 없이 통과해야 함
        choPlayer.validateAlly(choPiece);
    }
}
