package model;

import static org.assertj.core.api.Assertions.assertThat;

import model.pieces.Cannon;
import model.pieces.Horse;
import model.pieces.Piece;
import model.pieces.rule.HorseMoveRule;
import model.pieces.rule.MoveRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import strategy.InnerElephant;

class BoardTest {

    private static Board board;
    @BeforeAll()
    static void setUp() {
        board = new Board();
        Army cho = new Army(new InnerElephant());
        Army han = new Army(new InnerElephant());
        cho.deployTo(board, Country.CHO);
        han.deployTo(board, Country.HAN);
    }

    @Test
    void 보드에_맞는_위치에_잘_들어갔는지_테스트(){
        Piece expected = new Horse(Country.HAN);
        Position position = Position.of(1, 2);
        assertThat(board.isPieceAt(position, expected)).isTrue();
    }

    @Test
    void 말이_목적지에_잘_들어갔는지_테스트(){
        Piece horse = new Horse(Country.CHO);
        Move move = Move.of(Position.of(10, 8), Position.of(8, 7));
        MoveRule moveRule = new HorseMoveRule();
        move.move(board,moveRule.movePatterns());
        assertThat(board.isPieceAt(Position.of(8, 7), horse)).isTrue();
    }
}
