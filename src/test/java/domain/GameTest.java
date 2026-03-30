package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
    private Players players;
    private Game game;

    @BeforeEach
    void setUp() {
        players = Players.createInitial(new Name("cho"), new Name("han"));
        Formation choFormation = Formation.from(FormationCommand.from("1"));
        Formation hanFormation = Formation.from(FormationCommand.from("1"));
        Board board = BoardFactory.create(choFormation, hanFormation);
        game = new Game(board, players);
    }

    @Test
    void 턴이_바뀌면_진영이_바뀐다() {
        Side first = game.getCurrentSide();
        players.switchPlayer();
        Side second = game.getCurrentSide();

        assertThat(first).isEqualTo(Side.CHO);
        assertThat(second).isEqualTo(Side.HAN);
    }

    @Test
    void 보드의_기물을_이동이_가능하다() {
        Position from = new Position(0, 3);
        Piece expected = game.getBoard().get(from);
        Position to = new Position(1, 3);
        game.move(from, to);

        assertThat(game.getBoard()).doesNotContainKey(from);
        assertThat(game.getBoard().get(to)).isEqualTo(expected);
    }

    @Test
    void 존재하지_않는_목적지로_이동하는_경우_예외가_발생한다() {
        Position from = new Position(0, 3);
        Position to = new Position(1, 4);

        assertThatThrownBy(() -> game.move(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 장군이_하나면_게임은_종료상태다() {
        Game game = new Game(
                new Board(Map.of(new Position(4, 1), new General(Side.CHO))),
                Players.createInitial(new Name("cho"), new Name("han"))
        );

        assertThat(game.isOver()).isTrue();
    }
}
