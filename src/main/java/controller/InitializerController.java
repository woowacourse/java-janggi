package controller;

import db.dao.JanggiGameDao;
import db.dao.JanggiGameDao.GameEntity;
import janggiGame.arrangement.ArrangementOption;
import java.util.List;
import service.initializer.JanggiGameInitializer;
import view.InputView;

public class InitializerController {
    private final InputView inputView;
    private final JanggiGameInitializer janggiGameInitializer;
    private final JanggiGameDao janggiGameDao;

    public InitializerController(InputView inputView, JanggiGameInitializer janggiGameInitializer,
                                 JanggiGameDao janggiGameDao) {
        this.inputView = inputView;
        this.janggiGameInitializer = janggiGameInitializer;
        this.janggiGameDao = janggiGameDao;
    }

    public Long getGameId() {
        Long gameId;

        if (inputView.readStartOption() == 1) {
            List<GameEntity> games = janggiGameDao.findNotFinishedGames();
            gameId = inputView.readSavedGameId(games);
            return gameId;
        }

        gameId = janggiGameInitializer.getNewGameId(
                ArrangementOption.findBy(inputView.readHanArrangement()).getArrangementStrategy(),
                ArrangementOption.findBy(inputView.readChoArrangement()).getArrangementStrategy()
        );

        return gameId;
    }
}

