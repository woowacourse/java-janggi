package repository.game;

public interface GameRepository {

    boolean existsGameRecord();

    GameRecord findGameRecord();

    void save(GameRecord gameRecord);

    void deleteAll();
}
