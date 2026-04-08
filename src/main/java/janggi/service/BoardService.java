package janggi.service;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.mapper.BoardMapper;
import janggi.repository.BoardCellRepository;
import janggi.repository.GameRepository;
import java.util.Map;

public class BoardService {

    private final GameRepository gameRepository;
    private final BoardCellRepository boardCellRepository;

    public BoardService(final GameRepository gameRepository,
        final BoardCellRepository boardCellRepository) {
        this.gameRepository = gameRepository;
        this.boardCellRepository = boardCellRepository;
    }

    public void createBoard(final long gameStateId, final Map<Position, Piece> positionPieceMap) {
        boardCellRepository.saveAll(BoardMapper.toEntity(gameStateId, positionPieceMap));
    }

    public boolean hasBoard(final long gameId) {
        return !boardCellRepository.findAllByGameId(gameId).isEmpty();
    }

    public Map<Position, Piece> loadBoard(final long gameId) {
        if (gameRepository.findById(gameId).isEmpty()) {
            throw new IllegalArgumentException("저장된 게임이 없습니다.");
        }

        return BoardMapper.toDomain(boardCellRepository.findAllByGameId(gameId));
    }

    public void movePiece(final long gameId, final Position from, final Position to,
        final Piece target) {
        boardCellRepository.upsertByPositionAndGameId(to, gameId, target);
        boardCellRepository.deleteByPositionAndGameId(from, gameId);
    }
}
