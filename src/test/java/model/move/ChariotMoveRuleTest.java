package model.move;

import model.board.Board;
import model.board.Country;
import model.pieces.Piece;
import model.pieces.PieceType;
import model.position.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChariotMoveRuleTest {
    @Test
    void 초나라_차는_초나라_궁성안에서_대각선으로_한칸_이동할_수_있다(){
        Board board = new Board();
        Piece chariot = new Piece(Country.CHO, PieceType.CHARIOT);
        Position from = Position.of(10, 4);
        board.place(from, chariot);
        Move move = Move.of(from, Position.of(9, 5));
        assertThat(chariot.canMove(move, board)).isTrue();
    }

    @Test
    void 초나라_차는_초나라_궁성안에서_대각선으로_두칸_이동할_수_있다(){
        Board board = new Board();
        Piece chariot = new Piece(Country.CHO, PieceType.CHARIOT);
        Position from = Position.of(10, 4);
        board.place(from, chariot);
        Move move = Move.of(from, Position.of(8, 6));
        assertThat(chariot.canMove(move, board)).isTrue();
    }

    @Test
    void 초나라_차는_한나라_궁성_안에서_대각선으로_한칸_이동할_수_있다(){
        Board board = new Board();
        Piece chariot = new Piece(Country.CHO, PieceType.CHARIOT);
        Position from = Position.of(3, 4);
        board.place(from, chariot);
        Move move = Move.of(from, Position.of(2, 5));
        assertThat(chariot.canMove(move, board)).isTrue();
    }

    @Test
    void 초나라_차는_한나라_궁성안에서_대각선으로_두칸_이동할_수_있다(){
        Board board = new Board();
        Piece chariot = new Piece(Country.CHO, PieceType.CHARIOT);
        Position from = Position.of(3, 4);
        board.place(from, chariot);
        Move move = Move.of(from, Position.of(1, 6));
        assertThat(chariot.canMove(move, board)).isTrue();
    }
}
