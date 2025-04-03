package janggi.dao;

import janggi.entity.BoardEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryBoardDao implements BoardDao {

    private static long id = 1L;

    private final Map<Long, BoardEntity> idToBoard;

    public InMemoryBoardDao() {
        this.idToBoard = new HashMap<>();
    }

    @Override
    public void save(final BoardEntity boardEntity) {
        Optional<Long> boardIdOptional = findByJanggiId(boardEntity.janggiId());
        if (boardIdOptional.isEmpty()) {
            long targetId = id++;
            idToBoard.put(targetId, boardEntity.addBoardId(targetId));
            return;
        }
        idToBoard.put(boardEntity.boardId(), boardEntity);
    }

    @Override
    public Optional<Long> findByJanggiId(final long janggiId) {
        Optional<BoardEntity> boardEntityOptional = idToBoard.values().stream()
                .filter(entity -> entity.janggiId() == janggiId)
                .findAny();
        return boardEntityOptional.map(BoardEntity::boardId);
    }
}
