package domain.dao;

import domain.Country;
import domain.piece.Cha;
import domain.piece.Piece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

class JanggiPieceDaoTest {

    private JanggiPieceDao pieceDao = new JanggiPieceDao(JanggiDBConnect.getConnection());

    @Nested
    class insertTest {

        @Test
        void insertPieceTest() {
            JanggiGameDao gameDao = new JanggiGameDao(JanggiDBConnect.getConnection());
            Piece piece = new Cha(Country.HAN);
            int id = gameDao.createGame("Test", Country.HAN);
            Assertions.assertDoesNotThrow(() -> pieceDao.addPiecesBatch(id, List.of(piece)));

            gameDao.deleteGameRoom(id);
        }
    }
}