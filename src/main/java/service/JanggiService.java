package service;

import domain.board.Board;
import domain.janggigame.JanggiGame;
import domain.players.Players;
import domain.position.Movement;
import dto.BoardResponseDto;
import repository.GameRepository;

public class JanggiService {

    private final GameRepository gameRepository;
    private final BoardService boardService;

    public JanggiService(GameRepository gameRepository, BoardService boardService) {
        this.gameRepository = gameRepository;
        this.boardService = boardService;
    }

    public Long initialBoardState(int hanPlacementCode, int choPlacementCode) {
        Board board = boardService.initialState(hanPlacementCode, choPlacementCode);
        Players players = new Players();
        JanggiGame janggiGame = new JanggiGame(board, players);
        Long gameId = gameRepository.save(janggiGame);
        boardService.save(gameId, janggiGame.getBoard(), janggiGame.getPlayers());
        return gameId;
    }

    public void playGame(Long gameId, Movement movement) {
        JanggiGame janggiGame = findGameById(gameId);
        janggiGame.playGame(movement);
        boardService.update(gameId, janggiGame.getBoard(), janggiGame.getPlayers());
        janggiGame.switchTurn();
        gameRepository.update(gameId, janggiGame);
    }

    public BoardResponseDto getBoardState(Long gameId) {
        return boardService.findState(gameId);
    }

    public boolean isFinished(Long gameId) {
        return gameRepository.isFinished(gameId);
    }

    private JanggiGame findGameById(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게임이 존재하지 않습니다."));
    }

    public String getWhoseTurn(Long gameId) {
        return gameRepository.findCurrentTurnById(gameId);
    }
}
