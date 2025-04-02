package janggi.dao;

import janggi.domain.piece.PieceType;
import janggi.dto.PieceTypeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class PieceTypeDaoTest {

    private PieceTypeDao pieceTypeDao;

    @BeforeEach
    void setUp() {
        pieceTypeDao = new PieceTypeDao(new TestConnectionManager());
    }

    @Nested
    class 쿼리에_따라_각_CRUD가_정상적으로_동작하는지_테스트한다 {

        @Test
        void 초기_장기_기물_종류를_추가한다() {
            assertThatNoException()
                    .isThrownBy(() -> pieceTypeDao.insertInitialPieceType());
        }

        @Disabled
        @Test
        void ID로_기물_타입을_조회한다() {
            final int id = 12;
            final var pieceType = pieceTypeDao.findPieceTypeById(id);

            assertThat(pieceType)
                    .isEqualTo(new PieceTypeDto(id, PieceType.SANG.getTitle()));
        }

        @Test
        void 기물_종류를_모두_삭제한다() {
            assertThatNoException()
                    .isThrownBy(pieceTypeDao::deleteAllPieceTypeIfExists);
        }
    }
}
