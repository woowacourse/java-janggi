package dao;

import domain.board.Point;
import domain.piece.Piece;
import domain.piece.Sa;
import domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Board Dao 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BoardDaoTest {

    @Test
    void 저장된_데이터가_있다면_true를_반환한다() {
        BoardDao boardDao = new FakeBoardDao();

        assertThat(boardDao.hasRecords()).isTrue();
    }

    @Test
    void 저장된_데이터가_없다면_false를_반환한다() {
        BoardDao boardDao = new FakeBoardDao();

        boardDao.removeAll();

        assertThat(boardDao.hasRecords()).isFalse();
    }

    @Test
    void 보드를_조회할_수_있다() {
        BoardDao boardDao = new FakeBoardDao();

        assertThat(boardDao.load()).hasSize(2);
    }

    @Test
    void 보드에_피스를_저장할_수_있다() {
        BoardDao boardDao = new FakeBoardDao();

        Point point = Point.of(1, 1);
        Piece sa = new Sa(Team.HAN);
        boardDao.save(point, sa);

        assertThat(boardDao.load().get(point)).isEqualTo(sa);
    }

    @Test
    void 보드_전체를_삭제할_수_있다() {
        BoardDao boardDao = new FakeBoardDao();

        boardDao.removeAll();

        assertThat(boardDao.load()).hasSize(0);
    }
}
