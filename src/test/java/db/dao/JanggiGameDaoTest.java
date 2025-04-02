package db.dao;

import static org.assertj.core.api.Assertions.assertThat;

import db.dao.JanggiGameDao.GameDto;
import fixture.TestDBConnectionFixture;
import janggiGame.piece.Dynasty;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameDaoTest {
    private final JanggiGameDao janggiGameDao = new JanggiGameDao(TestDBConnectionFixture.getInstance());

    JanggiGameDaoTest() throws SQLException {
    }

    @BeforeEach
    void clearDB() throws SQLException {
        try (Connection connection = TestDBConnectionFixture.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM janggi_game")) {
            preparedStatement.executeUpdate();
        }
    }

    @DisplayName("게임을 저장한다")
    @Test
    void save() {
        // given
        Dynasty dynasty = Dynasty.HAN;
        String dynastyName = dynasty.name();

        // when
        Long gameId = janggiGameDao.save(dynastyName);

        // then
        assertThat(gameId)
                .isNotNull()
                .isGreaterThan(0);
    }

    @DisplayName("게임 상태를 업데이트한다")
    @Test
    void updateGame() {
        // given
        Long gameId = janggiGameDao.save("HAN");

        // when
        janggiGameDao.updateGame(gameId, "CHO", true);

        // then
        Optional<GameDto> result = janggiGameDao.findById(gameId);
        assertThat(result).isPresent();
        assertThat(result.get().currentDynasty()).isEqualTo("CHO");
        assertThat(result.get().wasLastPassed()).isTrue();
    }

    @DisplayName("게임을 끝난 상태로 처리한다")
    @Test
    void markAsFinished() {
        // given
        Long gameId = janggiGameDao.save("HAN");

        // when
        janggiGameDao.markAsFinished(gameId);

        // then
        List<GameDto> result = janggiGameDao.findNotFinishedGames();
        assertThat(result).noneMatch(game -> game.id().equals(gameId));
    }

    @DisplayName("끝난 상태가 아닌 게임을 조회한다")
    @Test
    void findNotFinishedGame() {
        // given
        Long gameId1 = janggiGameDao.save("HAN");
        Long gameId2 = janggiGameDao.save("CHO");
        Long gameId3 = janggiGameDao.save("CHO");
        Long gameId4 = janggiGameDao.save("HAN");

        janggiGameDao.markAsFinished(gameId2);

        // when
        List<GameDto> result = janggiGameDao.findNotFinishedGames();

        List<Long> gameIds = result.stream()
                .map(GameDto::id)
                .toList();

        // then
        assertThat(gameIds)
                .doesNotContain(gameId2)
                .hasSize(3);
    }

    @DisplayName("id 기반으로 게임을 조회한다")
    @Test
    void findById() {
        // given
        Long gameId = janggiGameDao.save("HAN");

        // when
        Optional<GameDto> result = janggiGameDao.findById(gameId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().id()).isEqualTo(gameId);
        assertThat(result.get().currentDynasty()).isEqualTo("HAN");
    }
}
