package dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.connector.DBConnector;
import dao.connector.H2DBConnector;
import game.Turn;

class TurnDaoTest {

    private static Connection connection;
    private TurnDao turnDao;

    @BeforeAll
    static void beforeAll() throws SQLException {
        DBConnector dbConnector = new H2DBConnector();
        connection = dbConnector.getConnection();
        String sql = """
                CREATE TABLE IF NOT EXISTS turn
                (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    value INT NOT NULL
                );
                """;
        connection.prepareStatement(sql).execute();
        connection.setAutoCommit(false);
    }

    @BeforeEach
    void setUp() {
        turnDao = new TurnDao(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
    }

    @Test
    void 게임_턴을_저장한다() {
        Turn turn = new Turn(1);
        TurnEntity entity = TurnConverter.toEntity(turn);

        turnDao.save(entity);

        assertThat(turnDao.find().value()).isEqualTo(1);
    }

    @Test
    void 게임_턴을_업데이트한다() {
        Turn turn = new Turn(1);
        TurnEntity entity = TurnConverter.toEntity(turn);
        turnDao.save(entity);
        turn.increaseRound();

        turnDao.update(TurnConverter.toEntity(turn));

        assertThat(turnDao.find().value()).isEqualTo(2);
    }

    @Test
    void 게임_턴_정보가_존재하면_알려준다() {
        Turn turn = new Turn(1);
        TurnEntity entity = TurnConverter.toEntity(turn);
        turnDao.save(entity);

        assertThat(turnDao.exists()).isTrue();
    }

    @Test
    void 게임_턴_정보가_존재하지_않아도_알려준다() {
        Turn turn = new Turn(1);
        TurnEntity entity = TurnConverter.toEntity(turn);

        assertThat(turnDao.exists()).isFalse();
    }

    @Test
    void 모든_게임_턴_정보를_제거한다() {
        Turn turn = new Turn(1);
        TurnEntity entity = TurnConverter.toEntity(turn);
        turnDao.save(entity);

        turnDao.removeAll();

        assertThat(turnDao.exists()).isFalse();
    }

}
