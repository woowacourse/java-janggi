package janggi;

import janggi.controller.ApplicationConfigurer;
import janggi.controller.GameController;
import janggi.repository.Repository;
import janggi.view.BoardInitiliazeView;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            System.out.println("docker-compose 실행중입니다.. (5초 소요됨)");
            ShellExecutor.executeShellCommand("cd ./docker\ndocker-compose -p janggi up -d", 5);
            Thread.sleep(5000);

            runApplication();
        } finally {
            System.out.println("docker-compose 중지중입니다..");
            ShellExecutor.executeShellCommand("cd ./docker\ndocker-compose -p janggi down", 5);
        }
    }

    private static void runApplication() {
        ApplicationConfigurer applicationConfigurer = new ApplicationConfigurer(new BoardInitiliazeView());
        Repository repository = applicationConfigurer.configureRepository();

        GameController gameController = new GameController(
            new InputView(),
            new OutputView(),
            repository
        );
        gameController.play();
    }
}
