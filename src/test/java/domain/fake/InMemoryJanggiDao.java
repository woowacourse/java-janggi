package domain.fake;

import dao.JanggiDao;
import domain.type.ChessTeam;
import game.Janggi;
import game.Turn;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryJanggiDao implements JanggiDao {

    private final Map<Long, String> dataSource = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0L);

    @Override
    public void save(final Janggi janggi) {
        dataSource.put(idGenerator.getAndIncrement(), janggi.getCurrentTeam().name());
    }

    @Override
    public void clear() {
        dataSource.clear();
    }

    @Override
    public Optional<Turn> findTurn() {
        final String data = dataSource.get(idGenerator.get() - 1L);
        if (Objects.equals(data, null)) {
            return Optional.empty();
        }
        final ChessTeam chessTeam = ChessTeam.valueOf(data);
        return Optional.of(new Turn(chessTeam));
    }
}
