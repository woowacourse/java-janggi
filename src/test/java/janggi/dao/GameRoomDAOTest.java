package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.dao.impl.GameRoomDAOImpl;
import janggi.domain.Team;
import janggi.manager.DatabaseManager;
import janggi.manager.DatabaseTestManager;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Disabled
class GameRoomDAOTest {

    private final DatabaseManager databaseManager = DatabaseTestManager.create();
    private GameRoomDAO gameRoomDAOImpl;
    private final String roomName = "room1";

    @BeforeEach
    void init() {
        databaseManager.createTableIfNotExist();
        gameRoomDAOImpl = new GameRoomDAOImpl(databaseManager);
    }

    @DisplayName("게임 룸을 생성한다.")
    @Test
    void test1() {
        assertThatCode(() -> gameRoomDAOImpl.create(roomName)).doesNotThrowAnyException();
    }

    @DisplayName("게임 룸이 존재하면 true를 반환한다.")
    @Test
    void test2() {
        // given
        gameRoomDAOImpl.create(roomName);

        // when
        boolean result = gameRoomDAOImpl.exist(roomName);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("게임 룸이 존재하지 않으면 false를 반환한다.")
    @Test
    void test3() {
        // given & when & then
        assertThat(gameRoomDAOImpl.exist(roomName)).isFalse();
    }

    @DisplayName("게임 룸이 존재하는데 생성을 하면 예외를 반환한다.")
    @Test
    void test4() {
        // given
        gameRoomDAOImpl.create(roomName);

        // when & then
        assertThatThrownBy(() -> gameRoomDAOImpl.create(roomName)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 중복된 방 제목입니다.");
    }

    @DisplayName("게임을 저장한다.")
    @Test
    void test5() {
        // given
        gameRoomDAOImpl.create(roomName);

        // when & then
        assertThatCode(() -> gameRoomDAOImpl.update(roomName, Team.HAN)).doesNotThrowAnyException();
    }

    @DisplayName("게임 룸을 삭제한다.")
    @Test
    void test6() {
        // given
        gameRoomDAOImpl.create(roomName);

        // when
        gameRoomDAOImpl.delete(roomName);

        // then
        assertThat(gameRoomDAOImpl.exist(roomName)).isFalse();
    }

    @DisplayName("해당 게임 방이 존재하지 않는데 저장을 하면 예외가 발생한다")
    @Test
    void test7() {
        // give & when & then
        assertThatThrownBy(() -> gameRoomDAOImpl.update(roomName, Team.HAN)).hasMessage("해당 방이 존재하지 않습니다!")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("방이 존재한다면 방 이름들을 반환한다.")
    @Test
    void test8() {
        // given
        List<String> nameList = List.of("방1", "방2", "방3", "방4");

        for (String name : nameList) {
            gameRoomDAOImpl.create(name);
        }

        // when
        List<String> result = gameRoomDAOImpl.findAllNames();

        // then
        assertThat(result).containsExactlyInAnyOrderElementsOf(nameList);

    }

    @AfterEach
    void clearAll() throws SQLException {
        DatabaseTestManager.resetDatabase();
    }
}
