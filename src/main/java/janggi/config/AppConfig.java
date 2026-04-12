package janggi.config;

import janggi.controller.JanggiController;
import janggi.infra.PersistenceConfig;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class AppConfig {

    private final PersistenceConfig persistenceConfig;

    private JanggiService janggiService;

    private OutputView outputView;
    private InputView inputView;

    private JanggiController janggiController;

    public AppConfig(PersistenceConfig persistenceConfig) {
        this.persistenceConfig = persistenceConfig;
    }

    public JanggiService janggiService() {
        if (janggiService == null) {
            janggiService = new JanggiService(
                    persistenceConfig.gameDao(),
                    persistenceConfig.pieceDao(),
                    persistenceConfig.transactionExecutor()
            );
        }

        return janggiService;
    }

    public OutputView outputView() {
        if (outputView == null) {
            outputView = new OutputView();
        }

        return outputView;
    }

    public InputView inputView() {
        if (inputView == null) {
            inputView = new InputView();
        }

        return inputView;
    }

    public JanggiController janggiController() {
        if (janggiController == null) {
            janggiController = new JanggiController(
                    outputView(),
                    inputView(),
                    janggiService()
            );
        }

        return janggiController;
    }
}
