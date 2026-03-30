package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class PlayerTest {

    @Test
    void 턴_상태를_토글하면_현재_턴_여부가_반전된다() {
        Player player = new Player(new Name("cho"), Side.CHO, new ActiveTurn());

        player.toggleTurn();
        assertThat(player.isCurrentTurn()).isFalse();

        player.toggleTurn();
        assertThat(player.isCurrentTurn()).isTrue();
    }

    @Test
    void 플레이어는_상대방의_기물을_선택하면_예외가_발생한다() {
        Player choPlayer = new Player(new Name("cho"), Side.CHO, new ActiveTurn());
        Piece hanPiece = new Soldier(Side.HAN);

        assertThatThrownBy(() -> choPlayer.validateAlly(hanPiece))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
