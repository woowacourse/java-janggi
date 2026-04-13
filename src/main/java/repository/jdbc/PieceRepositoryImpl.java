package repository.jdbc;

import domain.piece.PieceRepository;
import repository.entity.PieceEntity;

import java.sql.Connection;
import java.util.List;

public class PieceRepositoryImpl implements PieceRepository {
    @Override
    public List<PieceEntity> findByGameId(final Connection connection, final long gameId) {
        return List.of();
    }

    @Override
    public void move(final Connection connection, final long gameId, final int fromRow, final int fromColumn, final int toRow, final int toColumn) {

    }

    @Override
    public void delete(final Connection connection, final long gameId, final int row, final int column) {

    }

    @Override
    public void saveAll(final Connection connection, final long gameId, final List<PieceEntity> pieces) {

    }
}
