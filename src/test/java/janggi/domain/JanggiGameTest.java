package janggi.domain;

import janggi.domain.piece.General;
import janggi.domain.piece.Soldier;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class JanggiGameTest {

    @Test
    @DisplayName("GREEN 팀부터 기물을 이동할 수 있다")
    void moveByPlayer() {
        //given
        Player redPlayer = new Player("flint", Team.RED);
        Player greenPlayer = new Player("abc", Team.GREEN);
        Board board = new Board(new HashMap<>(Map.of(Position.of(8, 5), new General(Team.GREEN),
                Position.of(4, 4), new Soldier(Team.GREEN),
                Position.of(2, 5), new General(Team.RED),
                Position.of(3, 3), new Soldier(Team.RED))));
        JanggiGame janggiGame = new JanggiGame(board, redPlayer, greenPlayer);

        //when
        //then

        assertAll(() -> {
            assertThat(janggiGame.getCurrentPlayer())
                    .usingRecursiveComparison()
                    .isEqualTo(greenPlayer);
            assertDoesNotThrow(() -> janggiGame.moveByPlayer(Position.of(8, 5), Position.of(8, 4)));
            assertThat(janggiGame.getCurrentPlayer())
                    .usingRecursiveComparison()
                    .isEqualTo(redPlayer);
            assertDoesNotThrow(() -> janggiGame.moveByPlayer(Position.of(2, 5), Position.of(2, 4)));
            assertThat(janggiGame.getCurrentPlayer())
                    .usingRecursiveComparison()
                    .isEqualTo(greenPlayer);
        });
    }

    @Test
    @DisplayName("자신의 기물만 움직일 수 있다")
    void moveByPlayerNotInTurn() {
        //given
        Player redPlayer = new Player("flint", Team.RED);
        Player greenPlayer = new Player("abc", Team.GREEN);
        Board board = new Board(
                Map.of(Position.of(1, 1), new General(Team.GREEN),
                        Position.of(2, 2), new General(Team.RED),
                        Position.of(2, 3), new Soldier(Team.RED)));
        JanggiGame janggiGame = new JanggiGame(board, redPlayer, greenPlayer);

        //when
        //then
        Assertions.assertThatThrownBy(() -> janggiGame.moveByPlayer(Position.of(2, 2), Position.of(3, 2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("적의 기물을 선택할 수 없습니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"GREEN,GREEN_WIN", "RED,RED_WIN"})
    @DisplayName("두 팀 중 궁이 잡은 팀이 승리한다")
    void checkGeneralDiedWhenOtherRemain(Team team, GameStatus expected) {
        //given
        Player redPlayer = new Player("flint", Team.RED);
        Player greenPlayer = new Player("abc", Team.GREEN);
        Board board = new Board(Map.of(Position.of(1, 1), new General(team)));
        JanggiGame janggiGame = new JanggiGame(board, redPlayer, greenPlayer);

        //when
        janggiGame.checkWinCondition();

        //then
        assertThat(janggiGame.getGameStatus()).isEqualTo(expected);
    }

    @Test
    @DisplayName("두 팀의 궁이 모두 살아있으면 게임을 진행한다")
    void checkGeneralDiedWhenAllAlive() {
        //given
        Player redPlayer = new Player("flint", Team.RED);
        Player greenPlayer = new Player("abc", Team.GREEN);
        Board board = new Board(Map.of(Position.of(1, 1), new General(Team.RED),
                Position.of(1, 2), new General(Team.GREEN),
                Position.of(2, 2), new Soldier(Team.RED)));
        JanggiGame janggiGame = new JanggiGame(board, redPlayer, greenPlayer);

        //when
        janggiGame.checkWinCondition();

        //then
        assertThat(janggiGame.getGameStatus()).isEqualTo(GameStatus.CONTINUE);
    }

    @Test
    @DisplayName("두 팀의 기물 중, 궁만 살아있으면 무승부 처리한다")
    void checkGeneralDiedWhenRemainOnlyGeneral() {
        //given
        Player redPlayer = new Player("flint", Team.RED);
        Player greenPlayer = new Player("abc", Team.GREEN);
        Board board = new Board(
                Map.of(Position.of(1, 1), new General(Team.RED),
                        Position.of(1, 2), new General(Team.GREEN)));
        JanggiGame janggiGame = new JanggiGame(board, redPlayer, greenPlayer);

        //when
        janggiGame.checkWinCondition();

        //then
        assertThat(janggiGame.getGameStatus()).isEqualTo(GameStatus.DRAW);
    }

    @Test
    @DisplayName("두 팀의 기물 중, 궁 이외의 기물이 하나라도 살아있으면 게임을 진행한다")
    void checkGeneralDiedWhenRemainNotOnlyGeneral() {
        //given
        Player redPlayer = new Player("flint", Team.RED);
        Player greenPlayer = new Player("abc", Team.GREEN);
        Board board = new Board(Map.of(Position.of(1, 1), new General(Team.RED),
                Position.of(1, 2), new General(Team.GREEN),
                Position.of(2, 2), new Soldier(Team.GREEN)));
        JanggiGame janggiGame = new JanggiGame(board, redPlayer, greenPlayer);

        //when
        janggiGame.checkWinCondition();

        //then
        assertThat(janggiGame.getGameStatus()).isEqualTo(GameStatus.CONTINUE);
    }
}
