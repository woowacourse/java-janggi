package service;

import domain.board.Board;
import domain.janggigame.JanggiGame;
import domain.piece.Side;
import domain.players.Players;
import domain.position.Movement;
import dto.BoardResponseDto;
import global.template.TransactionTemplate;
import repository.GameRepository;

public class JanggiService {

    private final GameRepository gameRepository;
    private final BoardService boardService;

    public JanggiService(GameRepository gameRepository, BoardService boardService) {
        this.gameRepository = gameRepository;
        this.boardService = boardService;
    }

    public Long initialBoardPlacementBySide(int hanPlacementCode, int choPlacementCode) {
        return TransactionTemplate.execute(connection -> {
            Board board = boardService.initialState(hanPlacementCode, choPlacementCode);
            Players players = new Players();
            JanggiGame janggiGame = new JanggiGame(board, players);
            Long gameId = gameRepository.save(connection, janggiGame);
            boardService.save(connection, gameId, janggiGame.getBoard(), janggiGame.getPlayers());
            return gameId;
        });
    }

    public void playGame(Long gameId, Movement movement) {
        TransactionTemplate.execute(connection -> {
            JanggiGame janggiGame = findGameById(gameId);
            janggiGame.playGame(movement);
            boardService.update(connection, gameId, janggiGame.getBoard(), janggiGame.getPlayers());
            janggiGame.switchTurn();
            gameRepository.update(connection, gameId, janggiGame);
            return null;
        });
    }

    public BoardResponseDto getBoardState(Long gameId) {
        return boardService.findBoardState(gameId);
    }

    public boolean isFinished(Long gameId) {
        return gameRepository.isFinished(gameId);
    }

    private JanggiGame findGameById(Long gameId) {
        Board board = findBoardBy(gameId);
        Players players = findPlayersBy(gameId);
        Side currentTurn = getWhoseTurn(gameId);
        return JanggiGame.of(board, players, currentTurn);
    }

    private Players findPlayersBy(Long gameId) {
        return boardService.findPlayersBy(gameId);
    }

    private Board findBoardBy(Long gameId) {
        return boardService.findBoardBy(gameId);
    }

    public Side getWhoseTurn(Long gameId) {
        return gameRepository.findCurrentTurnById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("게임이 존재하지 않습니다."));
    }
}
