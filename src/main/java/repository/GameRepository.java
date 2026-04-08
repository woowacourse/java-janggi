package repository;

import java.util.Optional;

public interface GameRepository {
    void save(SavedGame savedGame);

    Optional<SavedGame> find();

    void clear();
}
