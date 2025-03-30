package janggi.controller;

import janggi.JanggiGame;
import janggi.piece.Color;
import janggi.piece.Piece;
import janggi.position.Position;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Map;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        JanggiGame janggiGame = new JanggiGame();

        while (true) {
            printBoard(janggiGame.getBoard(), janggiGame.getScore(Color.RED), janggiGame.getScore(Color.BLUE));

            String startPosition = inputView.inputMoveStartPosition();
            String endPosition = inputView.inputMoveEndPosition();

            janggiGame.move(Position.from(startPosition), Position.from(endPosition));
        }
    }

    private void printBoard(final Map<Position, Piece> board, final double redScore, final double blueScore) {
        outputView.printBoard(board);
        outputView.printScore(redScore, blueScore);
    }
}
