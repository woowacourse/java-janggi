package janggi.domain.repository;

import janggi.domain.Game;
import janggi.dto.GameDto;
import java.util.List;
import java.util.Optional;

public interface GameRepository {

    Long save(Game game);

    void update(Long id, Game game);

    Optional<Game> findById(Long gameId);

    List<GameDto> findAllGames();
}
