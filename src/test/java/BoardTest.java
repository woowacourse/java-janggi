import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Board;
import domain.Camp;
import domain.InvalidMoveException;
import domain.Position;
import domain.pieces.Cannon;
import domain.pieces.Chariot;
import domain.pieces.General;
import domain.pieces.Horse;
import domain.pieces.Soldier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    void 특정_위치에_기물을_놓을_수_있다() {
        Board board = new Board();
        Position position = new Position(1, 2);
        Horse piece = new Horse(Camp.HAN);
        board.locatePiece(position, piece);

        Assertions.assertEquals(board.getPieceFrom(position), piece);
    }

    @Test
    void 같은_팀_기물은_잡을_수_없다() {
        Board board = new Board();
        Position position = new Position(1, 2);
        Horse locatedPiece = new Horse(Camp.HAN);
        Horse anotherPiece = new Horse(Camp.HAN);
        board.locatePiece(position, locatedPiece);

        assertThatThrownBy(() -> board.locatePiece(position, anotherPiece)).isInstanceOf(
                InvalidMoveException.class).hasMessageContaining("[ERROR]", "같은 팀");
    }

    @Test
    void 기물을_이동시킬_수_있다() {
        Board board = new Board();
        Position fromPosition = new Position(1, 2);
        Position toPosition = new Position(1, 3);

        Soldier piece = new Soldier(Camp.HAN);
        board.locatePiece(fromPosition, piece);

        board.move(fromPosition, toPosition);

        Assertions.assertFalse(board.isExist(fromPosition));
        Assertions.assertEquals(board.getPieceFrom(toPosition).getClass(), piece.getClass());
    }

    @Test
    void 보드_특정_포지션에_기물이_있다() {
        Board board = new Board();
        Position position = new Position(1, 2);
        Soldier piece = new Soldier(Camp.HAN);

        board.locatePiece(position, piece);

        Assertions.assertTrue(board.isExist(position));
    }

    @Test
    void 보드_특정_포지션에_기물이_Cannon이_아니다() {
        Board board = new Board();
        Position position = new Position(1, 2);
        Cannon piece = new Cannon(Camp.HAN);

        board.locatePiece(position, piece);

        Assertions.assertFalse(board.isNotCannon(position));
    }

    @Test
    void 상대_왕을_잡으면_게임_종료() {
        Board board = new Board();
        Position choGeneralPos = new Position(4, 1);
        Position hanGeneralPos = new Position(4, 6);
        Position hanChariotPos = new Position(4, 5);

        board.locatePiece(choGeneralPos, new General(Camp.CHO));
        board.locatePiece(hanGeneralPos, new General(Camp.HAN));
        board.locatePiece(hanChariotPos, new Chariot(Camp.HAN));

        board.move(hanChariotPos, choGeneralPos);

        Assertions.assertTrue(board.isGameOver());
    }

    @Test
    void 한나라_현재_남은_기물의_점수를_구할_수_있다() {
        Board board = new Board();
        board.generatePiecesBy(Camp.HAN, 1);

        double totalScore = board.calculateScoreByCamp(Camp.HAN);

        Assertions.assertEquals(totalScore, 73.5);
    }

    @Test
    void 초나라_현재_남은_기물의_점수를_구할_수_있다() {
        Board board = new Board();
        board.generatePiecesBy(Camp.CHO, 1);

        double totalScore = board.calculateScoreByCamp(Camp.CHO);

        Assertions.assertEquals(totalScore, 72);
    }
}
