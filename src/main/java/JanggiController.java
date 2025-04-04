import domain.JanggiPosition;
import domain.game.JanggiGame;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public class JanggiController {
    public void run(JanggiService janggiService) {
        JanggiGame game = new JanggiGame(janggiService);
        startJanggiGame(game);
    }

    private void startJanggiGame(JanggiGame game) {
        Map<JanggiPosition, Piece> board = game.start();
        OutputView.printJanggiBoard(board);

        while (!game.isEnd()) {
            doJanggiGame(game);
        }
        OutputView.printScore(game.getChoScore(), game.getHanScore());
    }

    private void doJanggiGame(JanggiGame game) {
        boolean validInput = false;
        while (!validInput) {
            OutputView.printCurrentPlayerTurn(game.getPlayer());
            String input = InputView.input();

            if (input.equalsIgnoreCase("q")) {
                System.out.println("게임이 중단되었습니다.");
                System.exit(0);
                return;
            }

            try {
                List<JanggiPosition> positions = InputView.parsePositions(input);
                Map<JanggiPosition, Piece> board = game.move(positions.get(0), positions.get(1));

                OutputView.printJanggiBoard(board);

                validInput = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
