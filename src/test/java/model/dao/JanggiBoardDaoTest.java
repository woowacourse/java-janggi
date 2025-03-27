package model.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import model.janggiboard.JanggiBoard;
import model.janggiboard.JanggiBoardSetUp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiBoardDaoTest {

    private static final int TEST_GAME_ID = 999;
    private final JanggiBoardDao janggiboardDao = new JanggiBoardDao();

    @Test
    void connection() throws SQLException {
        try (final var connection = janggiboardDao.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }

    @Test
    void updateJanggiGameTest() throws SQLException {
        JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardSetUp.DEFAULT_SETUP);

        janggiboardDao.updateJanggiGame(TEST_GAME_ID, janggiBoard.getAlivePieces());
    }

    @Test
    void deleteJanggiGameTest() throws SQLException {
        janggiboardDao.deleteJanggiGame(TEST_GAME_ID);
    }

    @Test
    @DisplayName("게임 정보 존재 여부 테스트")
    void existJanggiGameTest() throws SQLException {
        assertThat(janggiboardDao.existJanggiGame(TEST_GAME_ID)).isTrue();
    }

}