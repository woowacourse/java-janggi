package janggi.dao;

import janggi.domain.Position;
import janggi.entity.BoardEntity;
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
    public void save(final BoardEntity boardEntity, Position departure) {
        Optional<BoardEntity> boardEntityOptional = findByJanggiIdAndRowAndColumn(boardEntity.janggiId(),
                departure.getRow(), departure.getColumn());
        if (boardEntityOptional.isPresent()) {
            BoardEntity exist = boardEntityOptional.get();
            idToBoard.put(exist.boardId(), new BoardEntity(exist.boardId(),
                    boardEntity.janggiId(),
                    boardEntity.pieceType(),
                    boardEntity.team(),
                    boardEntity.row(),
                    boardEntity.column(),
                    boardEntity.isAlive()));
            return;
        }
        long targetId = ++id;
        BoardEntity saved = new BoardEntity(targetId,
                boardEntity.janggiId(),
                boardEntity.pieceType(),
                boardEntity.team(),
                boardEntity.row(),
                boardEntity.column(),
                boardEntity.isAlive());
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
    public List<BoardEntity> findAllByJanggiIdAndIsAlive(final long janggiId, boolean isAlive) {
        return idToBoard.values().stream()
                .filter(boardEntity -> boardEntity.janggiId() == janggiId)
                .toList();
    }

    public Map<Long, BoardEntity> getIdToBoard() {
        return idToBoard;
    }
}
