package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.dto.MoveDto;
import janggi.fixture.TestJanggiJdbcDao;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiJdbcDaoTest {

    private TestJanggiJdbcDao janggiJdbcDao;

    @BeforeEach
    void setUp() {
        janggiJdbcDao = new TestJanggiJdbcDao();
    }

    @AfterEach
    void endUp() throws SQLException {
        janggiJdbcDao.rollBack();
    }

    @DisplayName("초기 게임을 저장한다.")
    @Test
    void testSaveInitialGame() {
        // given
        // when
        // then
        assertThatCode(() -> janggiJdbcDao.saveInitialGame(1))
                .doesNotThrowAnyException();
    }

    @DisplayName("끝나지 않은 게임이 있는지 찾는다.")
    @Test
    void testExistNotFinishedGame() {
        // given
        // when
        // then
        assertThat(janggiJdbcDao.existNotFinishedGame()).isTrue();
    }

    @DisplayName("끝나지 않은 게임 중 가장 최근 게임을 찾는다.")
    @Test
    void testFindRecentNotFinishedGame() {
        // given
        // when
        // then
        assertThat(janggiJdbcDao.findRecentNotFinishedGameId()).isEqualTo(2);
    }

    @DisplayName("끝나지 않은 게임들을 찾는다.")
    @Test
    void testFindNotFinishedGames() {
        // given
        // when
        // then
        assertThat(janggiJdbcDao.findNotFinishedGameIds()).isEqualTo(List.of(1, 2));
    }

    @DisplayName("게임 id로 조회한 게임의 차림상 번호를 찾는다.")
    @Test
    void testFindGameSetup() {
        // given
        // when
        // then
        assertThat(janggiJdbcDao.findGameSetup(2)).isEqualTo(1);
    }

    @DisplayName("게임 id로 조회한 게임의 움직인 기록들을 찾는다.")
    @Test
    void testSelectAllHistory() {
        // given
        // when
        // then
        assertThat(janggiJdbcDao.selectAllHistory(1)).isEqualTo(
                List.of(
                        new MoveDto(new Position(Row.NINE, Column.TWO), new Position(Row.SEVEN, Column.THREE)),
                        new MoveDto(new Position(Row.THREE, Column.FOUR), new Position(Row.THREE, Column.THREE)),
                        new MoveDto(new Position(Row.SEVEN, Column.ONE), new Position(Row.SEVEN, Column.FOUR))
                )
        );
    }

    @DisplayName("움직인 기록을 저장한다.")
    @Test
    void testSaveHistory() {
        // given
        // when
        // then
        final MoveDto moveDto = new MoveDto(new Position(Row.ONE, Column.FOUR), new Position(Row.ZERO, Column.FOUR));
        assertThatCode(() -> janggiJdbcDao.saveHistory(moveDto, 2)).doesNotThrowAnyException();
    }

    @DisplayName("게임이 끝난 여부를 저장한다.")
    @Test
    void testSetGameFinished() {
        // given
        // when
        // then
        assertAll(
                () -> assertThatCode(() -> janggiJdbcDao.setGameFinished(1)).doesNotThrowAnyException(),
                () -> assertThat(janggiJdbcDao.findNotFinishedGameIds()).hasSize(1)
        );
    }
}
