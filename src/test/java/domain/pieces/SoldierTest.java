package domain.pieces;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Board;
import domain.Camp;
import domain.InvalidMoveException;
import domain.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SoldierTest {

    @Test
    void 한나라_기준_앞으로_한_칸_전진_가능() {
        Board board = Board.empty();
        Soldier soldier = new Soldier(Camp.HAN);
        Position fromPosition = new Position(0, 3);
        Position toPosition = new Position(0, 4);

        Assertions.assertTrue(soldier.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 한나라_기준_왼쪽으로_한_칸_전진_가능() {
        Board board = Board.empty();
        Soldier soldier = new Soldier(Camp.HAN);
        Position fromPosition = new Position(0, 3);
        Position toPosition = new Position(1, 3);

        Assertions.assertTrue(soldier.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 한나라_기준_뒤로_한_칸_후진_불가() {
        Board board = Board.empty();
        Soldier soldier = new Soldier(Camp.HAN);
        Position fromPosition = new Position(0, 3);
        Position toPosition = new Position(0, 2);

        Assertions.assertFalse(soldier.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 같은_팀_기물_막힘으로_앞으로_한_칸_전진_불가() {
        Board board = Board.empty();
        Soldier soldier = new Soldier(Camp.HAN);
        Horse piece = new Horse(Camp.HAN);

        Position fromPosition = new Position(0, 3);
        Position toPosition = new Position(0, 4);

        board.locatePiece(fromPosition, soldier);
        board.locatePiece(toPosition, piece);

        assertThatThrownBy(() -> board.move(fromPosition, toPosition)).isInstanceOf(
                InvalidMoveException.class).hasMessageContaining("[ERROR]", "같은 팀");
    }

    @Test
    void 상대_팀_기물_잡고_앞으로_한_칸_전진_가능() {
        Board board = Board.empty();
        Soldier soldier = new Soldier(Camp.HAN);
        Horse piece = new Horse(Camp.CHO);

        Position fromPosition = new Position(0, 3);
        Position toPosition = new Position(0, 4);

        board.locatePiece(fromPosition, soldier);
        board.locatePiece(toPosition, piece);

        board.move(fromPosition, toPosition);

        Assertions.assertEquals(board.getPieceFrom(toPosition), soldier);
    }

    @Test
    void 초나라_기준_앞으로_한_칸_전진_가능() {
        Board board = Board.empty();
        Soldier soldier = new Soldier(Camp.CHO);
        Position fromPosition = new Position(0, 6);
        Position toPosition = new Position(0, 5);

        Assertions.assertTrue(soldier.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 초나라_기준_오른쪽으로_한_칸_전진_가능() {
        Board board = Board.empty();
        Soldier soldier = new Soldier(Camp.CHO);
        Position fromPosition = new Position(0, 6);
        Position toPosition = new Position(1, 6);

        Assertions.assertTrue(soldier.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 초나라_기준_뒤로_한_칸_후진_불가() {
        Board board = Board.empty();
        Soldier soldier = new Soldier(Camp.CHO);
        Position fromPosition = new Position(0, 6);
        Position toPosition = new Position(0, 7);

        Assertions.assertFalse(soldier.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 초나라_기준_궁성_대각선_이동_가능() {
        Board board = Board.empty();
        Soldier soldier = new Soldier(Camp.CHO);
        Position fromPosition = new Position(3, 2);
        Position toPosition = new Position(4, 1);

        Assertions.assertTrue(soldier.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 초나라_기준_궁성_대각선_이동_불가() {
        Board board = Board.empty();
        Soldier soldier = new Soldier(Camp.CHO);
        Position fromPosition = new Position(3, 2);
        Position toPosition = new Position(2, 1);

        Assertions.assertFalse(soldier.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 초나라_기준_궁성_대각선_후진_불가() {
        Board board = Board.empty();
        Soldier soldier = new Soldier(Camp.CHO);
        Position fromPosition = new Position(3, 0);
        Position toPosition = new Position(4, 1);

        Assertions.assertFalse(soldier.canMove(fromPosition, toPosition, board));
    }
}
