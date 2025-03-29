package game;

import java.sql.SQLOutput;
import java.util.List;
import piece.Country;
import position.Position;
import view.InputView;
import view.OutputView;

public class JanggiGame {

    private final InputView inputView;
    private final OutputView outputView;

    private Country turn = Country.Cho;

    public JanggiGame(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        Board board = new Board(StartPosition.MA_SANG_SANG_MA, StartPosition.MA_SANG_MA_SANG);
        outputView.displayBoard(board);

        while (true) {
            try {
                List<String> moveInfo = inputView.readMoveCommand();

                Position source = Position.of(moveInfo.get(0), moveInfo.get(1));
                Position target = Position.of(moveInfo.get(2), moveInfo.get(3));

                board.movePiece(source, target);
                outputView.displayBoard(board);
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR"+e.getMessage());
            }
        }
    }

}
