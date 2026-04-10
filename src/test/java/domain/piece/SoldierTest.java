package domain.piece;

import domain.board.Board;
import domain.Camp;
import domain.position.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SoldierTest {

    @Test
    void 한나라_기준_앞으로_한_칸_전진_가능() {
        Board board = new Board();
        Soldier soldier = new Soldier(Camp.HAN);
        Position fromPosition = new Position(0, 3);
        Position toPosition = new Position(0, 4);

        Assertions.assertTrue(soldier.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 한나라_기준_왼쪽으로_한_칸_전진_가능() {
        Board board = new Board();
        Soldier soldier = new Soldier(Camp.HAN);
        Position fromPosition = new Position(0, 3);
        Position toPosition = new Position(1, 3);

        Assertions.assertTrue(soldier.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 한나라_기준_뒤로_한_칸_후진_불가() {
        Board board = new Board();
        Soldier soldier = new Soldier(Camp.HAN);
        Position fromPosition = new Position(0, 3);
        Position toPosition = new Position(0, 2);

        Assertions.assertFalse(soldier.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 같은_팀_기물_막힘으로_앞으로_한_칸_전진_불가() {
        Board board = new Board();
        Soldier soldier = new Soldier(Camp.HAN);
        Soldier piece = new Soldier(Camp.HAN);

        Position fromPosition = new Position(0, 3);
        Position toPosition = new Position(0, 4);

        board.locatePiece(fromPosition, soldier);
        board.locatePiece(toPosition, piece);

        assertThatThrownBy(() -> board.move(fromPosition, toPosition)).isInstanceOf(
                IllegalArgumentException.class).hasMessageContaining("[ERROR]", "같은 팀");
    }

    @Test
    void 상대_팀_기물_잡고_앞으로_한_칸_전진_가능() {
        Board board = new Board();
        Soldier soldier = new Soldier(Camp.HAN);
        Soldier piece = new Soldier(Camp.CHO);

        Position fromPosition = new Position(0, 3);
        Position toPosition = new Position(0, 4);

        board.locatePiece(fromPosition, soldier);
        board.locatePiece(toPosition, piece);

        board.move(fromPosition, toPosition);

        Assertions.assertEquals(board.getPieceFrom(toPosition), soldier);
    }

    @Test
    void 초나라_기준_앞으로_한_칸_전진_가능() {
        Board board = new Board();
        Soldier soldier = new Soldier(Camp.CHO);
        Position fromPosition = new Position(0, 6);
        Position toPosition = new Position(0, 5);

        Assertions.assertTrue(soldier.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 초나라_기준_오른쪽으로_한_칸_전진_가능() {
        Board board = new Board();
        Soldier soldier = new Soldier(Camp.CHO);
        Position fromPosition = new Position(0, 6);
        Position toPosition = new Position(1, 6);

        Assertions.assertTrue(soldier.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 초나라_기준_뒤로_한_칸_후진_불가() {
        Board board = new Board();
        Soldier soldier = new Soldier(Camp.CHO);
        Position fromPosition = new Position(0, 6);
        Position toPosition = new Position(0, 7);

        Assertions.assertFalse(soldier.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 초나라_졸_한나라_궁성_대각선_이동() {
        Board board = new Board();
        Soldier soldier = new Soldier(Camp.CHO);
        Position fromPosition = new Position(3,2);
        Position toPosition = new Position(4,2);

        Assertions.assertTrue(soldier.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 초나라_졸_궁성에_있지만_바깥대각선_불가() {
        Board board = new Board();
        Soldier soldier = new Soldier(Camp.CHO);
        Position fromPosition = new Position(3,2);
        Position toPosition = new Position(2,1);

        Assertions.assertFalse(soldier.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 초나라_졸_궁성_이어지지_않은_대각선_이동불가() {
        Board board = new Board();
        Soldier soldier = new Soldier(Camp.CHO);
        Position fromPosition = new Position(4,2);
        Position toPosition = new Position(3,1);

        Assertions.assertFalse(soldier.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 초나라_졸_궁성_안에서_뒤로대각_불가() {
        Board board = new Board();
        Soldier soldier = new Soldier(Camp.CHO);
        Position fromPosition = new Position(4,1);
        Position toPosition = new Position(3,2);

        Assertions.assertFalse(soldier.canMove(fromPosition, toPosition, board));
    }
}
