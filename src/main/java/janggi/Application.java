package janggi;

import janggi.controller.BoardInitializer;
import janggi.controller.GameController;
import janggi.repository.GameRepository;
import janggi.repository.MemoryGameRepository;
import janggi.repository.Repository;
import janggi.view.BoardInitiliazeView;
import janggi.view.ConfigurationView;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.io.IOException;

public class Application {

    private static final int RETRY_COUNT = 12;
    private static final ConfigurationView configurationView = new ConfigurationView();

    private static final GameRepository ONLINE_REPOSITORY = new GameRepository();
    private static final MemoryGameRepository MEMORY_REPOSITORY = new MemoryGameRepository();

    public static void main(String[] args) throws IOException, InterruptedException {
        boolean launchWithServer = false;

        try {
            launchWithServer = configurationView.readOnlineOrLocal(RETRY_COUNT);
            Repository repository = decideRepository(launchWithServer);

            runApplicationWith(repository);
        } finally {
            if (launchWithServer) {
                stopServer();
            }
        }
    }

    private static Repository decideRepository(final boolean launchOnline) throws IOException, InterruptedException {
        if (launchOnline) {
            return tryConnectAndDecideRepository();
        }

        configurationView.printConnectionFailed();
        return MEMORY_REPOSITORY;
    }

    private static Repository tryConnectAndDecideRepository() throws IOException, InterruptedException {
        configurationView.printConnectingServer();
        ShellExecutor.executeShellCommand("cd ./docker\ndocker-compose -p janggi up -d", 5);

        if (isServerConnectable()) {
            return ONLINE_REPOSITORY;
        }

        configurationView.printConnectionFailed();
        return MEMORY_REPOSITORY;
    }

    private static boolean isServerConnectable() {
        for (int i = 0; i < Application.RETRY_COUNT; i++) {
            if (Application.ONLINE_REPOSITORY.isConnectable()) {
                return true;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return false;
            }
        }
        return false;
    }

    private static void runApplicationWith(Repository repository) {
        BoardInitializer boardInitializer = new BoardInitializer(new BoardInitiliazeView());
        boardInitializer.initializeRepository(repository);

        GameController gameController = new GameController(
            new InputView(),
            new OutputView(),
            repository
        );
        gameController.play();
    }

    private static void stopServer() throws IOException, InterruptedException {
        configurationView.printStoppingServer();
        ShellExecutor.executeShellCommand("cd ./docker\ndocker-compose -p janggi down", 5);
    }
}
