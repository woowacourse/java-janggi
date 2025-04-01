package janggi.database.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.TestFixture;
import janggi.database.entity.TurnEntity;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnDaoTest {

    private final TurnDao turnDao = TestFixture.getTurnDao();

    @BeforeEach
    void setUp() {
        turnDao.delete();
    }

    @DisplayName("턴을 추가한다.")
    @Test
    void addTest() {

        // given

        // when
        turnDao.add("RED");

        // then
        assertThat(turnDao.find().get().getTeam()).isEqualTo("RED");
    }

    @DisplayName("턴을 수정한다.")
    @Test
    void updateTurnTest() {

        // given
        turnDao.add("RED");

        // when
        turnDao.update("BLUE");

        // then
        assertThat(turnDao.find().get().getTeam()).isEqualTo("BLUE");
    }

    @DisplayName("턴을 찾는다.")
    @Test
    void findTest() {

        // given
        turnDao.add("RED");

        // when
        final Optional<TurnEntity> findTurn = turnDao.find();

        // then
        assertThat(findTurn.get().getTeam()).isEqualTo("RED");
    }

    @DisplayName("턴을 삭제한다.")
    @Test
    void deleteTest() {

        // given
        turnDao.add("RED");

        // when
        turnDao.delete();

        // then
        assertThat(turnDao.find()).isEmpty();
    }
}
