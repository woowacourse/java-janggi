package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.fixture.TestGameJdbcDao;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameJdbcDaoTest {

    private TestGameJdbcDao gameJdbcDao;

    @BeforeEach
    void setUp() {
        gameJdbcDao = new TestGameJdbcDao();
    }

    @AfterEach
    void endUp() throws SQLException {
        gameJdbcDao.rollBack();
    }

    @DisplayName("초기 게임을 저장한다.")
    @Test
    void testSaveInitialGame() {
        // given
        // when
        // then
        assertThatCode(() -> gameJdbcDao.saveInitialGame(1))
                .doesNotThrowAnyException();
    }

    @DisplayName("끝나지 않은 게임이 있는지 찾는다.")
    @Test
    void testExistNotFinishedGame() {
        // given
        // when
        // then
        assertThat(gameJdbcDao.existNotFinishedGame()).isTrue();
    }

    @DisplayName("끝나지 않은 게임 중 가장 최근 게임을 찾는다.")
    @Test
    void testFindRecentNotFinishedGame() {
        // given
        // when
        // then
        assertThat(gameJdbcDao.findRecentNotFinishedGameId()).isEqualTo(2);
    }

    @DisplayName("끝나지 않은 게임들을 찾는다.")
    @Test
    void testFindNotFinishedGames() {
        // given
        // when
        // then
        assertThat(gameJdbcDao.findNotFinishedGameIds()).isEqualTo(List.of(1, 2));
    }

    @DisplayName("게임 id로 조회한 게임의 차림상 번호를 찾는다.")
    @Test
    void testFindGameSetup() {
        // given
        // when
        // then
        assertThat(gameJdbcDao.findGameSetup(2)).isEqualTo(1);
    }


    @DisplayName("게임이 끝난 여부를 저장한다.")
    @Test
    void testSetGameFinished() {
        // given
        // when
        // then
        assertAll(
                () -> assertThatCode(() -> gameJdbcDao.setGameFinished(1)).doesNotThrowAnyException(),
                () -> assertThat(gameJdbcDao.findNotFinishedGameIds()).hasSize(1)
        );
    }
}
