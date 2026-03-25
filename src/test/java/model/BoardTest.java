package model;

import model.pieces.Cannon;
import model.pieces.Piece;
import org.junit.jupiter.api.Test;
import strategy.InnerElephant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    @Test
    void 보드에_맞는_위치에_잘_들어갔는지_테스트(){
        Board board = new Board();
        Army cho = new Army(new InnerElephant());
        Army han = new Army(new InnerElephant());
        cho.deployTo(board, Country.CHO);
        han.deployTo(board, Country.HAN);

        Piece expected = new Cannon(Country.CHO);
        Position position = Position.of(8, 2);
        assertThat(board.isPieceAt(position, expected)).isTrue();
    }
}
