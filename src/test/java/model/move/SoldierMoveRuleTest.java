package model.move;

import model.board.Board;
import model.board.Country;
import model.pieces.Piece;
import model.pieces.PieceType;
import model.position.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SoldierMoveRuleTest {
    @Test
     void 초나라_졸은_궁성에서_전진_대각선으로_이동_가능() {
        Board board = new Board();
        Piece soldier = new Piece(Country.CHO, PieceType.SOLDIER);
        Position from = Position.of(2, 5);

        board.place(from, soldier);
        Move move = Move.of(from, Position.of(1, 4));
        assertThat(soldier.canMove(move, board)).isTrue();
    }

    @Test
    void 초나라_졸은_궁성에서_후진_대각선으로_이동_불가() {
        Board board = new Board();
        Piece soldier = new Piece(Country.CHO, PieceType.SOLDIER);
        Position from = Position.of(2, 5);

        board.place(from, soldier);
        Move move = Move.of(from, Position.of(3, 4));
        assertThat(soldier.canMove(move, board)).isFalse();
    }
}
