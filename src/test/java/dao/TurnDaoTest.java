package dao;

import domain.piece.Team;
import fake.FakeTurnDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Turn Dao 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TurnDaoTest {

    private TurnDao turnDao;

    @BeforeEach
    void setUp() {
        turnDao = new FakeTurnDao();
    }

    @Test
    void 턴을_조회할_수_있다() {
        assertThat(turnDao.load()).isEqualTo(Team.CHO);
    }

    @Test
    void 턴을_저장할_수_있다() {
        turnDao.save(null, Team.HAN);

        assertThat(turnDao.load()).isEqualTo(Team.HAN);
    }

    @Test
    void 턴_전체를_삭제할_수_있다() {
        turnDao.remove(null);

        assertThatThrownBy(turnDao::load)
                .isInstanceOf(NoSuchElementException.class);
    }
}
