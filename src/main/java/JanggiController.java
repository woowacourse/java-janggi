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
            boolean isContinue = move(game);

            if (!isContinue) {
                break;
            }
        }
    }

    private boolean move(Game game) {
        try {
            Board board = game.getBoard();
            String currentInput = inputView.readPosition(game.getTurnName());
            if (currentInput.equals("n")) {
                return false;
            }
            Position currentPosition = parsePosition(currentInput);

            Piece piece = board.findPieceByPosition(currentPosition)
                    .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다."));
            game.checkTurn(piece.getTeam());

            String targetInput = inputView.readTargetPosition();
            if (targetInput.equals("n")) {
                return false;
            }
            Position targetPosition = parsePosition(targetInput);

            game.tryToMove(currentPosition, targetPosition);

            outputView.printBoard(board.getBoard());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println();
            return move(game);
        }
    }

    private Position parsePosition(String input) {
        String[] tokens = input.split(" ");
        return Position.of(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
    }
}
