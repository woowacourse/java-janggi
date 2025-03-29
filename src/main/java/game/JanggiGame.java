package game;

import java.util.List;
import piece.Country;
import position.Position;
import view.InputView;
import view.OutputView;

public class JanggiGame {

    private final InputView inputView;
    private final OutputView outputView;

    private Country turnCountry = Country.CHO;

    public JanggiGame(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Board board = setGame();
        while (!isGameFinished(board)) {
            playTurn(board);
        }
    }

    private void playTurn(final Board board) {
        try {
            outputView.printTurn(turnCountry);
            List<String> moveInfo = inputView.readMoveCommand();

            Position fromPosition = Position.of(moveInfo.get(0), moveInfo.get(1));
            Position toPosition = Position.of(moveInfo.get(2), moveInfo.get(3));

            board.movePiece(fromPosition, toPosition, turnCountry);
            outputView.displayBoard(board);
            turnCountry = turnCountry.reverseCountry();

        } catch (IllegalArgumentException e) {
            System.out.println("ERROR" + e.getMessage());
        }
    }

    private Board setGame() {
        StartSet choStartingPosition = inputView.getStartingPosition(Country.CHO);
        StartSet hanStartingPosition = inputView.getStartingPosition(Country.HAN);
        Board board = new Board(choStartingPosition, hanStartingPosition);
        outputView.displayBoard(board);
        return board;
    }


    private boolean isGameFinished(Board board) {
        return board.isGeneralDead();
    }

}
