package model.move;

import model.board.Board;
import model.board.Country;
import model.pieces.Piece;
import model.pieces.PieceType;
import model.position.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CannonMoveRuleTest {
    @Test
    void 포는_궁성_대각선에서_기물이_하나_있으면_이동_가능(){
        Board board = new Board();
        Piece cannon = new Piece(Country.CHO, PieceType.CANNON);
        Piece bridge = new Piece(Country.CHO, PieceType.SOLDIER);

        board.place(Position.of(10, 4), cannon);
        board.place(Position.of(9, 5), bridge);

        Move move = Move.of(Position.of(10, 4), Position.of(8, 6));
        assertThat(cannon.canMove(move, board)).isTrue();
    }

    @Test
    void 포는_궁성_대각선에서_기물이_없으면_이동_불가(){
        Board board = new Board();
        Piece cannon = new Piece(Country.CHO, PieceType.CANNON);

        board.place(Position.of(10, 4), cannon);
        Move move = Move.of(Position.of(10, 4), Position.of(8, 6));
        assertThat(cannon.canMove(move, board)).isFalse();
    }
}
