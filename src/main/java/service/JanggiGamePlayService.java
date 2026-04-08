package service;

import static domain.player.Team.CHO;

import common.GameStatus;
import dao.GamePersistence;
import domain.manager.JanggiGameManager;
import domain.player.PlayerProfile;
import domain.player.Team;
import domain.position.Position;

public class JanggiGamePlayService {
    private final GamePersistence gamePersistence;

    public JanggiGamePlayService(GamePersistence gamePersistence) {
        this.gamePersistence = gamePersistence;
    }

    public void playTurn(long gameId, JanggiGameManager janggiGameManager, Position source, Position destination) {
        janggiGameManager.validateSource(source);
        janggiGameManager.move(source, destination);
        if (janggiGameManager.isGameRunning()) {
            Team currentTeam = janggiGameManager.getCurrentPlayer().getProfile().team();
            gamePersistence.saveTurnProgress(gameId, janggiGameManager.getBoard(), currentTeam);
            return;
        }
        gamePersistence.saveBoard(gameId, janggiGameManager.getBoard());
    }

    public PlayerProfile finishGame(long gameId, JanggiGameManager janggiGameManager) {
        PlayerProfile winnerProfile = janggiGameManager.calculateFinalScore();
        Team winnerTeam = winnerProfile.team();
        gamePersistence.finishGame(gameId, janggiGameManager.getBoard(), winnerTeam, resolveFinishedStatus(winnerTeam));
        return winnerProfile;
    }

    private GameStatus resolveFinishedStatus(Team winnerTeam) {
        return winnerTeam == CHO ? GameStatus.CHO_WIN : GameStatus.HAN_WIN;
    }
}
