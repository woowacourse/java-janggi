package dao;

import static org.assertj.core.api.Assertions.assertThat;

import dao.fake.FakeConnector;
import dao.fake.InMemoryDatabase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class GameDAOTest {
    private final InMemoryDatabase database = new InMemoryDatabase();
    private final Connector connector = new FakeConnector(database);
    private final GameDAO gameDAO = new GameDAO(connector);

    @Test
    @DisplayName("Game 생성을 요청한다.")
    void test_create() {
        //given
        assertThat(database.getGames().isEmpty()).isTrue();

        //when
        gameDAO.create();

        //then
        assertThat(database.getGames().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Game을 비활성화 상태로 수정한다.")
    void test_deactivate() {
        //given
        final int gameId = 1;
        gameDAO.create();
        assertThat(database.getGames().get(gameId)).isTrue();

        //when
        gameDAO.deactivate(gameId);

        //then
        assertThat(database.getGames().get(gameId)).isFalse();
    }

    @Test
    @DisplayName("활성화된 Game의 Id들을 조회한다.")
    void test_findAllActivateGames() {
        //given
        gameDAO.create();
        gameDAO.create();
        gameDAO.deactivate(2);
        assertThat(database.getGames().get(1)).isTrue();
        assertThat(database.getGames().get(2)).isFalse();

        //when&then
        assertThat(gameDAO.findAllActivateGames().size()).isEqualTo(1);
    }
}
