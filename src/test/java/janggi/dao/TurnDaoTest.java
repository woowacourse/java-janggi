package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.game.Turn;
import janggi.domain.piece.Side;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnDaoTest {

    private static Connection connection;
    private TurnDao turnDao;

    @BeforeAll
    static void getConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:23306/test_janggi?useSSL=false&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true",
                "user", "password"
        );
    }

    @BeforeEach
    void initTable() {
        turnDao = new TurnDao();
        turnDao.createTableIfAbsent(connection);
        turnDao.clear(connection);
    }

    @AfterAll
    static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @DisplayName("데이터를 저장한다.")
    @Test
    void saveData() {
        // given

        // when
        turnDao.save(new Turn(Side.RED), connection);

        Turn turn = turnDao.find(connection);

        // then
        assertThat(turn.getSide()).isEqualTo(Side.RED);
    }

    @DisplayName("데이터를 업데이트한다.")
    @Test
    void updateData() {
        // given
        turnDao.save(new Turn(Side.RED), connection);

        // when
        turnDao.update(new Turn(Side.BLUE), connection);
        Turn turn = turnDao.find(connection);

        // then
        assertThat(turn.getSide()).isEqualTo(Side.BLUE);
    }

    @DisplayName("데이터를 모두 삭제한다.")
    @Test
    void deleteAllData() {
        // given
        turnDao.save(new Turn(Side.RED), connection);

        // when
        turnDao.clear(connection);
        Turn turn = turnDao.find(connection);

        // then
        assertThat(turn).isNull();
    }
}
