package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PlayerTest {

    @Test
    void 현재_턴_상태를_반환한다() {
        Player current = new Player(new Name("cho"), Side.CHO, new ActiveTurn());
        Player notCurrent = new Player(new Name("han"),Side.HAN,  new InactiveTurn());

        assertThat(current.isCurrentTurn()).isTrue();
        assertThat(notCurrent.isCurrentTurn()).isFalse();
    }

    @Test
    void 턴_상태를_토글한다() {
        Player player = new Player(new Name("cho"), Side.CHO, new ActiveTurn());

        player.toggleTurn();
        assertThat(player.isCurrentTurn()).isFalse();

        player.toggleTurn();
        assertThat(player.isCurrentTurn()).isTrue();
    }
}
