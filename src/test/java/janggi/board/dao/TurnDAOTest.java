package janggi.board.dao;

import janggi.setting.CampType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TurnDAOTest {
    private FakeTurnDao fakeTurnDao;

    @BeforeEach
    void setUp() {
        fakeTurnDao = new FakeTurnDao();
    }

    @DisplayName("처음 게임시작 할 때, 초나라 부터 시작 insert 확인")
    @Test
    void test1() {
        //given
        CampType campType = CampType.CHO;

        //when
        fakeTurnDao.insertQuery(campType);

        //then
        Assertions.assertThat(fakeTurnDao.selectQuery()).isEqualTo(campType);
    }

    @DisplayName("레코드 update 확인")
    @Test
    void test2() {
        //given
        CampType campType = CampType.CHO;
        fakeTurnDao.insertQuery(campType);

        //when
        fakeTurnDao.updateQuery(CampType.HAN);

        //then
        Assertions.assertThat(fakeTurnDao.selectQuery()).isEqualTo(CampType.HAN);
    }

    @DisplayName("Select 확인")
    @Test
    void test3() {
        //given
        CampType campType = CampType.CHO;
        fakeTurnDao.insertQuery(campType);

        //when
        CampType selectedCampType = fakeTurnDao.selectQuery();

        //then
        Assertions.assertThat(selectedCampType).isEqualTo(campType);
    }

    @DisplayName("테이블 drop 확인")
    @Test
    void test4() {
        //when & then
        Assertions.assertThatCode(fakeTurnDao::dropTurnTable).doesNotThrowAnyException();
    }

}
