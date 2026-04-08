package service;

import static domain.player.Team.CHO;

import dao.BoardRepository;
import dao.GameRoom;
import common.GameStatus;
import domain.manager.JanggiGameManager;
import domain.player.Team;
import domain.position.Position;

public class JanggiGamePlayService {
    private final GameRoom gameRoom;
    private final BoardRepository boardRepository;

    public JanggiGamePlayService(GameRoom gameRoom, BoardRepository boardRepository) {
        this.gameRoom = gameRoom;
        this.boardRepository = boardRepository;
    }

    public void playTurn(long gameId, JanggiGameManager janggiGameManager, Position source, Position destination) {
        janggiGameManager.validateSource(source);
        janggiGameManager.move(source, destination);
        boardRepository.save(gameId, janggiGameManager.getBoard());

        if (janggiGameManager.isGameRunning()) {
            Team currentTeam = janggiGameManager.getCurrentPlayer().getProfile().team();
            gameRoom.updateGameState(gameId, currentTeam, GameStatus.PROGRESS);
        }
    }

    public void finishGame(long gameId, Team winnerTeam) {
        gameRoom.updateGameState(gameId, winnerTeam, resolveFinishedStatus(winnerTeam));
    }

    private GameStatus resolveFinishedStatus(Team winnerTeam) {
        return winnerTeam == CHO ? GameStatus.CHO_WIN : GameStatus.HAN_WIN;
    }
}

