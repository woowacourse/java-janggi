package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Team;
import janggi.manager.DatabaseManager;
import janggi.manager.DatabaseTestManger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameRoomDAOTest {

    static Connection connection;
    static DatabaseManager databaseManager = DatabaseTestManger.create();
    static GameRoomDAO gameRoomDAO;
    String roomName = "room1";

    @BeforeAll
    static void setUpDataBase() throws SQLException {
        connection = databaseManager.getConnection();
        gameRoomDAO = new GameRoomDAO(connection);
    }

    @DisplayName("게임 룸을 생성한다.")
    @Test
    void test1() {
        assertThatCode(() -> gameRoomDAO.create(roomName)).doesNotThrowAnyException();
    }

    @DisplayName("게임 룸이 존재하면 true를 반환한다.")
    @Test
    void test2() throws SQLException {
        // given
        gameRoomDAO.create(roomName);

        // when
        boolean result = gameRoomDAO.exist(roomName);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("게임 룸이 존재하지 않으면 false를 반환한다.")
    @Test
    void test3() throws SQLException {
        // given & when & then
        assertThat(gameRoomDAO.exist(roomName)).isFalse();
    }

    @DisplayName("게임 룸이 존재하는데 생성을 하면 예외를 반환한다.")
    @Test
    void test4() throws SQLException {
        // given
        gameRoomDAO.create(roomName);

        // when & then
        assertThatThrownBy(() -> gameRoomDAO.create(roomName)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 중복된 방 제목입니다.");
    }

    @DisplayName("게임을 저장한다.")
    @Test
    void test5() throws SQLException {
        // given
        gameRoomDAO.create(roomName);

        // when & then
        assertThatCode(() -> gameRoomDAO.save(roomName, Team.HAN)).doesNotThrowAnyException();
    }

    @DisplayName("해당 게임 방이 존재하지 않는데 저장을 하면 예외가 발생한다")
    @Test
    void test7() {
        // give & when & then
        assertThatThrownBy(() -> gameRoomDAO.save(roomName, Team.HAN)).hasMessage("해당 방이 존재하지 않습니다!")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("게임 룸을 삭제한다.")
    @Test
    void test6() throws SQLException {
        // given
        gameRoomDAO.create(roomName);

        // when
        gameRoomDAO.delete(roomName);

        // then
        assertThat(gameRoomDAO.exist(roomName)).isFalse();
    }

    @BeforeEach
    void clear() throws SQLException {
        connection = databaseManager.getConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM game_room");
        }
    }
}
