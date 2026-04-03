package janggi.service;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.entity.BoardEntity;
import janggi.mapper.BoardMapper;
import janggi.repository.BoardCellRepository;
import janggi.repository.BoardRepository;
import java.util.Map;

public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardCellRepository boardCellRepository;

    public BoardService(final BoardRepository boardRepository,
        final BoardCellRepository boardCellRepository) {
        this.boardRepository = boardRepository;
        this.boardCellRepository = boardCellRepository;
    }

    public boolean hasBoard(final long boardId) {
        return boardRepository.existsById(boardId);
    }

    public long createBoard(final long gameStateId, final String boardName, final Map<Position, Piece> positionPieceMap) {
        final long boardId = boardRepository.save(BoardEntity.from(gameStateId, boardName));
        boardCellRepository.saveAll(BoardMapper.toEntity(boardId, positionPieceMap));

        return boardId;
    }

    public Map<Position, Piece> loadBoard(final long boardId) {
        if (!boardRepository.existsById(boardId)) {
            throw new IllegalArgumentException("저장된 보드판이 없습니다.");
        }

        return BoardMapper.toDomain(boardCellRepository.findAllByBoardId(boardId));
    }


    public long createBoard(final long gameStateId, final String name) {
        final BoardEntity boardEntity = BoardEntity.from(gameStateId, name);

        return boardRepository.save(boardEntity);
    }

    public boolean removeBoard(final long id) {
        return boardRepository.deleteById(id);
    }
}
