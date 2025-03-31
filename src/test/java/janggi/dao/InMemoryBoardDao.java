package janggi.dao;

import janggi.entity.BoardEntity;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryBoardDao implements BoardDao {

    private long id = 0L;

    private final Map<Long, BoardEntity> idToBoard;

    public InMemoryBoardDao() {
        this.idToBoard = new HashMap<>();
    }

    @Override
    public void save(final BoardEntity boardEntity) {
        Optional<BoardEntity> boardEntityOptional = findByBoardId(boardEntity.boardId());
        if (boardEntityOptional.isPresent()) {
            BoardEntity exist = boardEntityOptional.get();
            idToBoard.put(exist.boardId(), boardEntity);
            return;
        }
        long targetId = ++id;
        BoardEntity saved = boardEntity.addBoardId(targetId);
        idToBoard.put(targetId, saved);
    }

    @Override
    public Optional<BoardEntity> findByJanggiIdAndRowAndColumn(final long janggiId,
                                                               final int row,
                                                               final int column) {
        return idToBoard.values().stream()
                .filter(boardEntity -> boardEntity.janggiId() == janggiId
                        && boardEntity.row() == row
                        && boardEntity.column() == column)
                .findAny();
    }

    @Override
    public Optional<BoardEntity> findByBoardId(final long boardId) {
        return Optional.ofNullable(idToBoard.get(boardId));
    }

    @Override
    public List<BoardEntity> findAllByJanggiIdAndIsAlive(final long janggiId, boolean isAlive) {
        return idToBoard.values().stream()
                .filter(boardEntity -> boardEntity.janggiId() == janggiId
                        && boardEntity.isAlive() == isAlive)
                .toList();
    }

    public Map<Long, BoardEntity> getIdToBoard() {
        return Collections.unmodifiableMap(idToBoard);
    }
}
