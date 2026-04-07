package domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.coordination.Coordination;
import fixture.BoardFixtureFactory;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    @Test
    void 왕이_잡히면_게임이_종료되고_승자의_차례를_유지한다() {
        Board board = new Board(new HashMap<>(BoardFixtureFactory.create("1", "1")
                .moveIgnoringValidation(Coordination.of(1, 10), Coordination.of(5, 3))
                .map()));
        JanggiGame janggiGame = JanggiGame.restore(new GameState(Turn.CHO, board.getBoard()));

        janggiGame.playTurn(List.of(5, 3), List.of(5, 2));

        assertThat(janggiGame.isGameEnd()).isTrue();
        assertThat(janggiGame.turn()).isEqualTo(Turn.CHO);
    }
}
