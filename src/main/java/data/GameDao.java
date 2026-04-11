package data;

import java.sql.Connection;
import java.util.Optional;

public interface GameDao {
    Long insert(Connection conn, GameDto gameDto);

    void update(Connection conn, GameDto gameDto);

    Optional<GameDto> findById(Connection conn, Long id);

    void deleteById(Connection conn, Long id);
}
