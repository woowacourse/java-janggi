import dao.JanggiDao;
import domain.JanggiPosition;
import domain.game.JanggiGame;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private JanggiDao janggiDao = new JanggiDao();

    public void run() {
        JanggiGame game = new JanggiGame();
        startJanggiGame(game);
    }

    private void startJanggiGame(JanggiGame game) {
        Map<JanggiPosition, Piece> board = janggiDao.loadBoard();
        game.start(board);
        OutputView.printJanggiBoard(board);

        while (!game.isEnd()) {
            doJanggiGame(game);
        }
        OutputView.printScore(game.getChoScore(), game.getHanScore());
    }

    private void doJanggiGame(JanggiGame game) {
        boolean validInput = false;
        while (!validInput) {
            try {
                OutputView.printCurrentPlayerTurn(game.getPlayer());
                List<JanggiPosition> positions = InputView.inputPositionsWithBlank();

                Map<JanggiPosition, Piece> board = game.move(positions.get(0), positions.get(1));

                OutputView.printJanggiBoard(board);

                validInput = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
