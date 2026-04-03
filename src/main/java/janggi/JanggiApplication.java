package janggi;

import janggi.db.DatabaseInitializer;
import janggi.db.H2ConnectionManager;
import janggi.db.TransactionManager;
import janggi.repository.GamePieceRepository;
import janggi.repository.GameStateRepository;
import janggi.service.GameService;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Scanner;

public class JanggiApplication {
    public static void main(String[] args) {
        H2ConnectionManager connectionManager = new H2ConnectionManager();
        new DatabaseInitializer(connectionManager).initialize();
        TransactionManager transactionManager = new TransactionManager(connectionManager);

        GameRunner janggi = new GameRunner(
                new InputView(new Scanner(System.in)),
                new OutputView(),
                new GameService(transactionManager, new GameStateRepository(), new GamePieceRepository())
        );
        janggi.run();
    }
}
