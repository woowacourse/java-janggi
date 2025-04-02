package domain.dao;

import domain.Country;
import domain.JanggiCoordinate;
import domain.piece.Cha;
import domain.piece.Piece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiCoordinateDaoTest {

    private final JanggiCoordinateDao coordinateDao = new JanggiCoordinateDao(JanggiDBConnect.getConnection());

    @Nested
    class insertTest {

        @Test
        void insertPieceToCoordinateTest() {
            JanggiCoordinate coordinate = new JanggiCoordinate(3, 4);
            Piece piece = new Cha(Country.HAN);

            JanggiGameDao gameDao = new JanggiGameDao(JanggiDBConnect.getConnection());
            JanggiPieceDao pieceDao = new JanggiPieceDao(JanggiDBConnect.getConnection());
            int gameId = gameDao.createGame("TEST", Country.HAN);
            List<Integer> pieceIds = pieceDao.addPiecesBatch(gameId, List.of(piece));

            Assertions.assertDoesNotThrow(() -> coordinateDao.addPieceToCoordinateBatch(pieceIds, List.of(coordinate), gameId));
            gameDao.deleteGameRoom(gameId);
        }
    }

    @Nested
    class findTest {

        @Test
        void findAllPiecesTest() {
            Piece piece1 = new Cha(Country.HAN);
            Piece piece2 = new Cha(Country.HAN);

            JanggiGameDao gameDao = new JanggiGameDao(JanggiDBConnect.getConnection());
            JanggiPieceDao pieceDao = new JanggiPieceDao(JanggiDBConnect.getConnection());
            int gameId = gameDao.createGame("TEST1", Country.HAN);

            List<Integer> pieceIds = pieceDao.addPiecesBatch(gameId, List.of(piece1, piece2));
            coordinateDao.addPieceToCoordinateBatch(
                    pieceIds,
                    List.of(new JanggiCoordinate(1, 2), new JanggiCoordinate(2, 3)),
                    gameId);

            Map<JanggiCoordinate, Piece> map = coordinateDao.findAllPieces(gameId);

            Assertions.assertAll(
                    () -> assertThat(map.get(new JanggiCoordinate(1, 2))).isEqualTo(new Cha(Country.HAN)),
                    () -> assertThat(map.get(new JanggiCoordinate(2, 3))).isEqualTo(new Cha(Country.HAN))
            );

            gameDao.deleteGameRoom(gameId);
        }
    }
}