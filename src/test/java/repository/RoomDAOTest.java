package repository;

import domain.Room;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.mock.TestConnector;

class RoomDAOTest {
    private static final Connector CONNECTOR = new TestConnector();
    private static final RoomDAO PLAYER_REPOSITORY = new RoomDAO(CONNECTOR);

    @Test
    @DisplayName("room 생성을 요청한다.")
    void test_create() {
        final Room room = new Room(0);
        PLAYER_REPOSITORY.create(room);
    }
}
