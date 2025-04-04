package janggi.domain.board.dao;

import janggi.database.utils.DatabaseUtils;
import janggi.database.DBConnectorTest;
import janggi.domain.setting.CampType;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class TurnDAOImplTest {
    private static TurnDAO turnDAO;
    private static DatabaseUtils databaseUtils;

    @BeforeAll
    static void setUp() {
        databaseUtils = new DatabaseUtils(new DBConnectorTest());
        turnDAO = new TurnDAOImpl(databaseUtils);
    }
    private void createTurnTable() {
        databaseUtils.executeQuery(createTurnTableQuery(), stmt -> {
           try {
               stmt.executeUpdate();

               return null;
           } catch (SQLException e) {
               throw new RuntimeException("[ERROR] 테스트 turn 테이블 만들다가 에러 발생", e);
           }
        });
    }

    private String createTurnTableQuery() {
        return "CREATE TABLE IF NOT EXISTS turn ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "current_turn CHAR(1) NOT NULL)";
    }

    @DisplayName("턴 테이블에 insert 확인")
    @Test
    void test1() {
        //given
        createTurnTable();

        //when
        turnDAO.insertQuery(CampType.CHO);

        //then
        CampType selectedCampType = turnDAO.selectQuery();
        Assertions.assertThat(selectedCampType).isEqualTo(CampType.CHO);
        turnDAO.dropTurnTable();
    }

    @DisplayName("턴 테이블에 업데이트 쿼리 확인, 초 -> 한")
    @Test
    void test2() {
        //given
        createTurnTable();
        turnDAO.insertQuery(CampType.CHO);

        //when
        turnDAO.updateQuery(CampType.HAN);

        //then
        CampType selectedCampType = turnDAO.selectQuery();
        Assertions.assertThat(selectedCampType).isEqualTo(CampType.HAN);
        turnDAO.dropTurnTable();
    }

    @DisplayName("턴 테이블에 업데이트 쿼리 확인, 한 -> 초")
    @Test
    void test3() {
        //given
        createTurnTable();
        turnDAO.insertQuery(CampType.HAN);

        //when
        turnDAO.updateQuery(CampType.CHO);

        //then
        CampType selectedCampType = turnDAO.selectQuery();
        Assertions.assertThat(selectedCampType).isEqualTo(CampType.CHO);
        turnDAO.dropTurnTable();
    }

    @DisplayName("턴 테이블에 값 SELECT 확인")
    @Test
    void test4() {
        //given
        createTurnTable();
        turnDAO.insertQuery(CampType.CHO);

        //when
        CampType selectedCampType = turnDAO.selectQuery();

        //then
        Assertions.assertThat(selectedCampType).isEqualTo(CampType.CHO);
        turnDAO.dropTurnTable();
    }

    @DisplayName("턴 테이블 삭제 확인")
    @Test
    void test5() {
        //given
        createTurnTable();

        //when & then
        Assertions.assertThatCode(turnDAO::dropTurnTable).doesNotThrowAnyException();
    }





}