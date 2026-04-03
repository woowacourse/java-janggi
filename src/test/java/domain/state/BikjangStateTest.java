package domain.state;

import domain.game.JanggiGame;
import domain.piece.Team;
import domain.setup.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("BikjangState 클래스 테스트")
class BikjangStateTest {

    private JanggiGame game;
    private BikjangState bikjangState;

    @BeforeEach
    void setUp() {
        game = new JanggiGame();
        game.processCommand(new Command("1"));
        game.processCommand(new Command("1"));
        bikjangState = new BikjangState();
    }

    @Test
    @DisplayName("y 입력 시 EndGameState(DRAW)를 반환한다")
    void yInputReturnsEndGameStateWithDraw() {
        GameState nextState = bikjangState.handle(game, new Command("y"));

        assertThat(nextState).isInstanceOf(EndGameState.class);
        assertThat(nextState.isFinished()).isTrue();
    }

    @Test
    @DisplayName("Y 대문자 입력도 무승부 처리된다")
    void upperCaseYAlsoReturnsDraw() {
        GameState nextState = bikjangState.handle(game, new Command("Y"));

        assertThat(nextState).isInstanceOf(EndGameState.class);
    }

    @Test
    @DisplayName("n 입력 시 PlayingState를 반환한다")
    void nInputReturnsPlayingState() {
        GameState nextState = bikjangState.handle(game, new Command("n"));

        assertThat(nextState).isInstanceOf(PlayingState.class);
    }

    @Test
    @DisplayName("N 대문자 입력도 PlayingState를 반환한다")
    void upperCaseNAlsoReturnsPlayingState() {
        GameState nextState = bikjangState.handle(game, new Command("N"));

        assertThat(nextState).isInstanceOf(PlayingState.class);
    }

    @Test
    @DisplayName("y/n 이외의 입력은 예외를 던진다")
    void invalidInputThrowsException() {
        assertThatThrownBy(() -> bikjangState.handle(game, new Command("yes")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("빈 공백 입력은 예외를 던진다")
    void emptyLikeInputThrowsException() {
        assertThatThrownBy(() -> bikjangState.handle(game, new Command("a")))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
