package janggiGame.state.Running;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import fixture.JanggiGameFixture;
import janggiGame.JanggiGame;
import janggiGame.position.Position;
import janggiGame.state.GameScore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RunningTest {
    @DisplayName("입력받은 위치에 기물이 없다면 예외를 발생시킨다")
    @Test
    void validateEmptySpace() {
        // given
        JanggiGame janggiGame = JanggiGameFixture.getRunningJanggiGame();

        Position origin = Position.getInstanceBy(0, 2);
        Position destination = Position.getInstanceBy(1, 4);

        // when // then
        assertThatCode(() -> janggiGame.takeTurn(origin, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("입력받은 위치의 기물이 다른 편이라면 예외를 발생시킨다")
    @Test
    void validatePieceDynasty() {
        // given
        JanggiGame janggiGame = JanggiGameFixture.getRunningJanggiGame();

        Position origin = Position.getInstanceBy(0, 9);
        Position destination = Position.getInstanceBy(0, 8);

        // when // then
        assertThatCode(() -> janggiGame.takeTurn(origin, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("말이 이동했다면 원래의 위치는 비어있고 새로운 위치에 말이 들어간다")
    @Test
    void checkStatusAfterMoving() {
        // given
        JanggiGame janggiGame = JanggiGameFixture.getRunningJanggiGame();

        Position origin = Position.getInstanceBy(0, 0);
        Position destination = Position.getInstanceBy(0, 1);

        janggiGame.takeTurn(origin, destination);
        // when
        boolean originActual = janggiGame.getPieces().containsKey(origin);
        boolean destinationActual = janggiGame.getPieces().containsKey(destination);

        // then
        assertThat(originActual).isFalse();
        assertThat(destinationActual).isTrue();
    }

    @DisplayName("각 나라의 점수를 구할 수 있다")
    @Test
    void getGameScore() {
        // given
        JanggiGame janggiGame = JanggiGameFixture.getRunningJanggiGame();
        GameScore gameScore = janggiGame.getGameScore();

        // when
        double hanScore = gameScore.hanScore();
        double choScore = gameScore.choScore();

        // then
        assertThat(hanScore).isEqualTo(73.5);
        assertThat(choScore).isEqualTo(72);
    }

    @DisplayName("Running 상태는 끝나지 않은 상태이다")
    @Test
    void isFinished() {
        // given
        JanggiGame janggiGame = JanggiGameFixture.getRunningJanggiGame();

        // when
        boolean actual = janggiGame.isFinished();

        // then
        assertThat(actual).isFalse();
    }
}
