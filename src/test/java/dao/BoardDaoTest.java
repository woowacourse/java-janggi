package dao;

import domain.board.Point;
import domain.piece.Piece;
import domain.piece.Sa;
import domain.piece.Team;
import fake.FakeBoardDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Board Dao 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BoardDaoTest {

    private static final int DEFAULT_BOARD_ID = 1;

    private BoardDao boardDao;

    @BeforeEach
    void setUp() {
        boardDao = new FakeBoardDao();
    }

    @Test
    void 저장된_데이터가_있다면_true를_반환한다() {
        assertThat(boardDao.hasRecords()).isTrue();
    }

    @Test
    void 저장된_데이터가_없다면_false를_반환한다() {
        boardDao.removeAll(null);

        assertThat(boardDao.hasRecords()).isFalse();
    }

    @Test
    void 보드번호로_보드를_조회할_수_있다() {
        assertThat(boardDao.load(DEFAULT_BOARD_ID)).hasSize(2);
    }

    @Test
    void 보드를_저장할_수_있다() {
        Point point = Point.of(1, 1);
        Piece sa = new Sa(Team.HAN);
        boardDao.save(null, point, sa, DEFAULT_BOARD_ID);

        assertThat(boardDao.load(DEFAULT_BOARD_ID).get(point)).isEqualTo(sa);
    }

    @Test
    void 보드번호로_보드를_삭제할_수_있다() {
        boardDao.remove(null, DEFAULT_BOARD_ID);

        assertThat(boardDao.load(DEFAULT_BOARD_ID)).hasSize(0);
    }
}
