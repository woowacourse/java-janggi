import repository.MongoCounterRepository;
import repository.MongoGameRepository;
import view.ConsoleView;
import view.InputView;
import view.OutputView;

public class Main {
    public static void main(String[] args) {
        try (MongoGameRepository gameRepository = new MongoGameRepository();
             MongoCounterRepository counterRepository = new MongoCounterRepository()) {
            JanggiRunner janggiRunner = new JanggiRunner(
                    new ConsoleView(new InputView(), new OutputView()),
                    gameRepository,
                    counterRepository
            );
            janggiRunner.run();
        }
    }
}
