package model.move;

import model.board.Board;
import model.board.Country;
import model.pieces.Piece;
import model.pieces.PieceType;
import model.position.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CannonMoveRuleTest {

    @Test
    void 초나라_포는_초나라_궁성안_대각선에서_사이에_기물이_하나_있으면_이동할_수_있다() {
        Board board = new Board();
        Piece cannon = new Piece(Country.CHO, PieceType.CANNON);
        Piece bridge = new Piece(Country.CHO, PieceType.SOLDIER);
        Position from = Position.of(10, 4);
        board.place(from, cannon);
        board.place(Position.of(9, 5), bridge);
        Move move = Move.of(from, Position.of(8, 6));
        assertThat(cannon.canMove(move, board)).isTrue();
    }

    @Test
    void 초나라_포는_초나라_궁성안_대각선에서_사이에_기물이_없으면_이동할_수_없다() {
        Board board = new Board();
        Piece cannon = new Piece(Country.CHO, PieceType.CANNON);
        Position from = Position.of(10, 4);
        board.place(from, cannon);
        Move move = Move.of(from, Position.of(8, 6));
        assertThat(cannon.canMove(move, board)).isFalse();
    }

    @Test
    void 초나라_포는_한나라_궁성안_대각선에서_사이에_기물이_하나_있으면_이동할_수_있다() {
        Board board = new Board();
        Piece cannon = new Piece(Country.CHO, PieceType.CANNON);
        Piece bridge = new Piece(Country.HAN, PieceType.SOLDIER);
        Position from = Position.of(3, 4);
        board.place(from, cannon);
        board.place(Position.of(2, 5), bridge);
        Move move = Move.of(from, Position.of(1, 6));
        assertThat(cannon.canMove(move, board)).isTrue();
    }

    @Test
    void 초나라_포는_한나라_궁성안_대각선에서_사이에_기물이_없으면_이동할_수_없다() {
        Board board = new Board();
        Piece cannon = new Piece(Country.CHO, PieceType.CANNON);
        Position from = Position.of(3, 4);
        board.place(from, cannon);
        Move move = Move.of(from, Position.of(1, 6));
        assertThat(cannon.canMove(move, board)).isFalse();
    }
}
