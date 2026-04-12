package repository;

import domain.game.JanggiGame;
import dto.JanggiGameDto;
import java.util.List;
import java.util.Optional;

public interface JanggiGameRepository {

    Long save(final JanggiGame janggiGame);

    List<JanggiGameDto> findAll();

    Optional<JanggiGame> findById(final Long gamedId);

    void update(final Long gameId, final JanggiGame janggiGame);
}
