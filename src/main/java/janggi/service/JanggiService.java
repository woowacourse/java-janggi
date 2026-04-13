package janggi.service;

import janggi.domain.JanggiGame;
import janggi.domain.Position;
import janggi.persistence.GameStatus;
import janggi.persistence.dto.MoveHistory;
import janggi.persistence.model.JanggiGameHistory;
import janggi.persistence.repository.JanggiGameRepository;

public class JanggiService {

    private final JanggiGameRepository janggiGameRepository;
    private long currentGameId;

    public JanggiService(JanggiGameRepository janggiGameRepository) {
        this.janggiGameRepository = janggiGameRepository;
    }

    public JanggiGame loadGame() {
        JanggiGameHistory recentGame = janggiGameRepository.findRecentGame();
        if (recentGame.isBeforeStart()) {
            currentGameId = janggiGameRepository.createNewGame();
            return JanggiGame.createInitialJanggiGame();
        }
        currentGameId = recentGame.getGameId();
        return rebuildJanggiGame(recentGame);
    }

    public void play(JanggiGame janggiGame, Position startPosition, Position endPosition) {
        janggiGame.doGame(startPosition, endPosition);
        saveMove(janggiGame, startPosition, endPosition);
    }

    public void saveMove(JanggiGame janggiGame, Position startPosition, Position endPosition) {
        janggiGameRepository.saveMove(currentGameId, janggiGame.getTurnCount(), startPosition, endPosition);
    }

    public void finishGame() {
        janggiGameRepository.update(GameStatus.FINISHED, currentGameId);
    }

    private JanggiGame rebuildJanggiGame(JanggiGameHistory recentGame) {
        JanggiGame janggiGame = JanggiGame.createInitialJanggiGame();
        for (MoveHistory moveHistory : recentGame.getMoveHistories()) {
            janggiGame.doGame(
                new Position(moveHistory.startX(), moveHistory.startY()),
                new Position(moveHistory.endX(), moveHistory.endY())
            );
        }
        return janggiGame;
    }
}
