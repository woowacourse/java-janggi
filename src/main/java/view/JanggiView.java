package view;

import board.SangSetupType;
import pieces.Side;
import position.Position;
import util.Retry;

public class JanggiView {

    private final InputView in;
    private final OutputView out;

    public JanggiView(InputView in, OutputView out) {
        this.in = in;
        this.out = out;
    }

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
}
