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

    public void start() {
        Board board = new Board(StartPosition.MA_SANG_SANG_MA, StartPosition.MA_SANG_MA_SANG);
        outputView.displayBoard(board);

        while (true) {
            try {
                outputView.printTurn(turnCountry);
                List<String> moveInfo = inputView.readMoveCommand();

                Position source = Position.of(moveInfo.get(0), moveInfo.get(1));
                Position target = Position.of(moveInfo.get(2), moveInfo.get(3));

                board.movePiece(source, target,turnCountry);
                outputView.displayBoard(board);
                changeTurn();
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR"+e.getMessage());
            }
        }
    }

    private void changeTurn() {
        turnCountry = turnCountry.reverseCountry();
    }


}
