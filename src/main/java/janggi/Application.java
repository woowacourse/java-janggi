package janggi;

import janggi.controller.GameSetController;
import janggi.controller.GamePlayController;
import janggi.dto.SetInfoDto;
import janggi.service.GameSetDBService;
import janggi.service.JanggiDBService;
import janggi.dao.ConnectionUtil;
import janggi.view.GameRunningView;
import janggi.view.GameSettingView;
import java.sql.Connection;

public class Application {
    public static void main(String[] args) {
        GameSettingView gameSettingView = new GameSettingView();
        GameRunningView gameRunningView = new GameRunningView();

        Connection connection = ConnectionUtil.getConnection();

        GameSetDBService gameSetDBService = new GameSetDBService(connection);
        GameSetController gameSetController = new GameSetController(gameSettingView, gameSetDBService);
        SetInfoDto setInfoDto = gameSetController.setJanggiGame();

        JanggiDBService janggiDBService = new JanggiDBService(connection, setInfoDto.roomId());

        GamePlayController controller = new GamePlayController(gameRunningView, janggiDBService, setInfoDto.janggiGame());
        controller.run();
    }

}
