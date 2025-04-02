package janggi.dao.fake;

import janggi.domain.piece.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FakePieceTypeDaoTest {

    private FakePieceTypeDao fakePieceTypeDao;

    @BeforeEach
    void setUp() {
        fakePieceTypeDao = new FakePieceTypeDao();
    }

    @Nested
    class FAKE_객체를_만들어_테스트한다 {

        @Test
        void 초기_장기_기물_종류를_추가한다() {
            // When
            fakePieceTypeDao.insertInitialPieceType();

            // Then
            assertThat(fakePieceTypeDao.getPieceTypes().size())
                    .isEqualTo(PieceType.values().length);
        }

        @Test
        void ID로_기물_타입을_조회한다() {
            // Given
            final int id = 1;
            fakePieceTypeDao.insertInitialPieceType();

            // When & Then
            assertThat(fakePieceTypeDao.findPieceTypeById(id))
                    .isEqualTo(fakePieceTypeDao.getPieceTypes().getFirst());
        }

        @Test
        void 기물_종류를_모두_삭제한다() {
            // When
            fakePieceTypeDao.deleteAllPieceTypeIfExists();

            // Then
            assertThat(fakePieceTypeDao.getPieceTypes().isEmpty())
                    .isTrue();
        }
    }
}
