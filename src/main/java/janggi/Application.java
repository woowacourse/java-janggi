package janggi;

import janggi.dao.PieceDao;
import janggi.dao.TurnDao;
import janggi.controller.JanggiController;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        JanggiController janggiController = new JanggiController(
                new InputView(),
                new OutputView(),
                new JanggiService(
                        new PieceDao(),
                        new TurnDao()
                )
        );
        janggiController.play();
    }
}
