import board.Board;
import board.Position;
import board.Turn;
import dto.BoardDto;
import dto.TeamDto;
import java.util.function.BooleanSupplier;
import piece.Piece;
import view.Console;
import view.Input;
import view.Output;

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
        console.board(BoardDto.from(board));

        while (playTurn(board, turn)) {
            nextTurn(turn);
        }
        endGame(board);
    }

    public boolean playTurn(Board board, Turn turn) {
        return process(() -> {
            console.turn(TeamDto.from(turn.getCurrentTeam()));

            var response = console.command();
            if (response.abstain()) {
                abstain(board, turn);
                return false;
            }

            move(response.source(), response.destination(), board, turn);
            console.board(BoardDto.from(board));

            return isPlaying(board);
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

    public void endGame(Board board) {
        console.result(getWinner(board));
    }

    public void move(Position source, Position destination, Board board, Turn turn) {
        Piece piece = board.get(source);
        piece.move(board, turn.getCurrentTeam(), destination.x() - source.x(), destination.y() - source.y());
    }

    public boolean isPlaying(Board board) {
        return board.getWinnerIfGameOver() == null;
    }

    public TeamDto getWinner(Board board) {
        return TeamDto.from(board.getWinnerIfGameOver());
    }

    public void abstain(Board board, Turn turn) {
        board.abstain(turn.getCurrentTeam());
    }

}
