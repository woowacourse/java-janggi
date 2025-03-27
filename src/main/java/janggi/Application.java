package janggi;

import janggi.controller.GameSetController;
import janggi.controller.JanggiController;
import janggi.dto.SetInfoDto;
import janggi.service.GameSetDBService;
import janggi.service.JanggiDBService;
import janggi.util.ConnectionUtil;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.sql.Connection;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        Connection connection = ConnectionUtil.getConnection();

        GameSetDBService gameSetDBService = new GameSetDBService(connection);
        GameSetController gameSetController = new GameSetController(inputView, outputView, gameSetDBService);
        SetInfoDto setInfoDto = gameSetController.setJanggiGame();

        JanggiDBService janggiDBService = new JanggiDBService(connection, setInfoDto.boardId(), setInfoDto.roomId());

        JanggiController controller = new JanggiController(inputView, outputView, janggiDBService, setInfoDto.janggiGame());
        controller.run();
    }

}
