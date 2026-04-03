package repository;

import dto.GameRoomDto;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface GameRoomRepository {
    long save(String name, String side, Connection conn);

    List<GameRoomDto> findAll(Connection conn);

    Optional<GameRoomDto> findById(long id, Connection conn);
}
