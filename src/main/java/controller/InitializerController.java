package controller;

import db.dao.JanggiGameDao.GameDto;
import janggiGame.arrangement.ArrangementOption;
import java.util.List;
import service.JanggiGameStartService;
import view.InputView;

public class InitializerController {
    private final InputView inputView;
    private final JanggiGameStartService janggiGameStartService;

    public InitializerController(InputView inputView, JanggiGameStartService janggiGameStartService) {
        this.inputView = inputView;
        this.janggiGameStartService = janggiGameStartService;
    }

    public Long getGameId() {
        int option = inputView.readStartOption();

        if (option == 1) {
            List<GameDto> games = janggiGameStartService.getSavedGames();
            return inputView.readSavedGameId(games);
        }

        return janggiGameStartService.getNewGameId(
                ArrangementOption.findBy(inputView.readHanArrangement()).getArrangementStrategy(),
                ArrangementOption.findBy(inputView.readChoArrangement()).getArrangementStrategy()
        );
    }
}
