package model;

import static org.assertj.core.api.Assertions.assertThat;

import model.board.Army;
import model.board.Board;
import model.board.Country;
import model.board.strategy.InnerElephant;
import model.board.strategy.OuterElephant;
import model.move.Move;
import model.pieces.Chariot;
import model.pieces.Elephant;
import model.pieces.Horse;
import model.pieces.Piece;
import model.position.Position;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BoardTest {

    private static Board board;
    @BeforeAll()
    static void setUp() {
        board = new Board();
        Army cho = new Army(new InnerElephant());
        Army han = new Army(new OuterElephant());
        cho.deployTo(board, Country.CHO);
        han.deployTo(board, Country.HAN);
    }

    @Test
    void 보드에_맞는_위치에_잘_들어갔는지_테스트(){
        Piece expected = new Horse(Country.HAN);
        Position position = Position.of(1, 3);
        assertThat(board.isPieceAt(position, expected)).isTrue();
    }

    @Test
    void 마가_목적지에_잘_들어갔는지_테스트(){
        Piece horse = new Horse(Country.CHO);
        Move move = Move.of(Position.of(10, 8), Position.of(8, 7));
        board.move(move);
        assertThat(board.isPieceAt(Position.of(8, 7), horse)).isTrue();
    }

    @Test
    void 상이_목적지에_잘_들어갔는지_테스트(){
        Piece horse = new Elephant(Country.HAN);
        Move move = Move.of(Position.of(1, 2), Position.of(4, 4));
        board.move(move);
        assertThat(board.isPieceAt(Position.of(4, 4), horse)).isTrue();
    }

    @Test
    void 차가_목적지에_잘_들어갔는지_테스트(){
        Piece horse = new Chariot(Country.CHO);
        Move move = Move.of(Position.of(10, 1), Position.of(8, 1));
        board.move(move);
        assertThat(board.isPieceAt(Position.of(8, 1), horse)).isTrue();
    }


}
