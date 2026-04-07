package janggi.repository.dao;

import janggi.repository.entity.GameEntity;
import janggi.repository.util.TransactionManager;
import janggi.support.TestDBConnectionProvider;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameDaoTest {

    TransactionManager transactionManager;
    GameDao gameDao;

    @BeforeEach
    void setup() {
        transactionManager = new TransactionManager(new TestDBConnectionProvider());
        gameDao = new GameDao(transactionManager);
        transactionManager.begin();
    }

    @AfterEach
    void tearDown() {
        transactionManager.rollback();
        transactionManager.close();
    }

    @Test
    @DisplayName("게임 정보를 저장하면 데이터베이스에 기록되고 자동 생성된 식별자를 반환한다.")
    void insert_PersistsEntityAndCanBeFoundById() {
        // given
        GameEntity gameEntity = new GameEntity("CHO", true);

        // when
        Long id = gameDao.insert(gameEntity);

        // then
        Assertions.assertThat(gameDao.findById(id)).get()
                .extracting("id")
                .isEqualTo(id);
    }

    @Test
    @DisplayName("진행중인 게임이 있다면 목록을 반환한다.")
    void findActiveGames_ReturnsOnlyActiveGames() {
        // given
        GameEntity gameEntityActive = new GameEntity("CHO", true);
        GameEntity gameEntityEnd = new GameEntity("CHO", false);
        gameDao.insert(gameEntityActive);
        gameDao.insert(gameEntityActive);
        gameDao.insert(gameEntityEnd);

        // when
        List<GameEntity> activeGameEntities = gameDao.findActiveGames();

        // then
        Assertions.assertThat(activeGameEntities).hasSize(2);
    }

    @Test
    @DisplayName("진행중인 게임의 상태를 종료 상태로 변경한다.")
    void updateIsActiveFalse_ChangesGameStateToInactive() {
        // given
        GameEntity gameEntity = new GameEntity("CHO", true);
        Long savedId = gameDao.insert(gameEntity);

        // when
        gameDao.updateIsActive(savedId, false);

        // then
        Assertions.assertThat(gameDao.findById(savedId)).get()
                .extracting("isActive")
                .isEqualTo(false);
    }
}
