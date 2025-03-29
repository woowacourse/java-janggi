package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Team;
import janggi.manager.DatabaseManager;
import janggi.manager.DatabaseTestManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Disabled
class GameRoomDAOTest {

    static DatabaseManager databaseManager = DatabaseTestManager.create();
    static GameRoomDAO gameRoomDAO;
    String roomName = "room1";

    @BeforeAll
    static void setUpDataBase() {
        gameRoomDAO = new GameRoomDAO(databaseManager);
    }

    @AfterAll
    static void clearAll() throws SQLException {
        DatabaseTestManager.resetDatabase();
    }

    @BeforeEach
    void clear() throws SQLException {
        Connection connection = databaseManager.getConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM game_room");
        }
    }

    @DisplayName("게임 룸을 생성한다.")
    @Test
    void test1() {
        assertThatCode(() -> gameRoomDAO.create(roomName)).doesNotThrowAnyException();
    }

    @DisplayName("게임 룸이 존재하면 true를 반환한다.")
    @Test
    void test2() {
        // given
        gameRoomDAO.create(roomName);

        // when
        boolean result = gameRoomDAO.exist(roomName);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("게임 룸이 존재하지 않으면 false를 반환한다.")
    @Test
    void test3() {
        // given & when & then
        assertThat(gameRoomDAO.exist(roomName)).isFalse();
    }

    @DisplayName("게임 룸이 존재하는데 생성을 하면 예외를 반환한다.")
    @Test
    void test4() {
        // given
        gameRoomDAO.create(roomName);

        // when & then
        assertThatThrownBy(() -> gameRoomDAO.create(roomName)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 중복된 방 제목입니다.");
    }

    @DisplayName("게임을 저장한다.")
    @Test
    void test5() {
        // given
        gameRoomDAO.create(roomName);

        // when & then
        assertThatCode(() -> gameRoomDAO.save(roomName, Team.HAN)).doesNotThrowAnyException();
    }

    @DisplayName("게임 룸을 삭제한다.")
    @Test
    void test6() {
        // given
        gameRoomDAO.create(roomName);

        // when
        gameRoomDAO.delete(roomName);

        // then
        assertThat(gameRoomDAO.exist(roomName)).isFalse();
    }

    @DisplayName("해당 게임 방이 존재하지 않는데 저장을 하면 예외가 발생한다")
    @Test
    void test7() {
        // give & when & then
        assertThatThrownBy(() -> gameRoomDAO.save(roomName, Team.HAN)).hasMessage("해당 방이 존재하지 않습니다!")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("방이 존재한다면 방 이름들을 반환한다.")
    @Test
    void test8() {
        // given
        List<String> nameList = List.of("방1", "방2", "방3", "방4");

        for (String name : nameList) {
            gameRoomDAO.create(name);
        }

        // when
        List<String> result = gameRoomDAO.findAllNames();

        // then
        assertThat(result).containsExactlyInAnyOrderElementsOf(nameList);

    }

}
