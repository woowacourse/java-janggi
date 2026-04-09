package janggi.service;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardGenerator;
import janggi.domain.piece.Piece;
import janggi.domain.team.Team;
import janggi.infrastructure.mapper.BoardMapper;
import janggi.infrastructure.repository.BoardCellRepository;
import java.util.List;
import java.util.Map;

public class BoardService {

    private final BoardCellRepository boardCellRepository;

    public BoardService(final BoardCellRepository boardCellRepository) {
        this.boardCellRepository = boardCellRepository;
    }

    public Map<Position, Piece> loadOrCreateBoard(final long gameId, final List<Team> teams) {
        if (boardCellRepository.findAllByGameId(gameId).isEmpty()) {
            final Map<Position, Piece> positionPieceMap = BoardGenerator.generate(teams.get(0), teams.get(1))
                .getPositionPieceMap();
            boardCellRepository.saveAll(BoardMapper.toEntity(gameId, positionPieceMap));
        }

        return BoardMapper.toDomain(boardCellRepository.findAllByGameId(gameId));
    }

    public boolean isOver(final long gameId) {
        final Board board = new Board(BoardMapper.toDomain(boardCellRepository.findAllByGameId(gameId)));

        return board.isGameOver();
    }
}
