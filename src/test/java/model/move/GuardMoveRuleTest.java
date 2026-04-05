package model.move;

import model.board.Board;
import model.board.Country;
import model.pieces.Piece;
import model.pieces.PieceType;
import model.position.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GuardMoveRuleTest {
    @Test
    void 사는_자신의_궁성_안에서만_이동할_수_있다(){
        Board board = new Board();
        Piece guard = new Piece(Country.CHO, PieceType.GUARD);
        Position from = Position.of(10, 4);

        board.place(from, guard);

        Move insideMove = Move.of(from, Position.of(9, 4));
        Move outsideMove = Move.of(from, Position.of(10, 3));

        assertThat(guard.canMove(insideMove, board)).isTrue();
        assertThat(guard.canMove(outsideMove, board)).isFalse();
    }

    @Test
    void 사는_자신의_궁성_안에서_대각선으로_이동할_수_있다(){
        Board board = new Board();
        Piece guard = new Piece(Country.CHO, PieceType.GUARD);
        Position from = Position.of(9, 5);
        board.place(from, guard);

        Move diagonalMove = Move.of(from, Position.of(10, 4));
        assertThat(guard.canMove(diagonalMove, board)).isTrue();
    }
}
