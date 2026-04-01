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
        move(board, game);

        while (inputView.readRetryCommand()) {
            move(board, game);
        }
    }

    private void move(Board board, Game game) {
        try {
            Position position = inputView.readPosition(game.getTurnName());
            Position targetPosition = inputView.readTargetPosition();

            board.tryToMove(position, targetPosition, game.getTeam());
            game.nextTurn();
            outputView.printBoard(board.getBoard());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println();
            move(board, game);
        }
    }
}
