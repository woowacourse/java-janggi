package service;

import domain.GameId;
import domain.JanggiGame;
import domain.SettingType;
import domain.position.Position;
import java.util.List;
import repository.dao.GameDao;

public class JanggiGameService {
    private final TransactionTemplate transactionTemplate;
    private final GameDao gameDao;

    public JanggiGameService(TransactionTemplate transactionTemplate, GameDao gameDao) {
        this.transactionTemplate = transactionTemplate;
        this.gameDao = gameDao;
    }

    public void initializeDatabase() {
        transactionTemplate.execute(
                connection -> {
                    gameDao.initTable(connection);
                    return null;
                }
        );
    }

    public boolean isPlayingGameExist() {
        return transactionTemplate.execute(
                connection -> {
                    List<GameId> playingGameIds = gameDao.findPlayingGameIds(connection);
                    return !playingGameIds.isEmpty();
                }
        );
    }

    public void abandonGame() {
        transactionTemplate.execute(
                connection -> {
                    List<JanggiGame> playingGames = gameDao.findPlayingGames(connection);
                    for (JanggiGame game : playingGames) {
                        game.getContext().finishGame();
                        gameDao.updateContext(connection, game.getId(), game.getContext());
                    }
                    return null;
                }
        );
    }

    public JanggiGame startNewGame(SettingType choSettingType, SettingType hanSettingType) {
        return transactionTemplate.execute(
                connection -> {
                    JanggiGame newGame = JanggiGame.init(choSettingType, hanSettingType);
                    GameId gameId = gameDao.save(connection, newGame);
                    return gameDao.findGameById(connection, gameId);
                }
        );
    }

    public JanggiGame loadPlayingGame() {
        return transactionTemplate.execute(
                connection -> {
                    List<JanggiGame> playingGames = gameDao.findPlayingGames(connection);
                    if (playingGames.isEmpty()) {
                        throw new IllegalStateException("진행 중인 게임이 없습니다.");
                    }
                    return playingGames.getFirst();
                }
        );
    }

    public void doMove(JanggiGame game, Position start, Position destination) {
        transactionTemplate.execute(
                connection -> {
                    game.executeMove(start, destination);
                    gameDao.updateGamePiece(connection, game.getId(), game);
                    return null;
                }
        );
    }

    public void passTurn(JanggiGame game) {
        transactionTemplate.execute(
                connection -> {
                    game.passTurn();
                    gameDao.updateContext(connection, game.getId(), game.getContext());
                    return null;
                }
        );
    }
}
