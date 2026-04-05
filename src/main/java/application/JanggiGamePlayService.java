package application;

import static domain.player.Team.CHO;

import dao.BoardRepository;
import dao.GameRoom;
import domain.game.GameStatus;
import domain.manager.GameManager;
import domain.player.Team;
import domain.position.Position;

public class JanggiGamePlayService {
    private final GameRoom gameRoom;
    private final BoardRepository boardRepository;

    public JanggiGamePlayService(GameRoom gameRoom, BoardRepository boardRepository) {
        this.gameRoom = gameRoom;
        this.boardRepository = boardRepository;
    }

    public void playTurn(long gameId, GameManager gameManager, Position source, Position destination) {
        gameManager.validateSource(source);
        gameManager.move(source, destination);
        boardRepository.save(gameId, gameManager.getBoard());

        if (gameManager.isGameRunning()) {
            Team currentTeam = gameManager.getCurrentPlayer().getProfile().team();
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

