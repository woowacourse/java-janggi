import controller.JanggiController;

import domain.repository.JdbcGameRepository;
import service.JanggiService;
import view.InputView;
import view.OutputView;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        JanggiController janggiController = new JanggiController(
                new InputView(new Scanner(System.in)),
                new OutputView(),
                new JanggiService(new JdbcGameRepository())
        );
        janggiController.run();
    }
}
