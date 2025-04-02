package janggi.fixture;

import janggi.dao.PieceDao;
import janggi.dto.PieceDto;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakePieceDao implements PieceDao {

    private final Map<Integer, List<PieceDto>> pieces;

    public FakePieceDao(final Map<Integer, List<PieceDto>> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    @Override
    public List<PieceDto> findPiecesByGameId(final Connection connection, final int gameId) {
        return pieces.get(gameId);
    }

    @Override
    public void addPieces(final Connection connection, final int gameId, final List<PieceDto> pieceDtos) {
        pieces.put(gameId, pieceDtos);
    }

    @Override
    public void deletePiecesByGameId(final Connection connection, final int gameId) {
        pieces.remove(gameId);
    }
}
