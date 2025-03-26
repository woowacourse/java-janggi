package janggi;

import janggi.board.BoardFactory;
import janggi.board.Position;
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
                Score.initRedSideScore(),
                Score.initBlueSideScore(),
                Turn.firstTurn()
        );
    }

    public void play() {
        displayGameStatus();
        while (janggiGame.continueGame()) {
            outputView.printTurn(janggiGame.getTurn());
            String inputStartPosition = inputView.readStartPosition();
            if (inputStartPosition.equals("Q")) {
                break;
            }
            String inputEndPosition = inputView.readEndPosition();
            movePiece(inputStartPosition, inputEndPosition);
            displayGameStatus();
        }
        outputView.printResult(janggiGame.getRedScore(), janggiGame.getBlueScore());
    }

    private void displayGameStatus() {
        outputView.printBoard(janggiGame.getBoard());
        outputView.printScore(janggiGame.getRedScore(), janggiGame.getBlueScore());
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
