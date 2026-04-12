package domain.game;

import domain.piece.Team;
import domain.setup.Command;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("JanggiGame 클래스 테스트")
class JanggiGameTest {

    @Test
    @DisplayName("초기화 시 HAN 팀의 턴으로 시작한다")
    void initialTurnIsHan() {
        JanggiGame game = new JanggiGame();

        assertThat(game.getTurn().getTeam()).isEqualTo(Team.HAN);
    }

    @Test
    @DisplayName("HAN 상차림 배치 입력 후 CHO 상차림을 진행한다")
    void afterHanSetupTurnChangesToCho() {
        JanggiGame game = new JanggiGame();

        game.processCommand(new Command("1"));

        assertThat(game.getTurn().getTeam()).isEqualTo(Team.CHO);
    }

    @Test
    @DisplayName("기물 정상 이동 후 턴이 상대 팀으로 바뀐다")
    void turnChangesAfterMove() {
        JanggiGame game = new JanggiGame();
        game.processCommand(new Command("1"));
        game.processCommand(new Command("1"));

        game.processCommand(new Command("e6 e5"));

        assertThat(game.getTurn().getTeam()).isEqualTo(Team.HAN);
    }

    @Test
    @DisplayName("설정 단계에서 유효하지 않은 배치 코드는 예외를 던진다")
    void invalidArrangementCommandThrowsException() {
        JanggiGame game = new JanggiGame();

        assertThatThrownBy(() -> game.processCommand(new Command("5")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("플레이 단계에서 잘못된 좌표는 예외를 던진다")
    void invalidCoordinateInPlayingStateThrowsException() {
        JanggiGame game = new JanggiGame();
        game.processCommand(new Command("1"));
        game.processCommand(new Command("1")); // now in playing state, CHO's turn

        assertThatThrownBy(() -> game.processCommand(new Command("z0 a1")))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
