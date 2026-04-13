package repository.jdbc;

import domain.game.JanggiGame;
import domain.game.JanggiGameRepository;

public class JanggiGameRepositoryImpl implements JanggiGameRepository {
    @Override
    public JanggiGame findById(final long gameId) {
        return null;
    }

    @Override
    public long save(final JanggiGame game) {
        return 0;
    }
}
