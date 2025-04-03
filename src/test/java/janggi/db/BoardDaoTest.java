package janggi.db;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.dao.BoardDao;
import janggi.model.Board;
import janggi.model.Color;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import janggi.model.piece.Cannon;
import janggi.model.piece.Chariot;
import janggi.model.piece.Soldier;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BoardDaoTest extends DbTest {

    BoardDao boardDao = new BoardDao(mockConnection());

    @Test
    void 보드테이블을_업데이트한다() {
        Board board = new Board();
        board.putPiece(new Position(4, 4), new Cannon(Color.RED));
        board.putPiece(new Position(3, 4), new Chariot(Color.BLUE));
        board.putPiece(new Position(2, 4), new Soldier(Color.BLUE));
        board.putPiece(new Position(1, 4), new Cannon(Color.RED));
        boardDao.updateOccupiedPositions(board);
        assertThat(boardDao.findBoard().generateOccupiedPositions().getPositions()).containsAllEntriesOf(Map.of(
                new Position(4, 4), new PieceIdentity(Color.RED, PieceType.CANNON),
                new Position(3, 4), new PieceIdentity(Color.BLUE, PieceType.CHARIOT),
                new Position(2, 4), new PieceIdentity(Color.BLUE, PieceType.SOLDIER),
                new Position(1, 4), new PieceIdentity(Color.RED, PieceType.CANNON)
        ));
    }

    @Test
    void 보드테이블을_조회한다() {
        Board board = new Board();
        board.putPiece(new Position(4, 4), new Cannon(Color.RED));
        board.putPiece(new Position(3, 4), new Chariot(Color.BLUE));
        board.putPiece(new Position(2, 4), new Soldier(Color.BLUE));
        board.putPiece(new Position(1, 4), new Cannon(Color.RED));
        boardDao.updateOccupiedPositions(board);
        assertThat(boardDao.findBoard().generateOccupiedPositions().getPositions()).containsExactlyInAnyOrderEntriesOf(Map.of(
                new Position(4, 4), new PieceIdentity(Color.RED, PieceType.CANNON),
                new Position(3, 4), new PieceIdentity(Color.BLUE, PieceType.CHARIOT),
                new Position(2, 4), new PieceIdentity(Color.BLUE, PieceType.SOLDIER),
                new Position(1, 4), new PieceIdentity(Color.RED, PieceType.CANNON)
        ));
    }
}
