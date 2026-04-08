package janggi.service;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.infrastructure.mapper.BoardMapper;
import janggi.infrastructure.repository.BoardCellRepository;
import java.util.Map;

public class BoardService {

    private final BoardCellRepository boardCellRepository;

    public BoardService(final BoardCellRepository boardCellRepository) {
        this.boardCellRepository = boardCellRepository;
    }

    public Map<Position, Piece> loadOrCreateBoard(final long gameId,
        final Map<Position, Piece> positionPieceMap) {
        if (boardCellRepository.findAllByGameId(gameId).isEmpty()) {
            boardCellRepository.saveAll(BoardMapper.toEntity(gameId, positionPieceMap));
        }

        return BoardMapper.toDomain(boardCellRepository.findAllByGameId(gameId));
    }
}
