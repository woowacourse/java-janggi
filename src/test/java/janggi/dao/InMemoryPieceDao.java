package janggi.dao;

import janggi.entity.PieceEntity;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryPieceDao implements PieceDao {

    private long id = 0L;

    private final Map<Long, PieceEntity> idToBoard;

    public InMemoryPieceDao() {
        this.idToBoard = new HashMap<>();
    }

    @Override
    public void save(final PieceEntity pieceEntity) {
        Optional<PieceEntity> boardEntityOptional = findByPieceId(pieceEntity.pieceId());
        if (boardEntityOptional.isPresent()) {
            PieceEntity exist = boardEntityOptional.get();
            idToBoard.put(exist.pieceId(), pieceEntity);
            return;
        }
        long targetId = ++id;
        PieceEntity saved = pieceEntity.addPieceId(targetId);
        idToBoard.put(targetId, saved);
    }

    @Override
    public Optional<PieceEntity> findByBoardIdAndRowAndColumn(final long boardId,
                                                              final int row,
                                                              final int column) {
        return idToBoard.values().stream()
                .filter(boardEntity -> boardEntity.boardId() == boardId
                        && boardEntity.row() == row
                        && boardEntity.column() == column)
                .findAny();
    }

    @Override
    public Optional<PieceEntity> findByPieceId(final long pieceId) {
        return Optional.ofNullable(idToBoard.get(pieceId));
    }

    @Override
    public List<PieceEntity> findAllByBoardIdAndIsAlive(final long boardId, boolean isAlive) {
        return idToBoard.values().stream()
                .filter(boardEntity -> boardEntity.boardId() == boardId
                        && boardEntity.isAlive() == isAlive)
                .toList();
    }

    public Map<Long, PieceEntity> getIdToBoard() {
        return Collections.unmodifiableMap(idToBoard);
    }
}
