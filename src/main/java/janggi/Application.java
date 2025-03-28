package janggi;

import janggi.dao.PieceDao;
import janggi.dao.TurnDao;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        JanggiManager janggiManager = new JanggiManager(
                new InputView(),
                new OutputView(),
                new JanggiService(
                        new PieceDao(),
                        new TurnDao()
                )
        );
        janggiManager.play();
    }
}
