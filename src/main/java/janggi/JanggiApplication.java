package janggi;

import janggi.db.DatabaseInitializer;
import janggi.repository.GamePieceRepository;
import janggi.repository.GameStateRepository;
import janggi.service.GameService;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Scanner;

public class JanggiApplication {
    public static void main(String[] args) {
        new DatabaseInitializer().initialize();

        JanggiGame janggi = new JanggiGame(
                new InputView(new Scanner(System.in)),
                new OutputView(),
                new GameService(new GameStateRepository(), new GamePieceRepository())
        );
        janggi.run();
    }
}
