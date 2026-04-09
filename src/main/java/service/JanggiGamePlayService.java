package service;

import static domain.player.Team.CHO;

import common.GameStatus;
import dao.GamePersistence;
import domain.manager.JanggiGameManager;
import domain.player.PlayerProfile;
import domain.player.Team;
import domain.position.Position;
import domain.piece.BasicPiece;

public class JanggiGamePlayService {
    private final GamePersistence gamePersistence;

    public JanggiGamePlayService(GamePersistence gamePersistence) {
        this.gamePersistence = gamePersistence;
    }

    public void playTurn(long gameId, JanggiGameManager janggiGameManager, Position source, Position destination) {
        janggiGameManager.validateSource(source);
        BasicPiece movingPiece = janggiGameManager.getBoard().findPiece(source);
        janggiGameManager.move(source, destination);
        if (janggiGameManager.isGameRunning()) {
            Team currentTeam = janggiGameManager.getCurrentPlayer().getProfile().team();
            gamePersistence.saveTurnProgress(gameId, source, destination, movingPiece, currentTeam);
            return;
        }
        gamePersistence.saveMove(gameId, source, destination, movingPiece);
    }

    public PlayerProfile finishGame(long gameId, JanggiGameManager janggiGameManager) {
        PlayerProfile winnerProfile = janggiGameManager.calculateFinalScore();
        Team winnerTeam = winnerProfile.team();
        gamePersistence.finishGame(gameId, winnerTeam, resolveFinishedStatus(winnerTeam));
        return winnerProfile;
    }

    private GameStatus resolveFinishedStatus(Team winnerTeam) {
        if (winnerTeam == CHO) {
            return GameStatus.CHO_WIN;
        }
        return GameStatus.HAN_WIN;
    }
}
