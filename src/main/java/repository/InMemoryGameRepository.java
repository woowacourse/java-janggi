package repository;

import java.util.Optional;

public class InMemoryGameRepository implements GameRepository{
    private SavedGame savedGame;

    @Override
    public void save(SavedGame savedGame) {
        this.savedGame = savedGame;
    }

    @Override
    public Optional<SavedGame> find() {
        return Optional.ofNullable(savedGame);
    }

    @Override
    public void clear() {
        savedGame = null;
    }
}
