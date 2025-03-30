package config;

import janggi.controller.JanggiController;
import janggi.db.Connection;
import janggi.db.PieceDao;
import janggi.db.TurnDao;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Config {
    private static InputView inputView;
    private static OutputView outputView;
    private static Connection connection;
    private static PieceDao pieceDao;
    private static TurnDao turnDao;
    private static JanggiController janggiController;

    public JanggiController janggiController() {
        if (janggiController == null) {
            return new JanggiController(inputView(), outputView(), connection());
        }
        return janggiController;
    }

    private InputView inputView() {
        if (inputView == null) {
            return new InputView();
        }
        return inputView;
    }

    private OutputView outputView() {
        if (outputView == null) {
            return new OutputView();
        }
        return outputView;
    }

    private Connection connection() {
        if (connection == null) {
            return new Connection();
        }
        return connection;
    }
}
