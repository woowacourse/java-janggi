package game;

import java.util.List;
import piece.Country;
import position.Position;
import view.InputView;
import view.OutputView;

public class JanggiGame {

    private final InputView inputView;
    private final OutputView outputView;
    private final LoadOrSaveManager loadOrSaveManager = new LoadOrSaveManager();

    private Country turnCountry = Country.CHO;

    public JanggiGame(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Board board = initializeGame();
        outputView.displayBoard(board);
        while (!isGameOver(board)) {
            if (!playTurn(board)) {
                break;
            }
        }
        outputView.displayGameFinished();
    }

    private Board initializeGame() {
        boolean isLoad = inputView.askLoadSavedGame();

        if (isLoad) {
            this.turnCountry = loadOrSaveManager.loadTurnCountry();
            return loadOrSaveManager.loadBoard();
        }

        this.turnCountry = Country.CHO;
        StartSet choSet = inputView.getStartingPosition(Country.CHO);
        StartSet hanSet = inputView.getStartingPosition(Country.HAN);
        return new Board(choSet, hanSet);
    }

    private boolean isGameOver(Board board) {
        if (board.isGeneralDead()) {
            loadOrSaveManager.clear();
            return true;
        }
        return false;
    }

    private boolean playTurn(final Board board) {
        try {
            outputView.displayTurnCountry(turnCountry);
            outputView.displayCountryScore(turnCountry, board.getCountryScore(turnCountry));
            List<String> moveInfo = inputView.readMoveCommand();

            if (moveInfo.get(0).equals("quit")) {
                loadOrSaveManager.save(board, turnCountry);
                outputView.displayGameOver();
                return false;
            }

            Position from = Position.of(moveInfo.get(0), moveInfo.get(1));
            Position to = Position.of(moveInfo.get(2), moveInfo.get(3));

            board.movePiece(from, to, turnCountry);
            outputView.displayBoard(board);
            turnCountry = turnCountry.reverseCountry();

        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return true;
    }
}
