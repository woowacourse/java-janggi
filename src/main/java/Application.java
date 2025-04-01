import console.Console;
import console.Input;
import console.Output;
import console.util.PositionConverter;
import janggi.position.Board;
import janggi.position.Position;
import java.util.function.Supplier;
import repository.Schema;
import repository.connection.ConnectMysql;

public class Application {
    private final Console console = new Console(new Input(), new Output());
    private final PositionConverter positionConverter = new PositionConverter();

    public static void main(String[] args) {
        final Application janggi = new Application();
        janggi.start();
    }

    private void start() {
        Schema.setTable(new ConnectMysql());
        Board board = Board.generate();

        console.startGame();

        do {
            console.display(board);
            console.displayTurn(board);

            displayScore(board);

            Board boardForTurn = board;
            board = process(() -> takeTurn(boardForTurn));

        } while (!board.catchKing());

        console.end(board.findWinner());
    }

    private void displayScore(Board board) {
        double choScore = board.choScore();
        double hanScore = board.hanScore();
        console.displayScore(choScore, hanScore);
    }

    public Board takeTurn(Board board) {
        String[] movePosition = console.move();
        Position source = positionConverter.convert(movePosition[0]);
        Position destination = positionConverter.convert(movePosition[1]);

        return move(source, destination, board);
    }

    public Board move(Position source, Position destination, Board board) {
        return board.move(source, destination, new ConnectMysql());
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
