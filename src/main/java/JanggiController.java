import domain.JanggiPosition;
import domain.game.JanggiGame;
import domain.piece.Piece;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class JanggiController {

    public void run() {
        JanggiGame game = new JanggiGame();
        startJanggiGame(game);
    }

    private void startJanggiGame(JanggiGame game) {
        Map<JanggiPosition, Piece> board = game.start();
        OutputView.printJanggiBoard(board);

        while (!game.isEnd()) {
            doJanggiGame(game);
        }

        OutputView.printScore();
    }

    private void doJanggiGame(JanggiGame game) {
        boolean validInput = false;
        while (!validInput) {
            try {
                OutputView.printPlayerTurn(game.getPlayer());
                String[] positions = InputView.inputPositionsWithBlank();
                JanggiPosition beforePosition = new JanggiPosition(Character.getNumericValue(positions[0].charAt(0)),
                        Character.getNumericValue(positions[0].charAt(1)));
                JanggiPosition afterPosition = new JanggiPosition(Character.getNumericValue(positions[1].charAt(0)),
                        Character.getNumericValue(positions[1].charAt(1)));

                Map<JanggiPosition, Piece> board = game.move(beforePosition, afterPosition);

                if (!game.isEnd()) {
                    OutputView.printJanggiBoard(board);
                }

                validInput = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
