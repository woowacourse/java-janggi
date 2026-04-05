package janggi;

import janggi.config.AppConfig;
import janggi.controller.JanggiController;
import janggi.jdbc.ConnectionProvider;
import janggi.jdbc.dao.game.JdbcGameDao;
import janggi.jdbc.dao.piece.JdbcPieceDao;
import janggi.jdbc.transaction.TransactionExecutorImpl;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {
        ConnectionProvider provider = createConnectionProvider();
        JanggiService janggiService = createJanggiService(provider);

        JanggiController controller = new JanggiController(
                new OutputView(),
                new InputView(),
                janggiService
        );

        controller.run();
    }

    private static ConnectionProvider createConnectionProvider() {
        ConnectionProvider provider =
                new ConnectionProvider(new AppConfig());
        Runtime.getRuntime()
                .addShutdownHook(new Thread(provider::dispose));
        return provider;
    }

    private static JanggiService createJanggiService(ConnectionProvider provider) {
        return new JanggiService(
                new JdbcGameDao(),
                new JdbcPieceDao(),
                new TransactionExecutorImpl(provider)
        );
    }
}
