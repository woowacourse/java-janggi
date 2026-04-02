package model.game;

import model.board.Army;
import model.board.Board;
import model.board.Country;
import model.board.strategy.InnerElephant;
import model.board.strategy.OuterElephant;
import model.move.Move;
import model.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JanggiGameTest {
    private JanggiGame game;

    @BeforeEach
    void setUp(){
        Board board = new Board();
        Army cho = new Army(new InnerElephant());
        Army han = new Army(new OuterElephant());
        cho.deployTo(board, Country.CHO);
        han.deployTo(board, Country.HAN);
        game = new JanggiGame(board);
    }

    @Test
    void 현재_턴의_기물이_아니면_이동할_수_없다(){
        Move move = Move.of(Position.of(1, 1), Position.of(2, 1));

        assertThatThrownBy(() -> game.move(move))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이동에_성공하면_턴이_변경된다(){
        Move move = Move.of(Position.of(7, 1), Position.of(7, 2));
        game.move(move);
        assertThat(game.turn()).isEqualTo(Country.HAN);
    }

}
