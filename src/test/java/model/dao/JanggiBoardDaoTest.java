package model.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import model.janggiboard.JanggiBoard;
import model.janggiboard.JanggiBoardSetUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiBoardDaoTest {

    private static final int TEST_GAME_ID = 999;
    private JanggiBoardDao janggiboardDao;

    @BeforeEach
    void setUp() {
        janggiboardDao = new JanggiBoardDao();
    }

    @AfterEach
    void rollbackJanggiGame() throws SQLException {
        janggiboardDao.rollback();
    }

    @Test
    void connection() throws SQLException {
        try (final var connection = janggiboardDao.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }

    @Test
    void updateJanggiGameTest() {
        JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardSetUp.DEFAULT_SETUP);

        janggiboardDao.updateJanggiGame(TEST_GAME_ID, janggiBoard.getAlivePieces());
    }

    @Test
    @DisplayName("게임 정보 존재 여부 테스트")
    void existJanggiGameTest() {
        assertThat(janggiboardDao.existJanggiGame(TEST_GAME_ID)).isFalse();

        janggiboardDao.updateJanggiGame(TEST_GAME_ID, new JanggiBoard(JanggiBoardSetUp.DEFAULT_SETUP).getAlivePieces());

        assertThat(janggiboardDao.existJanggiGame(TEST_GAME_ID)).isTrue();
    }

    @Test
    void deleteJanggiGameTest() {
        janggiboardDao.deleteJanggiGame(TEST_GAME_ID);
    }

}
