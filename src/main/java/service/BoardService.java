package service;

import domain.board.Board;
import domain.board.BoardStateFactory;
import domain.board.Placement;
import domain.board.Setup;
import domain.piece.Piece;
import domain.piece.Side;
import domain.players.Player;
import domain.players.Players;
import domain.position.Position;
import dto.BoardResponseDto;
import repository.BoardRepository;
import repository.PlayerRepository;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class BoardService {
    private final BoardRepository boardRepository;
    private final PlayerRepository playerRepository;

    public BoardService(BoardRepository boardRepository, PlayerRepository playerRepository) {
        this.boardRepository = boardRepository;
        this.playerRepository = playerRepository;
    }
    public Board initialState(int hanPlacementCode, int choPlacementCode) {
        Setup hanSetup = Placement.from(hanPlacementCode).getSetup();
        Setup choSetup = Placement.from(choPlacementCode).getSetup();
        BoardStateFactory boardStateFactory = new BoardStateFactory(hanSetup, choSetup);
        Map<Position, Piece> state = boardStateFactory.create();
        return Board.of(state);
    }

    public BoardResponseDto findBoardState(Long gameId) {
        Board board = boardRepository.findByGameId(gameId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게임이 존재하지 않습니다."));
        return board.findState();
    }

    public void save(Connection connection, Long gameId, Board board, List<Player> players) {
        boardRepository.save(connection, gameId, board);
        playerRepository.saveAll(connection, gameId, players);
    }

    public void update(Connection connection, Long gameId, Board board, List<Player> players) {
        boardRepository.save(connection, gameId, board);
        playerRepository.update(connection, gameId, players);
    }

    public Board findBoardBy(Long gameId) {
        return boardRepository.findByGameId(gameId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게임의 보드가 존재하지 않습니다."));
    }

    public Players findPlayersBy(Long gameId) {
        Map<Side, Player> players = playerRepository.findPlayersByGameId(gameId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게임의 플레이어가 존재하지 않습니다."));
        return Players.of(players);
    }
}
