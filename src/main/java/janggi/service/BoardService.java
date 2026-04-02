package janggi.service;

import janggi.entity.BoardEntity;
import janggi.repository.BoardRepository;

public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(final BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public long createBoard(final String name) {
        final BoardEntity boardEntity = BoardEntity.from(name);

        return boardRepository.save(boardEntity);
    }
}
