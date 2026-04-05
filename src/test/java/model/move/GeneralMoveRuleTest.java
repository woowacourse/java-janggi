package model.move;

import model.board.Board;
import model.board.Country;
import model.pieces.Piece;
import model.pieces.PieceType;
import model.position.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GeneralMoveRuleTest {

    @Test
    void 장은_자신의_궁성_안에서만_이동할_수_있다(){
        Board board = new Board();
        Piece general = new Piece(Country.CHO, PieceType.GENERAL);
        Position from = Position.of(9, 5);

        board.place(from,general);

        Move insideMove = Move.of(from, Position.of(9, 4));
        Move outsideMove = Move.of(from, Position.of(7, 5));

        assertThat(general.canMove(insideMove, board)).isTrue();
        assertThat(general.canMove(outsideMove, board)).isFalse();
    }

    @Test
    void 장은_자신의_궁성_안에서_대각선으로_이동할_수_있다(){
        Board board = new Board();
        Piece general = new Piece(Country.CHO, PieceType.GENERAL);
        Position from = Position.of(9, 5);

        board.place(from, general);
        Move diagonalMove = Move.of(from, Position.of(8, 4));

        assertThat(general.canMove(diagonalMove, board)).isTrue();
    }
}
