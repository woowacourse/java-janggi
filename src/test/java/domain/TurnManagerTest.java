package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TurnManagerTest {

    TurnManager turnManager;

    @BeforeEach
    public void setUp(){
        turnManager = new TurnManager();
    }

    @Test
    public void 현재_턴이_누구인지_반환한다() {
        assertThat(turnManager.getCurrentTurn()).isEqualTo(TeamColor.CHO);

    }

    @Test
    public void 턴이_바뀌면_반대팀이_현재_턴이_된다() {
        turnManager.progressTurn();
        assertThat(turnManager.getCurrentTurn()).isEqualTo(TeamColor.HAN);

    }
}
