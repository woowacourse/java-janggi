package repository;

import dto.GameRoomDto;
import java.sql.Connection;
import java.util.List;

public interface GameRoomRepository {
    long save(String name, Connection conn);

    List<GameRoomDto> findAll(Connection conn);

    boolean existsById(long id, Connection conn);
}
