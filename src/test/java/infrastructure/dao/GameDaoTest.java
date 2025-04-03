package infrastructure.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.game.Game;
import infrastructure.H2Connector;
import infrastructure.entity.GameEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameDaoTest {

    private final static DbConnector dbConnector = new H2Connector();
    private final static Connection connection = dbConnector.getConnection();
    private static GameDao gameDao;

    @BeforeAll
    static void setUpDatabase() throws SQLException {
        gameDao = new GameDao(dbConnector);

        String ddl = """
                CREATE TABLE games (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(64) NOT NULL UNIQUE,
                    current_turn VARCHAR(64) NOT NULL
                );
                """;
        connection.prepareStatement(ddl).execute();
    }

    @BeforeEach
    void clearData() throws SQLException {
        connection.prepareStatement("DELETE FROM games").execute();
    }

    @AfterAll
    static void close() throws SQLException {
        connection.close();
    }

    @DisplayName("게임을 저장한다.")
    @Test
    void saveTest() {
        GameEntity gameEntity = gameDao.save(new GameEntity("방1", "CHO"));

        assertThat(gameEntity.getId()).isNotNull();
        assertThat(gameEntity.getName()).isEqualTo("방1");
        assertThat(gameEntity.getCurrentTurn()).isEqualTo("CHO");
    }

    @DisplayName("모든 게임을 조회한다.")
    @Test
    void findAllTest() {
        gameDao.save(new GameEntity("방1", "CHO"));
        gameDao.save(new GameEntity("방2", "HAN"));
        List<GameEntity> gameEntities = gameDao.findAll();

        assertThat(gameEntities).hasSize(2);
    }

    @DisplayName("게임을 수정한다. (턴을 변경한다.)")
    @Test
    void updateGameTest() {
        GameEntity gameEntity = gameDao.save(new GameEntity("방1", "CHO"));
        Game game = gameEntity.toDomain();
        game.next();
        gameEntity = GameEntity.from(game);

        gameDao.updateGame(gameEntity);

        List<GameEntity> gameEntities = gameDao.findAll();
        assertThat(gameEntities.getFirst().getCurrentTurn()).isEqualTo("HAN");
    }

    @DisplayName("게임을 삭제한다.")
    @Test
    void deleteGameTest() {
        GameEntity gameEntity = gameDao.save(new GameEntity("방1", "CHO"));
        gameDao.deleteGame(gameEntity);

        List<GameEntity> all = gameDao.findAll();
        assertTrue(all.isEmpty());
    }
}
