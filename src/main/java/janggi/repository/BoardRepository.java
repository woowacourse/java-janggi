package janggi.repository;

import janggi.model.Janggi;
import janggi.model.position.absolute.Position;
import java.util.Optional;

public interface BoardRepository {
    Optional<Janggi> findInProgressGame();

    void updateBoardWith(Position from, Position to);

    void deleteGame();
}
