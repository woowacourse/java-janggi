package janggi;

import janggi.config.AppConfig;
import janggi.controller.JanggiController;
import janggi.infra.ConnectionProvider;
import janggi.repository.JdbcGameRepository;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {
        ConnectionProvider provider =
                new ConnectionProvider(new AppConfig());

        Runtime.getRuntime().addShutdownHook(new Thread(provider::dispose));

        JanggiController controller = new JanggiController(
                new OutputView(),
                new InputView(),
                provider,
                JdbcGameRepository.of()
        );

        controller.run();
    }
}
