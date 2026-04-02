package janggi.service;

import janggi.entity.BoardEntity;
import janggi.repository.BoardRepository;

public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(final BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public long createBoard(final long gameStateId, final String name) {
        final BoardEntity boardEntity = BoardEntity.from(gameStateId, name);

        return boardRepository.save(boardEntity);
    }

    public boolean removeBoard(final long id) {
        return boardRepository.deleteById(id);
    }
}
