package janggi.dao;

import janggi.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TurnDaoTest {

    private TurnDao turnDao;

    @BeforeEach
    void beforeEach() {
        H2DatabaseConnector connector = new H2DatabaseConnector();
        Connection connection = connector.getConnection();
        turnDao = new TurnDao(connector);
        initializeSchema(connection);
    }

    @DisplayName("Turn 저장 기능 확인")
    @Test
    void saveTurnTest() {
        turnDao.saveTurn(Team.CHO);

        assertThat(turnDao.findCurrentTurn()).isEqualTo(Team.CHO);
    }

    @DisplayName("저장 순서 불러오기 확인")
    @Test
    void findCurrentTurnTest() {
        turnDao.saveTurn(Team.CHO);

        assertThat(turnDao.findCurrentTurn()).isEqualTo(Team.CHO);
    }

    @DisplayName("순서 업데이트 확인")
    @Test
    void updateTurnTest() {
        turnDao.saveTurn(Team.CHO);
        turnDao.updateTurn(Team.CHO, Team.HAN);

        assertThat(turnDao.findCurrentTurn()).isEqualTo(Team.HAN);
    }

    @DisplayName("저장되어 있는 순서 정보 전체 삭제")
    @Test
    void deleteAllTest() {
        turnDao.saveTurn(Team.CHO);
        turnDao.deleteAll();
        assertThatThrownBy(() -> turnDao.findCurrentTurn()).isInstanceOf(RuntimeException.class);
    }

    private void initializeSchema(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                        CREATE TABLE turn (
                            turn ENUM('CHO', 'HAN') PRIMARY KEY
                        )
                    """);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize schema", e);
        }
    }
}
