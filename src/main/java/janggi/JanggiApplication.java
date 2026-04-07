package janggi;

import janggi.controller.JanggiController;
import janggi.dao.game.JdbcGameDao;
import janggi.dao.piece.JdbcPieceDao;
import janggi.infra.AppConfig;
import janggi.infra.ConnectionProvider;
import janggi.infra.transaction.TransactionExecutorImpl;
import janggi.model.palace.Palaces;
import janggi.model.palace.factory.DefaultPalaceFactory;
import janggi.model.palace.factory.PalaceFactory;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {
        ConnectionProvider provider = createConnectionProvider();
        Palaces palaces = createPalaces();

        JanggiService janggiService = createJanggiService(provider, palaces);

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

    private static Palaces createPalaces() {
        PalaceFactory palaceFactory = new DefaultPalaceFactory();
        return palaceFactory.create();
    }

    private static JanggiService createJanggiService(
            ConnectionProvider provider,
            Palaces palaces
    ) {
        return new JanggiService(
                new JdbcGameDao(),
                new JdbcPieceDao(),
                new TransactionExecutorImpl(provider),
                palaces
        );
    }
}
