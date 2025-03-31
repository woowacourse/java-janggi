package janggi;

import janggi.controller.GameSetController;
import janggi.controller.GamePlayController;
import janggi.dto.SetInfoDto;
import janggi.service.GameSetService;
import janggi.service.JanggiService;
import janggi.dao.ConnectionUtil;
import janggi.view.GameRunningView;
import janggi.view.GameSettingView;
import java.sql.Connection;

public class Application {
    public static void main(String[] args) {
        GameSettingView gameSettingView = new GameSettingView();
        GameRunningView gameRunningView = new GameRunningView();

        Connection connection = ConnectionUtil.getConnection();

        GameSetService gameSetService = new GameSetService(connection);
        GameSetController gameSetController = new GameSetController(gameSettingView, gameSetService);
        SetInfoDto setInfoDto = gameSetController.setJanggiGame();

        JanggiService janggiService = new JanggiService(connection, setInfoDto.janggiGame(), setInfoDto.roomId());

        GamePlayController controller = new GamePlayController(gameRunningView, janggiService);
        controller.run();
    }
}
