package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PlayerTest {

    @Test
    void 현재_턴_상태를_반환한다() {
        Player current = new Player(new Name("cho"), new CurrentTurn());
        Player notCurrent = new Player(new Name("han"), new NotCurrentTurn());

        assertThat(current.isTurn()).isTrue();
        assertThat(notCurrent.isTurn()).isFalse();
    }

    @Test
    void 턴_상태를_토글한다() {
        Player player = new Player(new Name("cho"), new CurrentTurn());

        player.changeTurn();
        assertThat(player.isTurn()).isFalse();

        player.changeTurn();
        assertThat(player.isTurn()).isTrue();
    }
}
