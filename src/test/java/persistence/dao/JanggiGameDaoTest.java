package persistence.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import domain.game.Turn;
import domain.piece.Team;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.JanggiGameEntity;

class JanggiGameDaoTest {

    private Connection connection;
    private JanggiGameDao janggiGameDao = new JanggiGameDao();

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        createTable();
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    @DisplayName("새로운 게임을 생성할 수 있다")
    void testCreateGame() throws SQLException {
        // given
        JanggiGameEntity entity = new JanggiGameEntity(new Turn(Team.CHO));

        // when
        Long gameId = janggiGameDao.create(connection, entity);

        // then
        Optional<JanggiGameEntity> janggiGameEntity = janggiGameDao.findById(connection, gameId);
        assertThat(janggiGameEntity.isPresent()).isTrue();
    }

    @Test
    @DisplayName("새로운 게임을 생성하면 ID가 반환된다")
    void testCreateGameReturnId() throws SQLException {
        // given
        JanggiGameEntity entity = new JanggiGameEntity(new Turn(Team.CHO));

        // when
        Long gameId = janggiGameDao.create(connection, entity);

        // then
        assertThat(gameId).isNotNull();
    }

    @Test
    @DisplayName("ID로 저장된 게임을 조회할 수 있다")
    void testFindById() throws SQLException {
        // given
        JanggiGameEntity entity = new JanggiGameEntity(new Turn(Team.CHO));
        Long gameId = janggiGameDao.create(connection, entity);

        // when
        Optional<JanggiGameEntity> foundGame = janggiGameDao.findById(connection, gameId);

        // then
        assertSoftly(softly -> {
            softly.assertThat(foundGame).isPresent();
            softly.assertThat(foundGame.get().turn().getTeam()).isEqualTo(Team.CHO);
        });
    }

    @Test
    @DisplayName("게임의 턴을 업데이트할 수 있다")
    void testUpdateGame() throws SQLException {
        // given
        JanggiGameEntity entity = new JanggiGameEntity(new Turn(Team.CHO));
        Long gameId = janggiGameDao.create(connection, entity);

        // when
        JanggiGameEntity updatedEntity = new JanggiGameEntity(gameId, new Turn(Team.HAN));
        janggiGameDao.update(connection, gameId, updatedEntity);

        // then
        Optional<JanggiGameEntity> updatedGame = janggiGameDao.findById(connection, gameId);
        assertSoftly(softly -> {
            softly.assertThat(updatedGame).isPresent();
            softly.assertThat(updatedGame.get().turn().getTeam()).isEqualTo(Team.HAN);
        });
    }

    private void createTable() throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.execute("""
                DROP TABLE IF EXISTS janggi_game;
                CREATE TABLE janggi_game (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    turn VARCHAR(255) NOT NULL
                );
            """);
        }
    }
}
