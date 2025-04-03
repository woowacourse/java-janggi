package infra.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import infra.entity.PieceEntity;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PieceDaoTest {

    private final PieceDao pieceDao = new PieceDao();

    @BeforeEach
    void setUp() {
        pieceDao.deleteAll();
    }

    @AfterEach
    void tearDown() {
        pieceDao.deleteAll();
    }

    @Nested
    class ValidCases {

        @Test
        @DisplayName("기물을 저장할 수 있다.")
        void save() {
            // given
            PieceEntity piece = new PieceEntity("CANNON", "RED", 1, 2);
            pieceDao.save(piece);

            // when
            List<PieceEntity> pieces = pieceDao.findAll();

            // then
            PieceEntity savedPiece = pieces.get(0);
            assertSoftly(softly -> {
                softly.assertThat(pieces).hasSize(1);
                softly.assertThat(savedPiece.getId()).isNotNull();
                softly.assertThat(savedPiece.getDtype()).isEqualTo("CANNON");
                softly.assertThat(savedPiece.getTeam()).isEqualTo("RED");
                softly.assertThat(savedPiece.getColumnIndex()).isEqualTo(1);
                softly.assertThat(savedPiece.getRowIndex()).isEqualTo(2);
            });
        }

        @Test
        @DisplayName("기물이 존재하는지 확인할 수 있다.")
        void exists() {
            // when & then
            assertSoftly(softly -> {
                softly.assertThat(pieceDao.exists()).isFalse();
                pieceDao.save(new PieceEntity("HORSE", "GREEN", 0, 0));
                softly.assertThat(pieceDao.exists()).isTrue();
            });
        }

        @Test
        @DisplayName("여러 기물을 모두 조회할 수 있다.")
        void findAll() {
            // given
            PieceEntity piece1 = new PieceEntity("CHARIOT", "RED", 0, 0);
            PieceEntity piece2 = new PieceEntity("ELEPHANT", "GREEN", 1, 1);
            PieceEntity piece3 = new PieceEntity("JJU", "RED", 2, 2);

            PieceEntity savedPiece1 = pieceDao.save(piece1);
            PieceEntity savedPiece2 = pieceDao.save(piece2);
            PieceEntity savedPiece3 = pieceDao.save(piece3);

            // when
            List<PieceEntity> pieces = pieceDao.findAll();

            // then
            assertThat(pieces)
                .containsExactlyInAnyOrder(savedPiece1, savedPiece2, savedPiece3);
        }


        @Test
        @DisplayName("모든 기물을 삭제할 수 있다.")
        void deleteAll() {
            // given
            pieceDao.save(new PieceEntity("GENERAL", "GREEN", 4, 1));

            // when
            pieceDao.deleteAll();

            // then
            assertThat(pieceDao.findAll()).isEmpty();
        }
    }
}
