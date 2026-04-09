package service;

import static domain.player.Team.CHO;

import common.GameStatus;
import repository.JanggiGameRepository;
import domain.manager.JanggiGameManager;
import domain.player.PlayerProfile;
import domain.player.Team;
import domain.position.Position;
import domain.piece.BasicPiece;

public class JanggiGamePlayService {
    private final JanggiGameRepository janggiGameRepository;

    public JanggiGamePlayService(JanggiGameRepository janggiGameRepository) {
        this.janggiGameRepository = janggiGameRepository;
    }

    public void playTurn(long gameId, JanggiGameManager janggiGameManager, Position source, Position destination) {
        janggiGameManager.validateSource(source);
        BasicPiece movingPiece = janggiGameManager.getBoard().findPiece(source);
        janggiGameManager.move(source, destination);

        if (janggiGameManager.isGameRunning()) {
            saveProgressingGame(gameId, source, destination, movingPiece, janggiGameManager);
            return;
        }

        saveFinishedGame(gameId, source, destination, movingPiece, janggiGameManager);
    }

    private void saveProgressingGame(long gameId, Position source, Position destination, BasicPiece movingPiece, JanggiGameManager janggiGameManager) {
        Team currentTeam = janggiGameManager.getCurrentPlayer().getProfile().team();
        janggiGameRepository.saveTurnProgress(gameId, source, destination, movingPiece, currentTeam);
    }

    private void saveFinishedGame(long gameId, Position source, Position destination, BasicPiece movingPiece, JanggiGameManager janggiGameManager) {
        PlayerProfile winnerProfile = janggiGameManager.calculateFinalScore();
        Team winnerTeam = winnerProfile.team();
        GameStatus finishedStatus = resolveFinishedStatus(winnerTeam);
        janggiGameRepository.saveFinalMove(gameId, source, destination, movingPiece, winnerTeam, finishedStatus);
    }

    private GameStatus resolveFinishedStatus(Team winnerTeam) {
        if (winnerTeam == CHO) {
            return GameStatus.CHO_WIN;
        }
        return GameStatus.HAN_WIN;
    }
}
