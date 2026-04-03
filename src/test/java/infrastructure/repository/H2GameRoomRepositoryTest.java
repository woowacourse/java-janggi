package infrastructure.repository;

import domain.room.GameRoom;
import infrastructure.DatabaseManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("H2GameRoomRepository 테스트")
class H2GameRoomRepositoryTest {

    private static DatabaseManager databaseManager;
    private static H2GameRoomRepository repository;

    @BeforeAll
    static void setUpSchema() {
        databaseManager = new DatabaseManager("jdbc:h2:mem:room_test;DB_CLOSE_DELAY=-1");
        databaseManager.initSchema();
        repository = new H2GameRoomRepository(databaseManager);
    }

    @BeforeEach
    void cleanUp() throws SQLException {
        try (Connection conn = databaseManager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM game_rooms");
        }
    }

    @Test
    @DisplayName("게임방을 생성하면 id가 부여된 GameRoom이 반환된다")
    void saveReturnsGameRoomWithId() {
        GameRoom room = repository.save("테스트 방");

        assertThat(room.id()).isPositive();
        assertThat(room.name()).isEqualTo("테스트 방");
    }

    @Test
    @DisplayName("생성한 게임방은 PLAYING 상태로 목록에 나온다")
    void savedRoomAppearsInFindAllPlaying() {
        repository.save("방1");
        repository.save("방2");

        List<GameRoom> rooms = repository.findAllPlaying();

        assertThat(rooms).hasSize(2);
        assertThat(rooms).extracting(GameRoom::name).containsExactly("방1", "방2");
    }

    @Test
    @DisplayName("finish 처리된 게임방은 목록에 나오지 않는다")
    void finishedRoomNotInFindAllPlaying() {
        GameRoom room = repository.save("종료된 방");
        repository.finish(room.id());

        List<GameRoom> rooms = repository.findAllPlaying();

        assertThat(rooms).isEmpty();
    }

    @Test
    @DisplayName("PLAYING 게임방만 목록에 나온다")
    void onlyPlayingRoomsInFindAllPlaying() {
        GameRoom playing = repository.save("진행 중");
        GameRoom finished = repository.save("완료됨");
        repository.finish(finished.id());

        List<GameRoom> rooms = repository.findAllPlaying();

        assertThat(rooms).hasSize(1);
        assertThat(rooms.get(0).name()).isEqualTo("진행 중");
    }
}
