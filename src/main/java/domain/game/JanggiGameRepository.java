package domain.game;

public interface JanggiGameRepository {

    JanggiGame findById(long gameId);

    long save(JanggiGame game);
}
