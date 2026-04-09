package janggi.service;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardGenerator;
import janggi.domain.board.BoardMediatorImpl;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.infrastructure.mapper.BoardMapper;
import janggi.infrastructure.repository.BoardCellRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BoardService {

    private final BoardCellRepository boardCellRepository;

    public BoardService(final BoardCellRepository boardCellRepository) {
        this.boardCellRepository = boardCellRepository;
    }

    public Map<Position, Piece> loadOrCreateBoard(final long gameId, final List<Team> teams) {
        if (boardCellRepository.findAllByGameId(gameId).isEmpty()) {
            final Map<Position, Piece> positionPieceMap =
                BoardGenerator.generate(teams.get(0), teams.get(1))
                    .getPositionPieceMap();
            boardCellRepository.saveAll(BoardMapper.toEntity(gameId, positionPieceMap));
        }

        return BoardMapper.toDomain(boardCellRepository.findAllByGameId(gameId));
    }

    public List<Position> getMovablePositions(final long gameId, final Position position) {
        final Board board = getBoard(gameId);
        final Piece pieceToMove = getBoard(gameId).findPieceByPosition(position);

        return pieceToMove.calculateMovablePositions(position, new BoardMediatorImpl(board));
    }

    public boolean isOver(final long gameId) {
        return getBoard(gameId).isGameOver();
    }

    public Optional<Piece> getPieceByPosition(final long gameId, final Position position) {
        return boardCellRepository.findByPositionAndGameId(position, gameId)
            .map(boardCellEntity -> PieceType.valueOf(boardCellEntity.pieceType())
                .toPiece(TeamType.valueOf(boardCellEntity.team())));
    }

    public Board getBoard(final long gameId) {
        return new Board(BoardMapper.toDomain(boardCellRepository.findAllByGameId(gameId)));
    }
}
