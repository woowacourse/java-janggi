package domain.state;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.GameResult;
import domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FinishState 클래스 테스트")
class FinishStateTest {

    @Test
    @DisplayName("게임 결과를 보관하며 FINISH 상태를 반환한다")
    void storesGameResult() {
        GameResult gameResult = new GameResult(Team.CHO, 3, 5);
        FinishState finishState = new FinishState(gameResult);

        assertThat(finishState.phase()).isEqualTo(GamePhase.FINISH);
        assertThat(finishState.getGameResult()).isEqualTo(gameResult);
    }
}
