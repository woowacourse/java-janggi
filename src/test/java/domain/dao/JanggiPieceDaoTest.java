package domain.dao;

import domain.Country;
import domain.piece.Cha;
import domain.piece.Piece;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JanggiPieceDaoTest {

    private JanggiPieceDao pieceDao = new JanggiPieceDao(JanggiDBConnect.getConnection());

    @Nested
    class createTest {

        @Test
        void createPieceTableTest() {
            pieceDao.createPieceTableIfNotExist();
        }
    }

    @Nested
    class insertTest {

        @Test
        void insertPieceTest() {
            JanggiGameDao gameDao = new JanggiGameDao(JanggiDBConnect.getConnection());
            Piece piece = new Cha(Country.HAN);
            int num = gameDao.createGame("Test", Country.HAN);
            pieceDao.addPiece(num, piece);

            gameDao.deleteGameRoom(num);
        }
    }
}