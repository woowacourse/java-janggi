package service;

import domain.board.Board;
import domain.board.BoardStateFactory;
import domain.board.Placement;
import domain.board.Setup;
import domain.piece.Piece;
import domain.players.Player;
import domain.position.Position;
import dto.BoardResponseDto;
import repository.BoardRepository;
import repository.PlayerRepository;

import java.util.List;
import java.util.Map;

public class BoardService {
    private final BoardRepository boardRepository;
    private final PlayerRepository playerRepository;

    public BoardService(BoardRepository boardRepository, PlayerRepository playerRepository) {
        this.boardRepository = boardRepository;
        this.playerRepository = playerRepository;
    }

    public BoardResponseDto findState(Long gameId) {
        return boardRepository.findByGameId(gameId);
    }

    public Board initialState(int hanPlacementCode, int choPlacementCode) {
        Setup hanSetup = Placement.from(hanPlacementCode).getSetup();
        Setup choSetup = Placement.from(choPlacementCode).getSetup();
        BoardStateFactory boardStateFactory = new BoardStateFactory(hanSetup, choSetup);
        Map<Position, Piece> state = boardStateFactory.create();
        return Board.of(state);
    }

    public void save(Long gameId, Board board, List<Player> players) {
        boardRepository.save(gameId, board);
        playerRepository.saveAll(gameId, players);
    }

    public void update(Long gameId, Board board, List<Player> players) {
        boardRepository.save(gameId, board);
        playerRepository.update(gameId, players);
    }
}
