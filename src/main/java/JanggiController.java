import domain.*;
import domain.vo.Position;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Formation hanFormation = inputView.readHorseElephantFormation(Team.HAN.getName());
        Formation chuFormation = inputView.readHorseElephantFormation(Team.CHU.getName());

        Board board = BoardFactory.setUp(hanFormation, chuFormation);
        outputView.printBoard(board.getBoard());

        Game game = Game.of(board);

        while (true) {
            boolean isContinue = move(board, game);

            if (!isContinue) {
                break;
            }
        }
    }

    private boolean move(Board board, Game game) {
        try {
            String currentInput = inputView.readPosition(game.getTurnName());
            if (currentInput.equals("n")) {
                return false;
            }
            Position currentPosition = parsePosition(currentInput);

            String targetInput = inputView.readTargetPosition();
            if (targetInput.equals("n")) {
                return false;
            }
            Position targetPosition = parsePosition(targetInput);

            board.tryToMove(currentPosition, targetPosition, game.getTeam());
            game.nextTurn();
            outputView.printBoard(board.getBoard());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println();
            return move(board, game);
        }
    }

    private Position parsePosition(String input) {
        String[] tokens = input.split(" ");
        return Position.of(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
    }
}
