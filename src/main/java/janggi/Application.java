package janggi;

import janggi.controller.ApplicationConfigurer;
import janggi.controller.GameController;
import janggi.repository.Repository;
import janggi.view.BoardInitiliazeView;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.io.IOException;

public class Application {

    private static final int RETRY_COUNT = 12;

    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            launchDocker();
            runApplication();
        } finally {
            stopDocker();
        }
    }

    private static void launchDocker() throws IOException, InterruptedException {
        System.out.println("docker-compose 실행중입니다..");
        System.out.printf("처음 실행의 경우 최대 %s초 가량 소요됩니다.%n", RETRY_COUNT);
        System.out.println("! 강제 종료 시 docker 프로세스가 유지될 수 있습니다 !%n");
        ShellExecutor.executeShellCommand("cd ./docker\ndocker-compose -p janggi up -d", 5);
    }

    private static void runApplication() {
        ApplicationConfigurer applicationConfigurer = new ApplicationConfigurer(new BoardInitiliazeView());
        Repository repository = applicationConfigurer.loadRepository(RETRY_COUNT);
        applicationConfigurer.configureRepository(repository);

        GameController gameController = new GameController(
            new InputView(),
            new OutputView(),
            repository
        );
        gameController.play();
    }

    private static void stopDocker() throws IOException, InterruptedException {
        System.out.println("docker-compose 중지중입니다.. (약 2초 소요)");
        ShellExecutor.executeShellCommand("cd ./docker\ndocker-compose -p janggi down", 5);
    }
}
