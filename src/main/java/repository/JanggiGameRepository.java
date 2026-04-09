package repository;

import domain.game.JanggiGame;
import dto.JanggiGameDto;
import java.util.List;
import java.util.Optional;

public interface JanggiGameRepository {

    Long save(JanggiGame janggiGame);

    List<JanggiGameDto> findAll();

    Optional<JanggiGame> findById(Long gamedId);

    void update(Long gameId, JanggiGame janggiGame);
}
