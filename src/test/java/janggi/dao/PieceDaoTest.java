package janggi.dao;

import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.SangSetting;
import janggi.domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;

class PieceDaoTest {

    private PieceDao pieceDao;

    @BeforeEach
    void setUp() {
        pieceDao = new PieceDao(new TestConnectionManager());
    }

    @Nested
    class 쿼리에_따라_각_CRUD가_정상적으로_동작하는지_테스트한다 {

        @Test
        void 초기_기물을_장기판에_추가한다() {
            final BoardFactory boardFactory = new BoardFactory();
            final Board board = boardFactory.makeBoard(SangSetting.INNER_SANG, SangSetting.INNER_SANG);

            assertThatNoException()
                    .isThrownBy(() -> pieceDao.insertPieces(board.getPieces()));
        }

        @Test
        void 기물을_모두_삭제한다() {
            assertThatNoException()
                    .isThrownBy(pieceDao::deleteAllPieceIfExists);
        }

        @Test
        void 저장된_기물을_불러온다() {
            assertThatNoException()
                    .isThrownBy(pieceDao::findAllPiece);
        }

        @Test
        void 좌표에_기물이_존재한다면_삭제한다() {
            assertThatNoException()
                    .isThrownBy(() -> pieceDao.deletePieceByPositionIfExists(new Position(3, 7)));
        }

        @Test
        void 기물을_이동한다() {
            assertThatNoException()
                    .isThrownBy(() -> pieceDao.updatePiece(new Position(3, 7), new Position(1, 2)));
        }
    }
}
