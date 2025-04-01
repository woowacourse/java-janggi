package janggi.database.dao;

import static janggi.domain.Team.RED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.TestFixture;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.direction.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoTest {

    private final PieceDao pieceDao = TestFixture.getPieceDao();

    @BeforeEach
    void setUp() {
        pieceDao.deleteAll();
    }

    @DisplayName("피스를 추가한다.")
    @Test
    void addTest() {

        // given

        // when
        final Long pieceId = pieceDao.add("Solider", "RED", 1, 1);

        // then
        assertThat(pieceId).isGreaterThan(0L);
    }

    @DisplayName("피스를 삭제한다.")
    @Test
    void deleteTest() {

        // given
        final Piece soldier = new Soldier(new Position(1, 1), RED);
        pieceDao.add("Solider", "RED", 1, 1);

        // when
        pieceDao.delete(1, 1);

        // then
        assertThat(pieceDao.findAll().size()).isEqualTo(0);
    }

    @DisplayName("모든 피스를 삭제한다.")
    @Test
    void deleteAllPiece() {

        // given
        pieceDao.add("Solider", "RED", 1, 1);
        pieceDao.add("Solider", "RED", 1, 2);

        // when
        pieceDao.deleteAll();

        // then
        assertThat(pieceDao.findAll().size()).isEqualTo(0);
    }

    @DisplayName("모든 피스를 찾는다.")
    @Test
    void findAllPiece() {

        // given
        pieceDao.add("Solider", "RED", 1, 1);
        pieceDao.add("Solider", "RED", 1, 2);

        // when
        final int size = pieceDao.findAll().size();

        // then
        assertThat(size).isEqualTo(2);
    }

}
