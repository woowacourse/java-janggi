package dao;

import static org.assertj.core.api.Assertions.assertThat;

import dao.fake.FakeConnector;
import dao.fake.InMemoryDatabase;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class GameDAOTest {
    private static final InMemoryDatabase DATABASE = new InMemoryDatabase();
    private static final Connector CONNECTOR = new FakeConnector(DATABASE);
    private static final GameDAO GAME_DAO = new GameDAO(CONNECTOR);

    @Test
    @DisplayName("Game 생성을 요청한다.")
    void test_create() throws SQLException {
        //given
        assertThat(DATABASE.getGames().isEmpty()).isTrue();

        //when
        GAME_DAO.create();

        //then
        assertThat(DATABASE.getGames().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Game을 비활성화 상태로 수정한다.")
    void test_deactivate() throws SQLException {
        //given
        final int gameId = 1;
        GAME_DAO.create();
        assertThat(DATABASE.getGames().get(gameId)).isTrue();

        //when
        GAME_DAO.deactivate(gameId);

        //then
        assertThat(DATABASE.getGames().get(gameId)).isFalse();
    }

    @Test
    @DisplayName("Game의 활성화 여부를 반환한다.")
    void test_existsActiveGameById() throws SQLException {
        //given
        GAME_DAO.create();
        GAME_DAO.create();
        GAME_DAO.deactivate(2);
        assertThat(DATABASE.getGames().get(1)).isTrue();
        assertThat(DATABASE.getGames().get(2)).isFalse();

        //when
        assertThat(GAME_DAO.existsActiveGameById(1)).isTrue();
        assertThat(GAME_DAO.existsActiveGameById(2)).isFalse();
    }
}
