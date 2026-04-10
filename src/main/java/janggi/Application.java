package janggi;

import janggi.controller.ConsoleController;
import janggi.domain.repository.GameRepository;
import janggi.infrastructure.dao.GameDao;
import janggi.infrastructure.dao.PieceDao;
import janggi.infrastructure.repository.JdbcGameRepository;
import janggi.service.GameService;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        GameRepository gameRepository = new JdbcGameRepository(
                new GameDao(),
                new PieceDao());
        ConsoleController consoleController = new ConsoleController(
                new InputView(new Scanner(System.in)),
                new OutputView(),
                new GameService(gameRepository)
        );
        consoleController.run();
    }
}
