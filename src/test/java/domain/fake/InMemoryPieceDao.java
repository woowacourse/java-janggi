package domain.fake;

import dao.PieceDao;
import domain.chesspiece.ChessPiece;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryPieceDao implements PieceDao {

    private final Map<Long, ChessPiece> dataSource = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0L);

    @Override
    public void saveAll(final List<ChessPiece> chessPieces) {
        for (ChessPiece chessPiece : chessPieces) {
            dataSource.put(idGenerator.getAndIncrement(), chessPiece);
        }
    }

    @Override
    public void clear() {
        dataSource.clear();
    }

    @Override
    public List<ChessPiece> findAll() {
        return dataSource.values().stream().toList();
    }
}
