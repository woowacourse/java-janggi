package janggi.service;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.entity.BoardCellEntity;
import janggi.repository.BoardCellRepository;

public class BoardCellService {

    private final BoardCellRepository boardCellRepository;

    public BoardCellService(final BoardCellRepository boardCellRepository) {
        this.boardCellRepository = boardCellRepository;
    }

    public long createBoardCell(final long boardId, final Position position, final Piece piece) {
        final BoardCellEntity boardCellEntity = BoardCellEntity.from(boardId, position, piece);

        return boardCellRepository.save(boardCellEntity);
    }

}
