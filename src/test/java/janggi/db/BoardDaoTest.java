package janggi.db;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.dao.BoardDao;
import janggi.model.Color;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BoardDaoTest extends DbTest {

    BoardDao boardDao = new BoardDao(mockConnection());

    @Test
    void 보드테이블을_업데이트한다() {
        OccupiedPositions occupiedPositions = new OccupiedPositions(Map.of(
                new Position(4, 4), new PieceIdentity(Color.RED, PieceType.CANNON),
                new Position(3, 4), new PieceIdentity(Color.BLUE, PieceType.CHARIOT),
                new Position(2, 4), new PieceIdentity(Color.BLUE, PieceType.SOLDIER),
                new Position(1, 4), new PieceIdentity(Color.RED, PieceType.CANNON)
        ));
        boardDao.updateBoard(occupiedPositions);
        assertThat(boardDao.findBoard().generateOccupiedPositions().getPositions()).containsAllEntriesOf(Map.of(
                new Position(4, 4), new PieceIdentity(Color.RED, PieceType.CANNON),
                new Position(3, 4), new PieceIdentity(Color.BLUE, PieceType.CHARIOT),
                new Position(2, 4), new PieceIdentity(Color.BLUE, PieceType.SOLDIER),
                new Position(1, 4), new PieceIdentity(Color.RED, PieceType.CANNON)
        ));
    }
}
