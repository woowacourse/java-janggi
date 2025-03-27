package janggi;

import janggi.controller.GameSetController;
import janggi.controller.JanggiController;
import janggi.dto.SetInfoDto;
import janggi.service.GameSetDBService;
import janggi.service.JanggiDBService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        GameSetDBService gameSetDBService = new GameSetDBService();
        GameSetController gameSetController = new GameSetController(inputView, outputView, gameSetDBService);
        SetInfoDto setInfoDto = gameSetController.setJanggiGame();

        JanggiDBService janggiDBService = new JanggiDBService(setInfoDto.boardId(), setInfoDto.roomId());

        JanggiController controller = new JanggiController(inputView, outputView, janggiDBService, setInfoDto.janggiGame());
        controller.run();
    }

}
