package model.game;

import model.board.Army;
import model.board.Board;
import model.board.Country;
import model.board.strategy.InnerElephant;
import model.board.strategy.OuterElephant;
import model.move.Move;
import model.pieces.Piece;
import model.pieces.PieceType;
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

    @Test
    void 장을_잡으면_게임이_종료된다(){
        Move moveChariot_1 = Move.of(Position.of(10, 9), Position.of(9, 9));
        game.move(moveChariot_1);

        Move moveGeneral_1 = Move.of(Position.of(2, 5), Position.of(2, 6));
        game.move(moveGeneral_1);

        Move moveChariot_2 = Move.of(Position.of(9, 9), Position.of(9, 6));
        game.move(moveChariot_2);

        Move moveGeneral_2 = Move.of(Position.of(2, 6), Position.of(3, 6));
        game.move(moveGeneral_2);

        Move moveChariot_3 = Move.of(Position.of(9, 6), Position.of(3, 6));
        game.move(moveChariot_3);

        assertThatThrownBy(() ->
                game.move(Move.of(Position.of(7, 1), Position.of(6, 1))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 한나라의_장이_잡히면_초나라가_승리한다(){
        Board board = new Board();
        JanggiGame game = new JanggiGame(board);

        board.place(Position.of(2, 5), new Piece(Country.HAN, PieceType.GENERAL));
        board.place(Position.of(6, 5), new Piece(Country.CHO, PieceType.CHARIOT));
        game.move(Move.of(Position.of(6, 5), Position.of(2, 5)));

        assertThat(game.winner()).isEqualTo(Country.CHO);
    }

    @Test
    void 초나라의_장이_잡히면_한나라가_승리한다(){
        Board board = new Board();
        JanggiGame game = new JanggiGame(board);

        board.place(Position.of(9, 5), new Piece(Country.CHO, PieceType.GENERAL));
        game.move(Move.of(Position.of(9, 5), Position.of(8, 5)));
        board.place(Position.of(5, 5), new Piece(Country.HAN, PieceType.CHARIOT));
        game.move(Move.of(Position.of(5, 5), Position.of(8, 5)));

        assertThat(game.winner()).isEqualTo(Country.HAN);
    }
}
