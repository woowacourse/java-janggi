package domain.piece.dao;

import domain.Country;
import domain.JanggiCoordinate;
import domain.PieceInitializer;
import domain.dao.JanggiBoardDao;
import domain.dao.JanggiDao;
import domain.piece.Cha;
import domain.piece.Piece;
import org.junit.jupiter.api.Test;

import java.util.Map;

class JanggiDaoTest {

    private JanggiBoardDao dao = new JanggiBoardDao(JanggiDao.getConnection());

    @Test
    void createTable() {
        dao.createBoardTableIfNotExist();
    }

    @Test
    void addPiece() {
        dao.addPiece(new JanggiCoordinate(3, 5), new Cha(Country.CHO));
    }

    @Test
    void findPieceTest() {
        System.out.println(dao.findPieceByCoordinate(new JanggiCoordinate(3, 5)).getPieceType());
    }

    @Test
    void loadPieceTest() {
        Map<JanggiCoordinate, Piece> map = dao.loadBoard();
        map.entrySet().stream()
                .forEach(janggiCoordinatePieceEntry -> System.out.println(janggiCoordinatePieceEntry.getKey() + " " + janggiCoordinatePieceEntry.getValue().getPieceType().getName()));
    }

    @Test
    void savePiece() {
        Map<JanggiCoordinate, Piece> map = PieceInitializer.init();
        dao.saveGame(map);
    }

    @Test
    void clearPieceTest() {
        dao.clearBoard();
    }
}