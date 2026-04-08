package repository;

import java.util.Optional;

public interface GameRepository {

    long save(SavedGameDto savedGameDto);

    Optional<SavedGameDto> findLatestRunningGame();

    void update(SavedGameDto savedGameDto);
}
