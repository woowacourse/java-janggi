package domain.fake;

import dao.JanggiDao;
import game.Janggi;
import game.Turn;
import java.util.Optional;

public class FakeJanggiDao implements JanggiDao {

    @Override
    public void save(final Janggi janggi) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Optional<Turn> findTurn() {
        return Optional.empty();
    }
}
