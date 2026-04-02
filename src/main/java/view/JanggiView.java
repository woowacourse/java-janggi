package view;

import board.SangSetupType;
import pieces.Side;
import position.Position;
import util.Retry;

public class JanggiView {

    private final InputView in = new InputView();
    private final OutputView out = new OutputView();

    public SangSetupType askSangSetupUntilSuccess(Side side) {
        return Retry.untilSuccess(() -> {
            out.askSangSetup(side);
            return in.readSangSetup();
        });
    }

    public void printBoard(String board) {
        out.printBoard(board);
    }

    public void printTurnSide(Side side) {
        out.printTurnSide(side);
    }

    public Position askDeparture() {
        out.askDeparture();
        return readPositionUntilSuccess();
    }

    public Position askDestination() {
        out.askDestination();
        return readPositionUntilSuccess();
    }

    private Position readPositionUntilSuccess() {
        return Retry.untilSuccess(in::readPosition);
    }

    public void printGameIsOver(Side winner) {
        out.printGameIsOver(winner);
    }
}
