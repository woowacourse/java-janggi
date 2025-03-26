package janggi;

import janggi.board.Board;
import janggi.board.Position;
import janggi.piece.Side;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiManager {

    private final InputView inputView;
    private final OutputView outputView;
    private final Board board;
    private final Player redPlayer;
    private final Player bluePlayer;
    private Turn turn;

    public JanggiManager(final Board board, final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.board = board;
        this.redPlayer = Player.createRedSidePlayer();
        this.bluePlayer = Player.createBlueSidePlayer();
        this.turn = Turn.firstTurn();
    }

    public void play() {
        displayGameStatus();
        while (continueGame()) {
            outputView.printTurn(turn);
            String inputStartPosition = inputView.readStartPosition();
            if (inputStartPosition.equals("Q")) {
                break;
            }
            String inputEndPosition = inputView.readEndPosition();
            movePiece(inputStartPosition, inputEndPosition);
            displayGameStatus();
        }
        outputView.printResult(redPlayer, bluePlayer);
    }

    private boolean continueGame() {
        return !redPlayer.isEnd() && !bluePlayer.isEnd();
    }

    private void displayGameStatus() {
        outputView.printBoard(board.getBoard());
        outputView.printScore(redPlayer, bluePlayer);
    }

    private void movePiece(final String inputStartPosition, final String inputEndPosition) {
        handleException(() -> {
            Position start = parsePosition(inputStartPosition);
            Position end = parsePosition(inputEndPosition);
            int score = board.move(start, end, turn);
            if (turn.side() == Side.BLUE) {
                bluePlayer.minusScore(score);
            }
            if (turn.side() == Side.RED) {
                redPlayer.minusScore(score);
            }
            this.turn = turn.nextTurn();
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
