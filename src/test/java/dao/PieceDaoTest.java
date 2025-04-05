package dao;

import database.H2ConnectManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.piece.Country;
import domain.piece.Guard;
import domain.piece.Piece;
import domain.position.Position;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PieceDaoTest {

    private PieceDao pieceDao;

    @BeforeEach
    void setUp() {
        pieceDao = new PieceDao(new H2ConnectManager());
        pieceDao.clearPieces();
    }

    @Test
    void saveAndLoadPieces() {
        // given
        Piece piece1 = new Guard(new Position(1, 1), Country.HAN);
        Piece piece2 = new Guard(new Position(2, 2), Country.CHO);

        // when
        pieceDao.savePiece(piece1);
        pieceDao.savePiece(piece2);
        List<Piece> loaded = pieceDao.loadPieces();

        // then
        assertThat(loaded).hasSize(2);
        assertThat(loaded).anyMatch(p -> p.getCountry() == Country.HAN && p.getPosition().equals(new Position(1, 1)));
        assertThat(loaded).anyMatch(p -> p.getCountry() == Country.CHO && p.getPosition().equals(new Position(2, 2)));
    }

    @Test
    void clearPiece() {
        // given
        pieceDao.savePiece(new Guard(new Position(1, 1), Country.HAN));

        // when
        pieceDao.clearPieces();
        List<Piece> loaded = pieceDao.loadPieces();

        // then
        assertThat(loaded).isEmpty();
    }
}
