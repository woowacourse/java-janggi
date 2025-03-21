import java.util.function.BooleanSupplier;
import service.JanggiService;
import view.Console;
import view.Input;
import view.Output;

public class Application {
    private final Console console = new Console(new Input(), new Output());
    private final JanggiService service = new JanggiService();

    public static void main(String[] args) {
        final Application janggi = new Application();
        janggi.start();
    }

    private void start() {
        startGame();
        while (playTurn()) {
            nextTurn();
        }
        endGame();
    }

    private void startGame() {
        service.startGame();
        console.startGame();
        console.board(service.getBoard());
    }

    public boolean playTurn() {
        return process(() -> {
            console.turn(service.currentTurn());

            var response = console.command();
            if (response.abstain()) {
                service.abstain();
                return false;
            }

            service.move(response.source(), response.destination());
            console.board(service.getBoard());

            return service.isPlaying();
        });
    }

    public void nextTurn() {
        service.nextTurn();
    }

    public boolean process(BooleanSupplier action) {
        while(true) {
            try {
                return action.getAsBoolean();
            } catch (IllegalArgumentException e) {
                console.retry(e);
            }
        }
    }

    public void endGame() {
        console.result(service.getWinner());
    }
}
