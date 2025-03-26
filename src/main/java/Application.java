import console.Console;
import console.Input;
import console.Output;
import console.util.PositionConverter;
import janggi.position.Board;
import janggi.position.Position;
import java.util.function.Supplier;

public class Application {
    private final Console console = new Console(new Input(), new Output());

    public static void main(String[] args) {
        final Application janggi = new Application();
        janggi.start();
    }

    private void start() {
        Board board = Board.generate();

        console.startGame();

        while (true) {
            console.display(board);
            console.displayTurn(board);

            Board boardForTurn = board;
            board = process(() -> takeTurn(boardForTurn));
        }
    }

    public Board takeTurn(Board board) {
        String[] movePosition = console.move();
        Position source = new PositionConverter().convert(movePosition[0]);
        Position destination = new PositionConverter().convert(movePosition[1]);

        return move(source, destination, board);
    }

    public Board move(Position source, Position destination, Board board) {
        return board.move(source, destination);
    }

    public Board process(Supplier<Board> action) {
        while (true) {
            try {
                return action.get();
            } catch (IllegalArgumentException e) {
                console.retry(e);
            }
        }
    }

}
