package janggi.board.dao;

import janggi.board.JanggiBoard;
import janggi.piece.Gung;
import janggi.piece.Piece;
import janggi.value.JanggiPosition;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JanggiBoardDaoTest {
    private FakeJanggiBoardDao fakeJanggiBoardDao;

    @BeforeEach
    public void setUp() {
        fakeJanggiBoardDao = new FakeJanggiBoardDao();
    }

    @Test
    public void testInsertPieces() {
        //given
        List<Piece> choPieces = List.of(Gung.from(new JanggiPosition(4,8)));
        List<Piece> hanPieces = List.of(Gung.from(new JanggiPosition(4,1)));
        JanggiBoard janggiBoard = new JanggiBoard(choPieces, hanPieces);

        //when
        fakeJanggiBoardDao.insertPieces(janggiBoard);

        //then
        assertEquals(1, fakeJanggiBoardDao.selectChoRecords().size());
        assertEquals(1, fakeJanggiBoardDao.selectHanRecords().size());
    }

    @Test
    public void testUpdateRecords() {
        //given
        List<Piece> choPieces = List.of(Gung.from(new JanggiPosition(4,8)));
        List<Piece> hanPieces = List.of(Gung.from(new JanggiPosition(4,1)));
        JanggiBoard janggiBoard = new JanggiBoard(choPieces, hanPieces);

        fakeJanggiBoardDao.insertPieces(janggiBoard);

        //when
        fakeJanggiBoardDao.updateRecords(new JanggiPosition(4,8), new JanggiPosition(4,9),1);

        //then
        assertEquals(new JanggiPosition(4,9), fakeJanggiBoardDao.selectChoRecords().getFirst().getPosition());
    }

    @Test
    public void testDeleteRecords() {
        //given
        List<Piece> choPieces = List.of(Gung.from(new JanggiPosition(4,8)));
        List<Piece> hanPieces = List.of(Gung.from(new JanggiPosition(4,1)));
        JanggiBoard janggiBoard = new JanggiBoard(choPieces, hanPieces);

        fakeJanggiBoardDao.insertPieces(janggiBoard);

        //when
        fakeJanggiBoardDao.deleteRecords(new JanggiPosition(4,8), 1);

        //then
        assertTrue(fakeJanggiBoardDao.selectChoRecords().isEmpty());
    }
}
