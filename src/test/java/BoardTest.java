import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Board;
import domain.piece.Camp;
import domain.position.Position;
import domain.piece.Soldier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    void 특정_위치에_기물을_놓을_수_있다() {
        Board board = new Board();
        Position position = new Position(1, 2);
        Soldier piece = new Soldier(Camp.HAN);
        board.locatePiece(position, piece);

        Assertions.assertEquals(board.getPieceFrom(position), piece);
    }

    @Test
    void 같은_팀_기물은_잡을_수_없다() {
        Board board = new Board();
        Position position = new Position(1, 2);
        Soldier locatedPiece = new Soldier(Camp.HAN);
        Soldier anotherPiece = new Soldier(Camp.HAN);
        board.locatePiece(position, locatedPiece);

        assertThatThrownBy(() -> board.locatePiece(position, anotherPiece)).isInstanceOf(
                IllegalArgumentException.class).hasMessageContaining("[ERROR]", "같은 팀");
    }

    @Test
    void 기물을_이동시킬_수_있다() {
        Board board = new Board();
        Position fromPosition = new Position(1, 2);
        Position toPosition = new Position(1, 3);

        Soldier piece = new Soldier(Camp.HAN);
        board.locatePiece(fromPosition, piece); //초기 배치

        board.move(fromPosition, toPosition); // A위치의 기물을 B로 옮긴다.

        Assertions.assertFalse(board.isExist(fromPosition));
        Assertions.assertEquals(board.getPieceFrom(toPosition).getClass(), piece.getClass());
    }
}
