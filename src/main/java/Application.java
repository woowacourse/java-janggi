import console.Console;
import console.Input;
import console.Output;
import console.util.PositionConverter;
import janggi.Turn;
import janggi.board.Board;
import janggi.position.Position;
import java.util.function.BooleanSupplier;

public class Application {
    private final Console console = new Console(new Input(), new Output());

    public static void main(String[] args) {
        final Application janggi = new Application();
        janggi.start();
    }

    private void start() {
        Board board = Board.generate();
        Turn turn = Turn.start();

        console.startGame();
        console.display(board);

        while (takeTurn(board, turn)) {
            nextTurn(turn);
        }
//        endGame(board);
    }

    public boolean takeTurn(Board board, Turn turn) {
        return process(() -> {
            console.display(turn);

            //todo: 검증
            String[] movePosition = console.move().split(" ");
            Position source = new PositionConverter().convert(movePosition[0]);
            Position destination = new PositionConverter().convert(movePosition[1]);

//            move(response.source(), response.destination(), board, turn);
//            console.display(BoardDto.from(board));
//
//            return isPlaying(board);
            return false;
        });
    }

    public void nextTurn(Turn turn) {
        turn.next();
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

//    public void endGame(Board board) {
//        console.result(getWinner(board));
//    }

//    public void move(Position source, Position destination, Board board, Turn turn) {
//        Piece piece = board.get(source);
////        piece.move(board, turn.getCurrentTeam(), destination.x() - source.x(), destination.y() - source.y());
//    }
//
//    public boolean isPlaying(Board board) {
//        return board.getWinnerIfGameOver() == null;
//    }
//
//    public TeamDto getWinner(Board board) {
//        return TeamDto.from(board.getWinnerIfGameOver());
//    }
//
//    public void abstain(Board board, Turn turn) {
//        board.abstain(turn.getCurrentTeam());
//    }

}
