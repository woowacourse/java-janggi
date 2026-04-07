package domain.state;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.JanggiGame;
import domain.setup.Arrangements;
import domain.setup.Command;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ReadyState 클래스 테스트")
class ReadyStateTest {

    @Test
    @DisplayName("HAN 배치가 없을 때 명령 처리 시 HAN 배치가 등록되고 ReadyState를 반환한다")
    void handleFirstCommandRegistersHanArrangementAndReturnsReadyState() {
        Arrangements arrangements = new Arrangements();
        ReadyState state = new ReadyState(arrangements);
        JanggiGame game = new JanggiGame();

        GameState nextState = state.handle(game, new Command("1"));

        assertThat(nextState).isInstanceOf(ReadyState.class);
    }

    @Test
    @DisplayName("JanggiGame을 통해 두 번 명령하면 PlayingState로 전환된다")
    void gameTransitionsToPlayingStateAfterTwoArrangements() {
        JanggiGame game = new JanggiGame();

        game.processCommand(new Command("3"));
        game.processCommand(new Command("4"));

        assertThat(game.getBoard()).isPresent();
    }
}
