package janggi.domain.board.dao;

import janggi.database.utils.DatabaseUtils;
import janggi.database.DBConnectorTest;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TeamDAOImplTest {
    private static TeamDAO teamDAO;
    private static DatabaseUtils databaseUtils;

    @BeforeAll
    static void setUp() {
        databaseUtils = new DatabaseUtils(new DBConnectorTest());
        teamDAO = new TeamDAOImpl(databaseUtils);
    }

    private void createTeamTable() {
        databaseUtils.executeQuery(createTeamTableQuery(), stmt -> {
           try {
               stmt.executeUpdate();
               return null;
           } catch (SQLException e) {
               throw new RuntimeException("[ERROR] 테스트 team 테이블 만들다가 에러 발생", e);
           }
        });
    }

    private String createTeamTableQuery() {
        return "CREATE TABLE IF NOT EXISTS team (id INT AUTO_INCREMENT PRIMARY KEY, name CHAR(1) NOT NULL)";
    }

    private String selectTeamTable(final int teamId) {
        return databaseUtils.executeQuery(selectTeamTableQuery(), stmt -> {
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                }
                return "";
            } catch (SQLException e) {
                throw new RuntimeException("[ERROR] TEST, 팀 테이블 SELECT 에러", e);
            }
        }, teamId);
    }


    private String selectTeamTableQuery() {
        return "SELECT name FROM team WHERE id = ?";
    }

    @DisplayName("팀 테이블에 레코드 추가된 것 확인")
    @Test
    void test1() {
        //given
        int choId = 1;
        int hanId = 2;
        createTeamTable();

        //when
        teamDAO.insertTeam();

        //then
        String selectedChoName = selectTeamTable(choId);
        String selectedHanName = selectTeamTable(hanId);
        Assertions.assertThat(selectedChoName).isEqualTo("초");
        Assertions.assertThat(selectedHanName).isEqualTo("한");
        teamDAO.dropTeamTable();
    }

    @DisplayName("팀 테이블 삭제 확인")
    @Test
    void test2() {
        //given
        createTeamTable();

        //when & then
        Assertions.assertThatCode(teamDAO::dropTeamTable).doesNotThrowAnyException();
    }

}