package janggi;

import janggi.board.BoardFactory;
import janggi.board.Position;
import janggi.piece.Side;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiManager {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiGame janggiGame;

    public JanggiManager(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiGame = new JanggiGame(
                BoardFactory.initBoard(),
                Turn.firstTurn()
        );
    }

    public void play() {
        while (janggiGame.continueGame()) {
            displayGameStatus();
            String inputStartPosition = inputView.readStartPosition();
            if (inputStartPosition.equals("Q")) {
                break;
            }
            String inputEndPosition = inputView.readEndPosition();
            movePiece(inputStartPosition, inputEndPosition);
        }
        outputView.printResult(janggiGame.calculateWinner());
    }

    private void displayGameStatus() {
        outputView.printBoard(janggiGame.getBoard());
        outputView.printScore(janggiGame.scoreBySide(Side.RED), janggiGame.scoreBySide(Side.BLUE));
        outputView.printTurn(janggiGame.getTurn());
    }

    private void movePiece(final String inputStartPosition, final String inputEndPosition) {
        handleException(() -> {
            Position start = parsePosition(inputStartPosition);
            Position end = parsePosition(inputEndPosition);
            janggiGame.movePiece(start, end);
        });
    }

    private Position parsePosition(final String input) {
        String[] coordinate = input.split(",");
        try {
            int x = Integer.parseInt(coordinate[0]);
            int y = Integer.parseInt(coordinate[1]);
            return new Position(x, y);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("(x,y) 형태로 입력해주세요.");
        }
    }

    private void handleException(final Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
        }
    }
}
