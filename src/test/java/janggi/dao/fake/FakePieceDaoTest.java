package janggi.dao.fake;

import janggi.domain.piece.Byeong;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FakePieceDaoTest {

    private FakePieceDao fakePieceDao;

    @BeforeEach
    void setUp() {
        fakePieceDao = new FakePieceDao();
    }

    @Nested
    class FAKE_객체를_만들어_테스트한다 {


        @Test
        void 초기_기물을_장기판에_추가한다() {
            // Given
            final Map<Position, Piece> pieces = new HashMap<>();
            pieces.put(new Position(1, 1), new Byeong());

            // When
            fakePieceDao.insertPieces(pieces);

            // Then
            assertThat(fakePieceDao.findPieces().size())
                    .isEqualTo(pieces.size());
        }

        @Test
        void 기물을_모두_삭제한다() {
            // When
            fakePieceDao.deleteAllPieceIfExists();

            // Then
            assertThat(fakePieceDao.findPieces().isEmpty())
                    .isTrue();
        }
    }
}
