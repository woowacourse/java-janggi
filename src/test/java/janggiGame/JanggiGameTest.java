package janggiGame;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import fixture.JanggiGameFixture;
import janggiGame.piece.Dynasty;
import janggiGame.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameTest {
    @DisplayName("한 턴 무르면 이전 상태로 되돌아간다")
    @Test
    void undo() {
        // given
        JanggiGame janggiGame = JanggiGameFixture.getRunningJanggiGame();

        // when
        Dynasty lastDynasty = janggiGame.getCurrentDynasty();
        janggiGame.takeTurn(Position.getInstanceBy(0, 0), Position.getInstanceBy(0, 1));
        janggiGame.undoTurn();
        Dynasty currentDynasty = janggiGame.getCurrentDynasty();

        // then
        assertThat(currentDynasty).isEqualTo(lastDynasty);
    }

    @DisplayName("무를 수 있는 턴이 없는 상태에서 무르면 예외가 발생한다")
    @Test
    void cannotUndo() {
        // given
        JanggiGame janggiGame = JanggiGameFixture.getRunningJanggiGame();

        // when // then
        assertThatCode(janggiGame::undoTurn)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("[ERROR]");

    }
}
