package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.piece.Chariot;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardDaoTest {

    private Executor fakeDatabaseDatabaseExecutor;
    private BoardDao boardDao;

    @BeforeEach
    public void setup() {
        fakeDatabaseDatabaseExecutor = new FakeExecutor();
        boardDao = new BoardDao(fakeDatabaseDatabaseExecutor);
    }

    @Test
    public void testSaveBoard() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(Row.ONE, Column.ONE), new Chariot(PieceColor.RED));

        boardDao.saveBoard(board);

        assertTrue(((FakeExecutor) fakeDatabaseDatabaseExecutor).isExecuteBatchCalled());
    }

    @Test
    public void testLoadBoard() {
        Map<Position, Piece> expectedBoard = new HashMap<>();
        expectedBoard.put(new Position(Row.ONE, Column.ONE), new Chariot(PieceColor.RED));
        ((FakeExecutor) fakeDatabaseDatabaseExecutor).setQueryResult(expectedBoard);

        // loadBoard 호출
        Map<Position, Piece> board = boardDao.loadBoard();

        assertNotNull(board);
        assertEquals(1, board.size());
        assertTrue(board.containsKey(new Position(Row.ONE, Column.ONE)));
        assertEquals(PieceType.CHARIOT, board.get(new Position(Row.ONE, Column.ONE)).getType());
        assertEquals(PieceColor.RED, board.get(new Position(Row.ONE, Column.ONE)).getColor());
    }

    @Test
    public void testUpdatePosition() {
        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.ONE, Column.TWO);

        boardDao.updatePosition(source, destination);

        assertTrue(((FakeExecutor) fakeDatabaseDatabaseExecutor).isExecuteUpdateCalled());
    }

    @Test
    public void testDeleteBoard() {
        boardDao.deleteBoard();

        assertTrue(((FakeExecutor) fakeDatabaseDatabaseExecutor).isExecuteUpdateCalled());
    }
}
