package janggi.controller;

import janggi.domain.board.Position;
import janggi.domain.piece.Side;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;

    public JanggiController(final InputView inputView, final OutputView outputView, final JanggiService janggiService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = janggiService;
    }

    public void play() {
        while (janggiService.continueGame()) {
            displayGameStatus();
            String inputStartPosition = inputView.readStartPosition();
            if (inputStartPosition.equals("Q")) {
                break;
            }
            String inputEndPosition = inputView.readEndPosition();
            movePiece(inputStartPosition, inputEndPosition);
        }
        outputView.printResult(janggiService.calculateWinner());
        janggiService.clearGame();
    }

    private void displayGameStatus() {
        outputView.printBoard(janggiService.findPiecesByPosition());
        outputView.printScore(janggiService.scoreBySide(Side.RED), janggiService.scoreBySide(Side.BLUE));
        outputView.printTurn(janggiService.getTurn());
    }

    private void movePiece(final String inputStartPosition, final String inputEndPosition) {
        handleException(() -> {
            Position start = parsePosition(inputStartPosition);
            Position end = parsePosition(inputEndPosition);
            janggiService.movePiece(start, end);
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
