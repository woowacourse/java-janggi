package domain.game;

import domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Turn 클래스 테스트")
class TurnTest {

    @Test
    @DisplayName("changeTeam: HAN 팀 턴에서 changeTeam 하면 CHO 팀 턴이 된다")
    void hanTurnChangesToCho() {
        Turn hanTurn = new Turn(Team.HAN);

        Turn next = hanTurn.changeTeam();

        assertThat(next.getTeam()).isEqualTo(Team.CHO);
    }

    @Test
    @DisplayName("changeTeam: CHO 팀 턴에서 changeTeam 하면 HAN 팀 턴이 된다")
    void choTurnChangesToHan() {
        Turn choTurn = new Turn(Team.CHO);

        Turn next = choTurn.changeTeam();

        assertThat(next.getTeam()).isEqualTo(Team.HAN);
    }
}
